package fe.cmn.table;

import fe.cmn.data.FePojo;
import fe.cmn.panel.PanelContext;

/**
 * 
 * 表格查询上下文
 *
 */
public class TableQuerierContext extends PanelContext {

	private static final long serialVersionUID = 5288767402664052614L;
	
	FePojo tableFePojo;

	public FePojo getTableFePojo() {
		return tableFePojo;
	}

	public TableQuerierContext setTableFePojo(FePojo tableFePojo) {
		this.tableFePojo = tableFePojo;
		return this;
	}
}
