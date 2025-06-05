package fe.cmn.tree;

import fe.cmn.data.FePojo;
import fe.cmn.panel.PanelContext;

/*
 * 树查询上下文
 */
public class TreeNodeQuerierContext extends PanelContext {

	private static final long serialVersionUID = -747192282045113683L;
	
	// 树FePojo
	FePojo treeFepojo;
	
	// 父节点FePojo
	FePojo parentFepojo;

	public FePojo getTreeFepojo() {
		return treeFepojo;
	}

	public TreeNodeQuerierContext setTreeFepojo(FePojo treeFepojo) {
		this.treeFepojo = treeFepojo;
		return this;
	}

	public FePojo getParentFepojo() {
		return parentFepojo;
	}

	public TreeNodeQuerierContext setParentFepojo(FePojo parentFepojo) {
		this.parentFepojo = parentFepojo;
		return this;
	}
}
