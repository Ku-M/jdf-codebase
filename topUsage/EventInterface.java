package fe.cmn.event;

import fe.cmn.panel.PanelContext;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.AbstractVirtual;

@AbstractVirtual
public interface EventInterface {
	/**
	 * 由前端调用下来，当收到订阅的消息时，将前端当前的上下文、消息体、收到的组件等信息发送下来，以便后端实现响应
	 *
	 * @param event                                        ：收到的订阅消息
	 * @param panelContext：所在面板的上下文，提供回调、上下游组件检索、空间定位等重要功能
	 * @param source：触发该动作的源头组件对象
	 */
    void onEvent(EventDto event, PanelContext panelContext, WidgetDto source) throws Exception;
}
