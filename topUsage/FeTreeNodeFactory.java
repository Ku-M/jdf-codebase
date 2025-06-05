package fe.util.component.tree;

import fe.cmn.app.Context;
import fe.cmn.tree.TreeInterface;
import fe.cmn.tree.TreeNodeDto;
import fe.util.component.dto.TreeNodeExtraInfo;
import fe.util.intf.ServiceIntf;

public class FeTreeNodeFactory {
	
	public static FeTreeNodeFactory create() {
		return new FeTreeNodeFactory();
	}
	
	public FeTreeNodeProcessor[] nodeProcessors = {new EmptyFeNodeProc()
			}; 
	
	public FeTreeNodeProcessor[] getNodeProcessors() {
		return nodeProcessors;
	}
	/**
	 * 设置树节点处理类
	 * @param nodeProcessors
	 */
	public void setNodeProcessors(FeTreeNodeProcessor[] nodeProcessors) {
		this.nodeProcessors = nodeProcessors;
	}
	/**
	 * 根据节点类型获取节点处理器
	 * @param context
	 * @param nodeType
	 * @param treePanel
	 * @param serviceCell
	 * @return
	 * @throws Exception
	 */
	public FeTreeNodeProcessor getTreeNodeProcessor(Context context,String nodeType,TreeInterface treePanel,Class<? extends ServiceIntf> serviceCell) throws Exception {
		FeTreeNodeProcessor proc = null;
		for(FeTreeNodeProcessor nodeProc : nodeProcessors) {
			if(nodeProc.isMatch(nodeType)) {
				proc = nodeProc;
				break;
			}
		}
		if(proc == null) {
			System.err.println("Not suport for nodeType " + nodeType);
			proc = new EmptyFeNodeProc();
		}
		proc.setFactory(this);
		proc.setTreePanel(treePanel);
		proc.setServiceCell(serviceCell);
		proc.setContext(context);
		return proc;
	}
	/**
	 * 根据树节点获取节点处理器
	 * @param context
	 * @param node
	 * @param treePanel
	 * @param serviceCell
	 * @return
	 * @throws Exception
	 */
	public FeTreeNodeProcessor getTreeNodeProcessor(Context context,TreeNodeDto node,TreeInterface treePanel,Class<? extends ServiceIntf> serviceCell) throws Exception {
		TreeNodeExtraInfo extraInfo = (TreeNodeExtraInfo) node.getBinaryData();
		return getTreeNodeProcessor(context,extraInfo.getNodeType(),treePanel,serviceCell);
	}
}
