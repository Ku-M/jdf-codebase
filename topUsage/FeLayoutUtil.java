package fe.util;
/**
 * 布局工具类
 * @author chenxb
 *
 */

import java.util.List;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.panel.BoxDto;
import fe.cmn.panel.BoxMainAxisSize;
import fe.cmn.panel.ScrollBoxDto;
import fe.cmn.widget.ButtonDto;
/**
 * 界面布局辅助工具类
 * @author chenxb
 *
 */
public class FeLayoutUtil {
	
	/**
	 * 返回带默认参数的滚动条
	 * @param mainBox
	 * @return
	 */
	public static ScrollBoxDto wrapScorllBox(BoxDto mainBox) {
		mainBox.setMainAxisSize(BoxMainAxisSize.min);
		ScrollBoxDto scrollBox = ScrollBoxDto.wrap(mainBox);
		scrollBox.setChildMaxHeight(9999);
		return scrollBox;
	}
	/**
	 * 计算行操作按钮的宽度
	 * @param fontSize
	 * @param rowOperateButton
	 * @return
	 * @throws Exception 
	 */
	public static int caculateRowOperateWidth(double fontSize,List<ButtonDto> rowOperateButton) throws Exception {
		double maxOpBtnWidth = 0;
		for(ButtonDto button : rowOperateButton) {
			int btnWidth = 0;
			if(!CmnUtil.isStringEmpty(button.getIcon())) {
				btnWidth +=fontSize+10;
			}
			if(!CmnUtil.isStringEmpty(button.getText())) {
				double fontCnt = caculateLabelFontCnt(button.getText());
				btnWidth+=fontCnt*fontSize;
			}
			maxOpBtnWidth+=btnWidth+50;
		}
		return CmnUtil.getInteger(Math.ceil(maxOpBtnWidth));
	}
	
	/**
	 * 计算标签的字符数量，中文算1个长度，英文算半个
	 * @param label
	 * @return
	 */
	public static double caculateLabelFontCnt(String label) {
		double cnt =0;
		for(int i =0;i<label.length();i++) {
			char c = label.charAt(i);
			if(CmnUtil.isChinese(c)) {
				cnt+=1;
			}else {
				cnt+=0.5;
			}
		}
		return cnt;
	}
}
