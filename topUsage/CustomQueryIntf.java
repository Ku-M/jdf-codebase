package gpf.dc.basic.action.intf;

import gpf.dc.basic.fe.component.BaseFeActionIntf;
import gpf.dc.basic.param.view.CustomQueryParameter;
/**
 * 自定义查询接口，查询表格数据返回ResultSet，查询树列表返回List，查询单个表单返回表单数据
 * @author chenxb
 *
 * @param <T>
 */
public interface CustomQueryIntf<T extends CustomQueryParameter> extends BaseFeActionIntf<T>{

}
