package cell.jit;

import cell.CellIntf;
import cmn.anotation.ClassDeclare;

/**
 * 动作接口
 * 实现此接口声明动作
 * @author chenxb
 *
 */
@ClassDeclare(label = "JIT动作接口"
,what="通过实现此接口声明为可调用的定制动作接口"
, why = "保证通用动作模型支撑功能的完备性，提供在通用动作模型中调用定制代码的封装"
, how = "在定制动作代码类实现此接口，调用方法上添加@ActionMethod 和 @ActionMethodParams注解"
,developer="陈晓斌"
,version = "1.0"
,createTime = "2024-11-11"
,updateTime = "2024-11-11")
public interface ActionIntf extends CellIntf{
	
}
