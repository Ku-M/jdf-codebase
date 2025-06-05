package fe.cmn.panel.ability;

import fe.cmn.data.BasicAbility;
import fe.cmn.panel.PanelContext;
import flutter.coder.annt.NullSafe;

// 根据给的PanelContext.targetPanelPath转换返回新的PanelContext，转换失败返回null
public class ConvertPanelContext extends BasicAbility<PanelContext>
{
	private static final long serialVersionUID = 1627601412836163465L;
	
	@NullSafe
	String convertPanelPath;

	public String getConvertPanelPath() {
		return convertPanelPath;
	}

	public ConvertPanelContext setConvertPanelPath(String convertPanelPath) {
		this.convertPanelPath = convertPanelPath;
		return this;
	}

	public static PanelContext convert(PanelContext ctx, String convertPanelPath) throws Exception
    {
        ConvertPanelContext callback = new ConvertPanelContext().setConvertPanelPath(convertPanelPath);
        Object res = ctx.callback(callback);
        return res instanceof PanelContext ? (PanelContext) res : null;
    }
}
