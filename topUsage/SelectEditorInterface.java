package fe.cmn.editor;

import java.util.List;

import fe.cmn.data.PairDto;
import flutter.coder.annt.AbstractVirtual;

/**
 * 下拉框服务接口
 */
@AbstractVirtual
public interface SelectEditorInterface {
	/**
	 * 请求下拉选项数据。
	 * 
	 * <p>SelectEditorDto设置panelService后，前端则会调用请求。
	 * 
	 * @param querier 请求器
	 * @param context 界面上下文
	 * @return 返回选项列表
	 * @throws Exception
	 */
	List<PairDto> querySelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context) throws Exception;
	
	/**
	 * 前端带着输入值请求搜索下拉选项数据。
	 * 
	 * <p>SelectEditorDto设置remoteFilter及panelService后，前端则会调用请求。
	 * 
	 * @param querier 请求器
	 * @param context 界面上下文
	 * @return 返回选项列表
	 * @throws Exception
	 */
	List<PairDto> filterSelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context) throws Exception;
}
