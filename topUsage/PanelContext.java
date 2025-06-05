package fe.cmn.panel;

import java.util.Map;

import cell.cmn.cache.IMapCell;
import cson.CsonUtil;
import fe.cmn.app.Context;
import fe.cmn.data.ServiceParamDto;
import fe.cmn.mgr.PanelCacheUtil;
import fe.cmn.sys.FeTracker;
import flutter.coder.annt.NullSafe;
import flutter.rpc.CRpcCallbackCmd;

/**
 * 前端发给后端的容器上下文信息
 * 
 * 用于告诉后端一些关于前端当前动作所在容器的上下文信息，以便后端回调前端抓取所需的额外信息
 * 
 * 例如：前端表格某监听器触发后端逻辑，需要高速后端当前表格（Panel）的唯一标号，后端可通过此标号回调前端向上或向下访问其它组件的信息
 *
 * 信道改造为资源型CELL后, 此方法废弃 包含信道的PanelContext可以被随意到处传递, 不再需要export 但是, "切记",
 * 默认的信道有效期为1分钟, 亦可自行设置, 参考CWsCallbackChannel::CWS_CALLBACK_CHANNEL_EXPIRE_MS,
 * 若长时间不调用, 服务端会主动回收超时的信道, 使用被回收的信道会抛出异常
 * 
 * [重要] : 内含远程信道对象, 不可复制, 不能自行序列化反序列化.
 *              建议用完立刻显性调用close()释放资源, 否则系统会等待超时(默认1分钟)后才会释放, 存在堆积的可能 !!!!
 */
public class PanelContext extends Context
{
    private static final long serialVersionUID = -5122260593462937853L;

    /**
     * 当需要跨面板访问时，如访问父或子面板，可以通过指定widget path的方式进行访问，而无需再通过设置GlobalKey的方式（此方式逐渐废弃）
     * 此参数主要用于后端回调前端时传入，所以大多只需set方法 
     * 例如： 
     *  相对路径：../panel2, panel3/panel4
     *  绝对路径：/panel1/panel2 具体路径规则参考《用路径实现复杂跨Panel访问.docx》
     */
    String targetPanelPath;
    
    // 前端当前所在的Container的Global Key（用于全局定位Panel，回调也是通过此ID找到目标Panel执行）
    @NullSafe
    String currentPanelGlobalKey;

    // 当前Panel的WidgetId（用于值搜集、获取）
    // 来自前端，只读
    String currentPanelWidgetId;

    // 当前Panel的类名（DTO的全类名）
    // 来自前端，只读
    String currentPanelClass;

    // 当前界面存在的编辑值（有时重复打开的界面，例如下拉面板，本身已经有值了需要再次显示到界面）
    // 这个只用于界面传值给后端，所以java端只需要get方法、无需set方法
    PanelValue panelValue;

    // 页面间跳转（路由驱动）时，可以传参，参数类型不限定（但必须是CsonPojo子类或基础类型）
    // 此字段为前端传递给后端（由跳转页面时传入）
    // 来自前端，只读
    Object routeParameter;
    
    // 路由查询参数,考虑其动态性，仅在首页及路由构建时前端会带回来（web端会将其写入url）
    // 参数值类型不限定（但必须是CsonPojo子类或基础类型）
    Map<String, Object> queryParameters;
    
    // 当前悬浮层操作所关联的组件单元id（前端设置）,仅在布局器中使用
    String relatedUnitId;
    
    // 接口方法标识参数
    ServiceParamDto serviceParam;
  
    public String getTargetPanelPath()
    {
        return targetPanelPath;
    }

    public PanelContext setTargetPanelPath(String targetPanelPath)
    {
        this.targetPanelPath = targetPanelPath;
        return this;
    }

    public String getRelatedUnitId() {
        return relatedUnitId;
    }

    public String getCurrentPanelGlobalKey()
    {
        return currentPanelGlobalKey;
    }

    // 这是老的跨panel回调的方式，通过设置此key来实现定位。现在建议用指定targetPanelPath（目标面板WidgetPath）的方式去访问其它面板
    @Deprecated
    public PanelContext setCurrentPanelGlobalKey(String currentPanelGlobalKey)
    {
        this.currentPanelGlobalKey = currentPanelGlobalKey;
        return this;
    }

    public String getCurrentPanelClass()
    {
        return currentPanelClass;
    }

    public String getCurrentPanelWidgetId()
    {
        return currentPanelWidgetId;
    }

    public PanelValue getPanelValue()
    {
        return panelValue;
    }

    public Object getWidgetValue(String widgetId)
    {
        return panelValue == null ? null : panelValue.getValue(widgetId);
    }

    public Object getRouteParameter()
    {
        return routeParameter;
    }

    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }

    public ServiceParamDto getServiceParam() {
        return serviceParam;
    }

    public PanelContext setServiceParam(ServiceParamDto serviceParam) {
        this.serviceParam = serviceParam;
        return this;
    }

    // 此方法在后端调用，可以回调客户端对应Container提供的回调方法
    /**
     * 在后端调用，回调客户端执行相关的Ability
     * 
     * 【注】该方法只能在监听器等前端调用到后端的响应线程中使用，其是利用了ThreadLocal获取调入方的通讯信道进行回调，如果到其它线程里无法调用
     * 
     * 如果要
     * 
     * @param callback
     *            : 要执行的回调对象
     */
    public Object callback(PanelCallback callback) throws Exception
    {
        callback.setPanelGlobalKey(getCurrentPanelGlobalKey());
        callback.setTargetPanelPath(getTargetPanelPath());
        
        CRpcCallbackCmd cmd = new CRpcCallbackCmd();
        cmd.setTimeout(callback.getTimeout());
        cmd.setData(callback);

        return FeTracker.callback(prepareChannel(), cmd);
    }

    /**
     * 这个是信道资源有效期, 长时间不使用信道会被系统回收释放(默认1分钟)
     * 设置为-1则永远不会被释放(必须手动close)
     * 
     * 这个和timeout不一样, timeout是单次回调的超时时长
     */
    public void setChannelExpireMs(long expireMs)
    {
        prepareChannel().setExpireTime(expireMs);
    }
    /**
     * 信道改造为资源型CELL后, 此方法废弃
     * 包含信道的PanelContext可以被随意到处传递, 不再需要export
     * 但是, "切记", 默认的信道有效期为两分钟, 参考CWsCallbackChannel::CWS_CALLBACK_CHANNEL_EXPIRE_MS, 长时间无调用会被回收关闭
     * 所以建议用完自行close
     */
    @Deprecated
    public PanelContext exportThread() throws Exception
    {
        return this;
    }
    
    public PanelContext cloneWithChannel()
    {
        return (PanelContext) CsonUtil.clone(this).setChannel(getChannel());
    }

    public IMapCell<String, Object> getOrCreatePanelCache()
    {
        return PanelCacheUtil.getOrCreatePanelCache(this);
    }

    public IMapCell<String, Object> getPanelCache()
    {
        return PanelCacheUtil.getPanelCache(this);
    }
    
    public void putPanelCache(String key, Object value)
    {
        getOrCreatePanelCache().putValue(key, value);
    }
    
    public Object getPanelCacheValue(String key)
    {
        IMapCell<String, Object> cache = getPanelCache();
        if (cache == null)
            return null;
        
        return cache.getValue(key);
    }
    
    public Object removePanelCacheValue(String key)
    {
        IMapCell<String, Object> cache = getPanelCache();
        if (cache == null)
            return null;
        
        return cache.remove(key);
    }
    
    public boolean hasPanelCache()
    {
        return PanelCacheUtil.hasPanelCache(this);
    }

    public int getPanelCacheSize()
    {
        IMapCell<String, Object> cache = getPanelCache();
        if (cache == null)
            return 0;
        
        return cache.size();
    }

    // 删除(并释放)当前面板缓存, 返回true表示真的存在并删除成功, 如果不存在缓存等情况返回false
    public boolean deletePanelCache()
    {
        return PanelCacheUtil.deletePanelCache(this);
    }
}
