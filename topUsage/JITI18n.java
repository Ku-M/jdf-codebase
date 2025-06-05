package jit.i18n;

import cmn.i18n.AbsI18n;

public class JITI18n extends AbsI18n{
	
	private static JITI18n inst = new JITI18n();
	
	public final static String PLEASE_SELECT = get().format("Please select");
	
	public static JITI18n get() {
		return inst;
	}

	@Override
	public String getResourceFileName() {
		return "jit_i18n.setting";
	}

}
