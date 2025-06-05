package fe.cmn.editor;

import fe.cmn.data.FePojo;
import fe.cmn.panel.PanelContext;

public class SelectEditorQuerierContext extends EditorServiceContext<Object> {

	private static final long serialVersionUID = 7429499983683996612L;
	
	// SelectEditorDto下的数据存储
	FePojo selectEditorFePojo;
	
	// 当前值，单选为PairDto，多选为List<PairDto>
	Object value;

	public FePojo getSelectEditorFePojo() {
		return selectEditorFePojo;
	}
	
	public Object getValue() {
		return value;
	}

	@Override
	public Object getEditorValue() {
		return value;
	}
}
