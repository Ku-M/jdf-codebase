package gpf.dc.intf;

import java.io.Serializable;

import cell.CellIntf;
import crpc.CRpcContainerIntf;

/**
 * 声明为功能函数必须实现此接口,功能函数的开发样例：
 *  
 * 功能函数中必须带有process名称的函数，通过该函数名称获取相关的功能接口元数据信息
 * 调用方式：
 * Function xx = Cells.get(xxxx);
 * ParamIntf input = xxx;
 * output = xx.process(input);
 * @author chenxb
 *
 */
public interface FunctionIntf extends CellIntf,CRpcContainerIntf,Serializable{
	public final static String DECLARE_METHOD = "process";

//	/**
//	 * 
//	 * @param input
//	 * @return
//	 * @throws Exception
//	 */
//	public O process(I input)throws Exception;
}
