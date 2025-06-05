package fe.cmn.editor;

import fe.cmn.data.FePojo;

// 下拉框查询器
public class SelectEditorQuerier extends FePojo {

	private static final long serialVersionUID = -126901509638786676L;
	
	String keyWord;
	
	public SelectEditorQuerier() {
		
	}
	
	public String getKeyWord() {
		return this.keyWord;
	}
}
