package gpf.dc.config;

import com.kwaidoo.ms.tool.CmnUtil;

import bap.cells.Cells;
import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.gpf.dc.config.IPDCMgr;
import gpf.dc.concrete.RefNode;

public class RefPDCNode extends RefNode<PDC>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4418318685986786642L;
	PDC data;
	protected IDaoService getDaoService() {
		return Cells.get(IDaoService.class);
	}
	@Override
	public PDC getData() throws Exception {
		if(data == null) {
			if(!CmnUtil.isStringEmpty(getRefModelID()) && !CmnUtil.isStringEmpty(getRefInstID())) {
				try(IDao dao = getDaoService().newDao()){
					data = IPDCMgr.get().queryPDC(dao,getRefModelID(), getRefInstID());
				}
			}
		}
		return data;
	}

	@Override
	public RefPDCNode setData(PDC data) {
		this.data = data;
		setRefModelID(data.getFormModelId());
		setRefInstID(data.getUuid());
		setRefInstModified(true);
		return this;
	}

	
}
