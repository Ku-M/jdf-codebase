package fe.cmn.tree;

import cmn.util.Nulls;
import fe.cmn.data.FePojo;

public class TreeNodeQuerier extends FePojo {

	private static final long serialVersionUID = 5522185194429959550L;

	String parentKey;

	public String getParentKey() {
		return parentKey;
	}

	public TreeNodeQuerier setParentKey(String parentKey) {
		this.parentKey = parentKey;
		return this;
	}
	
	public boolean hasParent()
	{
	    return Nulls.isNotEmpty(getParentKey());
	}

	public TreeNodeQuerier setSelfBinaryData() {
		setBinaryDataIgnoreErr(this);
		return this;
	}
}
