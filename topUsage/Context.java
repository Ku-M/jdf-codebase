package fe.cmn.app;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.leavay.common.gson.annotations.Expose;
import com.leavay.common.nio.ws.cache.CWsChannelCache;
import com.leavay.common.nio.ws.cache.InvalidCacheException;
import com.leavay.common.util.TimeoutException;
import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cell.cmn.cache.IMapCell;
import cell.nio.ws.CWsCallbackChannel;
import cell.nio.ws.IWsCallbackChannel;
import crpc.CRpcContainerIntf;
import cson.CsonUtil;
import cson.core.CsonPojo;
import fe.cmn.mgr.PanelCacheUtil;
import flutter.coder.annt.IgnoreField;
import flutter.coder.annt.NullSafe;

/**
 * 前端发给后端的项目信息
 * 
* PanelContext内含远程信道对象, 不可复制, 不能自行序列化反序列化. 
*/
public class Context extends CsonPojo implements CRpcContainerIntf {

    private static final long serialVersionUID = 7197417085975761246L;

    // 前端标识(前端启动时生成的UUID,用以唯一标识前端应用/页面)
    // 每次由前端下发
    @NullSafe(initCode = "Cson.getGlobalAppUuid()")
    String appUuid;
    
    // 用户名（唯一用户code）
    // 此字段为前端传递给后端（由跳转页面时传入）
    // 来自前端，只读
    String currentUser;
    
    Object appInfo;
    
    // 是否为内嵌页面
    boolean isEmbedPage;
    
    long reqId;
    
    /**
     * 是否存在JDV页面。
     */
    boolean hasJDVPage;
    
    /**
     * JDV启动JDF初始消息数据。
     */
    Object initialMessageFromJDV;
    
    // 小程序端启动参数中的scene字段
    String sceneFromWxMiniParams;
    
    public Context() {
        
    }
    
    public String getAppUuid()
    {
        return appUuid;
    }

    public String getCurrentUser()
    {
        return currentUser;
    }

    public Object getAppInfo() {
        return appInfo;
    }

    public boolean isEmbedPage() {
        return isEmbedPage;
    }

    public long getReqId() {
		return reqId;
	}

    public boolean isHasJDVPage() {
		return hasJDVPage;
	}

	public Object getInitialMessageFromJDV() {
		return initialMessageFromJDV;
	}

	public String getSceneFromWxMiniParams() {
		return sceneFromWxMiniParams;
	}

	// 前端通讯信道(资源CELL),支持多机云联调
    // 前端无此字段,后端相互传递时带着走(需要@CRpcContainer才能以CELL的方式带着走)
    @IgnoreField
    @Expose(serialize = false, deserialize = false) 
    IWsCallbackChannel _channel;

    public static boolean debugChannel = false;
    @Override
    public void afterCsonDecode(Object jsonObj, Type typeOfT)
    {
        // 通过Json解析(来自客户端一定都是构造时如果没有信道, 则默认取当前线程的channel
        if (_channel == null)
        {
            if (debugChannel)
                System.err.println("INIT PanelContext Channel=====>\n"+ToolUtilities.getCurrentStack());
            
            _channel = CWsCallbackChannel.buildFromThread(getAppUuid());
        }
    }
    
    public IWsCallbackChannel getChannel()
    {
        return _channel;
    }
    
    public Context setChannel(IWsCallbackChannel channel)
    {
        _channel = channel;
        return this;
    }
    
    public IWsCallbackChannel prepareChannel()
    {
        return CmnUtil.assertNotNull(_channel, "There isn't valid connection channel");
    }
    
    public String prepareAppUuid()
    {
        return CmnUtil.assertNotNull(getAppUuid(), "There isn't valid application UUID");
    }
    
    public Context cloneWithChannel()
    {
        return CsonUtil.clone(this).setChannel(getChannel());
    }

    // 主动关闭, 及时释放相关资源, 避免堆积
    public void close()
    {
        prepareChannel().close();
    }

    public Object getCacheValue(String valueKey) throws InvalidCacheException
    {
        return prepareChannel().getCacheValue(valueKey);
    }
    
    public Object tryGetCacheValue(String valueKey)
    {
        return prepareChannel().tryGetCacheValue(valueKey);
    }

    public int getCacheSize() throws InvalidCacheException
    {
        return prepareChannel().getCacheSize();
    }

    public int tryGetCacheSize() throws InvalidCacheException
    {
        return prepareChannel().tryGetCacheSize();
    }
    
    public boolean containCacheKey(String key) throws InvalidCacheException
    {
        return prepareChannel().containCacheKey(key);
    }
    
    public boolean tryContainCacheKey(String key)
    {
        return prepareChannel().tryContainCacheKey(key);
    }
    
    public boolean isCacheEmpty() throws InvalidCacheException
    {
        return prepareChannel().isCacheEmpty();
    }

    public boolean isCachePresent()
    {
        return prepareChannel().isCachePresent();
    }
    
    public Object removeCacheValue(String valueKey) throws InvalidCacheException
    {
        return prepareChannel().removeCacheValue(valueKey);
    }

    public Object tryRemoveCacheValue(String valueKey)
    {
        return prepareChannel().tryRemoveCacheValue(valueKey);
    }
    
    public void clearCache()
    {
        prepareChannel().clearCache();
    }
    
    public void putCacheValue(String valueKey, Object value)
    {
        prepareChannel().putCacheValue(valueKey, value);
    }

    public Set<String> getAllCacheKeys() throws InvalidCacheException
    {
        return prepareChannel().getAllCacheKeys();
    }
    
    public Set<String> tryGetAllCacheKeys()
    {
        return prepareChannel().tryGetAllCacheKeys();
    }
    
    public Map<String, Object> peekAllCache() throws InvalidCacheException
    {
        return prepareChannel().peekAllCache();
    }

    
    public Map<String, Object> tryPeekAllCache()
    {
        return prepareChannel().tryPeekAllCache();
    }

    public CWsChannelCache getCache() throws InvalidCacheException
    {
        return prepareChannel().getCache();
    }

    public void setCacheExpireSecond(int second) throws InvalidCacheException
    {
        prepareChannel().setCacheExpireSecond(second);
    }
    
    public IMapCell<String, Object> getOrCreatePanelCache(String panelGlobalKey)
    {
        try
        {
            return PanelCacheUtil.getOrCreatePanelCache(this, panelGlobalKey);
        } catch (Throwable exp)
        {
            ToolUtilities.throwRuntimeException(exp);
            return null;
        }
    }
    
    public List<String> getChannelInfo()
    {
        return prepareChannel().getChannelInfoOfCache();
    }
    
    @Override
    public String toString()
    {
        return super.toString()+" : Channel="+_channel;
    }
}
