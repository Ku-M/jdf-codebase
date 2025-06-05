package fe.util.editor.valuehanlder;

import java.io.IOException;
import java.io.Serializable;

import com.leavay.common.util.ToolBasic;
import com.leavay.common.util.javac.ClassFactory;

import fe.util.editor.define.EmbedPanelDefine;

public class EditorFieldDefine implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2567078996073668877L;
	/**
	 * 英文名
	 */
	String name;
	/**
	 * 中文名
	 */
	String label;
	/**
	 * 是否必填
	 */
	boolean require = false;
	/**
	 * 界面值转Dto值处理类
	 */
	Class<? extends EditorTypeHandler> editorTypeHandler = DefaultEditorHandler.class;
	/**
	 * 嵌入表单定义
	 */
	EmbedPanelDefine embedPanelDefine;
	/**
	 * 附带的二进制数据
	 */
	byte[] binaryData;

	public EditorFieldDefine() {
	}

	public EditorFieldDefine(String name, String label, boolean require) {
		this(name, label, require, DefaultEditorHandler.class);
	}

	public EditorFieldDefine(String name, String label, boolean require, Class<? extends EditorTypeHandler> editorHandler) {
		this.name = name;
		this.label = label;
		this.require = require;
		this.editorTypeHandler = editorHandler;
	}

	public String getName() {
		return name;
	}

	public EditorFieldDefine setName(String name) {
		this.name = name;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public EditorFieldDefine setLabel(String label) {
		this.label = label;
		return this;
	}

	public boolean isRequire() {
		return require;
	}

	public EditorFieldDefine setRequire(boolean require) {
		this.require = require;
		return this;
	}

	public Class<? extends EditorTypeHandler> getEditorTypeHandler() {
		return editorTypeHandler;
	}

	public EditorFieldDefine setEditorTypeHandler(Class<? extends EditorTypeHandler> editorTypeHandler) {
		this.editorTypeHandler = editorTypeHandler;
		return this;
	}

	public EmbedPanelDefine getEmbedPanelDefine() {
		return embedPanelDefine;
	}

	public EditorFieldDefine setEmbedPanelDefine(EmbedPanelDefine embedPanelDefine) {
		this.embedPanelDefine = embedPanelDefine;
		return this;
	}
	
	@Override
	public String toString() {
		return label + "(" + name + "):" + editorTypeHandler;
	}
	
	public EditorFieldDefine setBinaryDataIgnoreErr(Object binaryData)
    {
        try
        {
            setBinaryData((Serializable) binaryData);
        } catch (Exception e)
        {
            ToolBasic.throwRuntimeException(e);
        }
        return this;
    }

    public Object getBinaryData() throws ClassNotFoundException, IOException
    {
        return binaryData == null ? null : ClassFactory.unserialize(binaryData);
    }

    // 设置附属数据，可以是任何对象（Flutter端不解析），通过序列化透传
    public EditorFieldDefine setBinaryData(Serializable binaryData) throws IOException
    {
        this.binaryData = binaryData == null ? null : ToolBasic.serialize(binaryData);
        return this;
    }
    
    // 绕过序列化、反序列化
    public EditorFieldDefine setBinaryBytes(byte[] bytes)
    {
        this.binaryData = bytes;
        return this;
    }
}
