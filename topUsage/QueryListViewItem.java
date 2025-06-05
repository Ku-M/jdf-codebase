package fe.cmn.listView.ability;

import java.util.List;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.BasicAbility;
import fe.cmn.data.FePojo;
import fe.cmn.listView.ListViewItemDto;
import fe.cmn.panel.PanelContext;
import flutter.coder.annt.NullSafe;

/**
 * 
 * 查询ListViewItem
 *
 */

public class QueryListViewItem extends BasicAbility<List> {

	private static final long serialVersionUID = -8850035426793134883L;
	
	String key;
	
	@NullSafe(initCode="true")
	Boolean queryCompelete = true;
	
	public String getKey() {
		return key;
	}

	public QueryListViewItem setKey(String key) {
		this.key = key;
		return this;
	}

	public Boolean getQueryCompelete() {
		return queryCompelete;
	}

	public QueryListViewItem setQueryCompelete(Boolean queryCompelete) {
		this.queryCompelete = queryCompelete;
		return this;
	}

	public static ListViewItemDto query(PanelContext context, String key) throws Exception {
		QueryListViewItem callback = new QueryListViewItem().setKey(key);
		List<ListViewItemDto> list = (List<ListViewItemDto>) context.callback(callback);
		return CmnUtil.isObjectEmpty(list)?null:list.get(0);
	}
	
	public static FePojo queryFePojo(PanelContext context, String key) throws Exception {
		QueryListViewItem callback = new QueryListViewItem().setKey(key).setQueryCompelete(false);
		List<FePojo> list = (List<FePojo>) context.callback(callback);
		return CmnUtil.isObjectEmpty(list)?null:list.get(0);
	}
	
	public static List<ListViewItemDto> queryAll(PanelContext context) throws Exception {
		QueryListViewItem callback = new QueryListViewItem();
		List<ListViewItemDto> list = (List<ListViewItemDto>) context.callback(callback);
		return list;
	}
	
	public static List<FePojo> queryAllFePojo(PanelContext context) throws Exception {
		QueryListViewItem callback = new QueryListViewItem().setQueryCompelete(false);
		List<FePojo> list = (List<FePojo>) context.callback(callback);
		return list;
	}
}
