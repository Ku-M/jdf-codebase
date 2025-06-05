package fe.cmn.panel.ability;

import fe.cmn.data.BasicAbility;
import fe.cmn.event.EventDto;
import fe.cmn.panel.PanelContext;
import flutter.coder.annt.NullSafe;

/**
 * 向前端发起广播事件
 */
public class FireEvent extends BasicAbility<Void>
{
    private static final long serialVersionUID = 8378952173962675397L;
    
    @NullSafe
    EventDto[] events;

    public EventDto[] getEvents()
    {
        return events;
    }

    public void setEvents(EventDto[] events)
    {
        this.events = events;
    }
    
    public static void fire(PanelContext panelContext, EventDto ... events) throws Exception
    {
        FireEvent callback = new FireEvent();
        callback.setEvents(events);
        callback.execute(panelContext);
    }
}
