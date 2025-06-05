package fe.cmn.panel.ability;

import com.leavay.common.util.ToolUtilities;

import fe.cmn.data.BasicAbility;
import fe.cmn.panel.PanelContext;
import flutter.coder.annt.NullSafe;

/**
 * 调用此能力，可以回退当前页面的路由
 * 例如当前是一个弹出界面、弹出对话框等，则会关闭
 * 
 * 返回：当前回退界面(前提是调用JumpToPage等能力进入的)或者弹窗抽屉的源PanelContext
 * 
 * 【注意】RootPopDialog、RootPopDrawer不会返回PanelContext
 * 
 */
public class QuitPopup extends BasicAbility<PanelContext>
{
    private static final long serialVersionUID = 1449955731690934220L;
    
    // 是否执行前端设置的回退前回调
    @NullSafe(initCode="true")
    Boolean executePopCallback = true;
    
    // 携带当前JAVA端堆栈, 以便当弹出的面板(子面板)找不到时, 能追溯回关闭的源头
    String quitStack;
    
    public String getQuitStack()
    {
        return quitStack;
    }

    public QuitPopup setQuitStack(String quitStack)
    {
        this.quitStack = quitStack;
        return this;
    }
    
    public Boolean getExecutePopCallback() {
		return executePopCallback;
	}

	public QuitPopup setExecutePopCallback(Boolean executePopCallback) {
		this.executePopCallback = executePopCallback;
		return this;
	}

	public static PanelContext quit(PanelContext context) throws Exception
    {
        QuitPopup quit = new QuitPopup();
        return (PanelContext)context.callback(quit);
    }
    
    public static PanelContext quit(PanelContext context, Boolean executePopCallback) throws Exception
    {
        QuitPopup quit = new QuitPopup().setExecutePopCallback(executePopCallback);
        return (PanelContext)context.callback(quit);
    }
    
    public static PanelContext quitWithStack(PanelContext context) throws Exception
    {
        QuitPopup quit = new QuitPopup();
        quit.setQuitStack(ToolUtilities.getCurrentStack());
        return (PanelContext)context.callback(quit);
    }
    
    public static PanelContext quitWithStack(PanelContext context, Boolean executePopCallback) throws Exception
    {
        QuitPopup quit = new QuitPopup().setExecutePopCallback(executePopCallback);
        quit.setQuitStack(ToolUtilities.getCurrentStack());
        return (PanelContext)context.callback(quit);
    }
}
