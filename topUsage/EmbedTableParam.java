package fe.util.component.param;

import fe.util.component.dto.TableSetting;

import java.util.List;

/**
 * @param <T>
 * @author chenxb
 */
public class EmbedTableParam<T> extends BasicTableParam {

	/**
	 *
	 */
	private static final long serialVersionUID = 3557171139598858213L;
	List<T> rows;

	public static <T> EmbedTableParam<T> create(List<T> rows) {
		EmbedTableParam<T> tEmbedTableParam = new EmbedTableParam<>();
		tEmbedTableParam.getSetting().setIsOpShowPopToast(false);
		return tEmbedTableParam.setRows(rows);
	}

	public List<T> getRows() {
		return rows;
	}

	public EmbedTableParam<T> setRows(List<T> rows) {
		this.rows = rows;
		return this;
	}

	public EmbedTableParam<T> setWritable(boolean isWritable) {
		this.isWritable = isWritable;
		return this;
	}

	public EmbedTableParam setSetting(TableSetting setting) {
		this.setting = setting;
		return this;
	}
}
