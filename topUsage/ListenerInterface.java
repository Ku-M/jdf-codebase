package fe.cmn.widget;

import fe.cmn.panel.PanelContext;
import flutter.coder.annt.AbstractVirtual;
import flutter.coder.annt.NullReturn;

@AbstractVirtual
public interface ListenerInterface
{
    /**
     * 由前端调用下来，当触发某监听器时，将前端当前的上下文、触发源等信息发送下来，以便后端实现响应
     * 
     * @param listener ：监听器对象（可能携带前端的相关数据）
     * @param panelContext：所在面板的上下文，提供回调、上下游组件检索、空间定位等重要功能
     * @param source：触发该动作的源头组件对象（只有监听器中设置了bringbackSourceWidget=true时，才会传值，否则为null）
     * 
     * @return 只有极少数情况需要前端处理返回结果，只有满足这两个条件才会处理结果：监听器必须是同步触发 + 执行器必须是后端调用(ServerListenerExecutorDto类型)
     */
    @NullReturn
    public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception;
}
