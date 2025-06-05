package fe.util.component.param;

import fe.util.component.dto.TableSetting;

import java.util.Arrays;
import java.util.List;

public class TableParam extends BasicTableParam {
	public final static List<Integer> defaultPageSizes = Arrays.asList(10, 20, 50, 100);
	/**
	 *
	 */
	private static final long serialVersionUID = -2133367508867817821L;
	String filtersKeyWord;
	Integer startPos;
	Integer pageSize;
	boolean queryCount;
	List<Integer> pageSizes = null;

//	public static TableParam defaultParam(){
//		TableParam tableParam = new TableParam();
//		tableParam.setStartPos(0).setPageSize(10).setQueryCount(true).setPageSizes(defaultPageSizes);
//		return tableParam;
//	}

	public TableParam defaultParam() {
		setStartPos(0).setPageSize(20).setQueryCount(true).setPageSizes(defaultPageSizes);
		return this;
	}

	public TableParam() {
		getSetting().setIsAllowRefresh(true);
	}

	public String getFiltersKeyWord() {
		return filtersKeyWord;
	}

	public TableParam setFiltersKeyWord(String filtersKeyWord) {
		this.filtersKeyWord = filtersKeyWord;
		return this;
	}

	public Integer getStartPos() {
		return startPos;
	}

	public TableParam setStartPos(Integer startPos) {
		this.startPos = startPos;
		return this;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public TableParam setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public boolean isQueryCount() {
		return queryCount;
	}

	public TableParam setQueryCount(boolean queryCount) {
		this.queryCount = queryCount;
		return this;
	}

	public List<Integer> getPageSizes() {
		return pageSizes;
	}

	public TableParam setPageSizes(List<Integer> pageSizes) {
		this.pageSizes = pageSizes;
		return this;
	}

	@Override
	public TableParam setWritable(boolean isWritable) {
		return (TableParam) super.setWritable(isWritable);
	}

	@Override
	public TableParam setSetting(TableSetting setting) {
		super.setSetting(setting);
		return this;
	}
}
