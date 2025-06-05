package fe.util.editor.valuehanlder;

import fe.cmn.data.PairDto;

public class SelectEditorHandler extends DefaultEditorHandler {

	/**
	 *
	 */
	private static final long serialVersionUID = 5026620641615040010L;

	@SuppressWarnings("rawtypes")
	@Override
	public void handler(Object object, String widgetId, EditorFieldDefine fieldDef, Object guiValue) throws Exception {
		PairDto pair = (PairDto) guiValue;
		setFieldValue(object, widgetId, pair.getKey());
	}

}
