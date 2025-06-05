package cell.fe.progress;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.leavay.client.util.lazy.LazyPool;
import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import bap.cells.BasicCell;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.editor.ability.AppendTextFieldValue;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.QuitPopup;
import fe.cmn.panel.ability.SetChildVisible;
import fe.cmn.panel.ability.SetEditorValue;
import fe.cmn.progress.ProgressCtrl;
import fe.util.dto.progress.FeProgressCtrlContextDto;
import fe.util.exception.handler.ExceptionHandleResult;
import fe.util.exception.handler.ExceptionHandlerFactory;

// 包裹一个后端的进度对象为Cell，然后适配到Fe端的进度控制器
public class CFeProgressCtrlWithTextArea extends BasicCell implements IFeProgressCtrlWithTextArea {

	public final static String LOG = CFeProgressCtrlWithTextArea.class.getSimpleName();
	/**
	 *
	 */
	private static final long serialVersionUID = 212261066830617495L;

	private final String _ctrlUuid = CmnUtil.allocUUIDWithUnderline();
	// 缓存最近发送的进度（绝对值，非百分比）
	private int _cacheProc = 0;
	// 进度条组件上下文信息
	private FeProgressCtrlContextDto ctrlContextDto = new FeProgressCtrlContextDto();
	//是否存在报错信息
	private ExceptionHandleResult handlerResult;
	private int lineCnt = 0;
	private StringBuffer cacheMsg = new StringBuffer();
	
	private LazyPool<Long> lazyPool = new LazyPool<Long>(1000) {

		@Override
		public void handle(List<Long> lstData) {
			if(cacheMsg.length() > 0) {
				String msg = cacheMsg.toString();
				cacheMsg.setLength(0);
				PanelContext panelContext = ctrlContextDto.getPanelContext();
				String messageWidgetId = ctrlContextDto.getMessageWidgetId();
				if (panelContext != null && !CmnUtil.isStringEmpty(messageWidgetId)) {
					try {
						AppendTextFieldValue.append(panelContext, messageWidgetId, msg);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	};

	public CFeProgressCtrlWithTextArea() {
		setTimeout(12*60*60*1000L);
	}

	public CFeProgressCtrlWithTextArea(FeProgressCtrlContextDto ctrlContextDto) {
		try {
			this.ctrlContextDto = ctrlContextDto;
			// 只要构造，就认为启动了，前端访问下来就不会扑空报错
			getProgService().sendProgressWithExpireTime(getCtrlUuid(), 0, "",ctrlContextDto.getProgressBarExpireSecond());
		} catch (Throwable err) {
			ToolUtilities.warnAndOutput(LOG, "Failed to send process : " + ToolUtilities.getExceptionStackMessage(err));
		}
	}

//	public static IFeProgressService getProgService() {
//		return IFeProgressService.get();
//	}

	public FeProgressCtrlContextDto getCtrlContextDto() {
		return ctrlContextDto;
	}

	public CFeProgressCtrlWithTextArea setCtrlContextDto(FeProgressCtrlContextDto ctrlContextDto) {
		this.ctrlContextDto = ctrlContextDto;
		return this;
	}

	@Override
	public int getMinimum() {
		return getCtrlContextDto().getMin();
	}

	@Override
	public void setMinimum(int iValue) {
		getCtrlContextDto().setMin(iValue);
	}

	@Override
	public int getMaximum() {
		return getCtrlContextDto().getMax();
	}

	@Override
	public void setMaximum(int iValue) {
		getCtrlContextDto().setMax(iValue);
	}


	public String getCtrlUuid() {
		return _ctrlUuid;
	}

	// 这是传递给前端的控制器，前端利用uuid来后端获取进度信息
	public ProgressCtrl getController() {
		return ProgressCtrl.wrap(getCtrlUuid());
	}

	// 转换为发送个FE服务的进度（0-1之间的小数）
	public double calcProcess(int process) {
		return (double) process / getMaximum();
	}

	// 输出时间前缀
	public String getTimePrefix() {
		SimpleDateFormat timeFormatter = getCtrlContextDto().getTimeFormatter();
		if (timeFormatter != null) {
			return timeFormatter.format(new Date(System.currentTimeMillis())) + "  -  ";
		}
		return "";
	}

	/**
	 * 追加输出日志
	 *
	 * @param msg 输出日志
	 * @throws Exception
	 */
	public void appendMessageEditor(String msg) throws Exception {
		if (!CmnUtil.isStringEmpty(msg)) {
			FeProgressCtrlContextDto ctrlContextDto = getCtrlContextDto();
			PanelContext panelContext = ctrlContextDto.getPanelContext();
			String messageWidgetId = ctrlContextDto.getMessageWidgetId();
			if (panelContext != null && !CmnUtil.isStringEmpty(messageWidgetId)) {
				if(ctrlContextDto.getMaxTextLine() != null && lineCnt > ctrlContextDto.getMaxTextLine()) {
					SetEditorValue.set(panelContext, messageWidgetId, null);
					lineCnt = 0;
				}
				if(ctrlContextDto.isLazyAppendText()) {
					cacheMsg.append(msg);
					lazyPool.add(System.currentTimeMillis());
				}else {
					AppendTextFieldValue.append(panelContext, messageWidgetId, msg);
				}
				lineCnt++;
			}
		}
	}

	/**
	 * 清空输出日志
	 *
	 * @throws Exception
	 */
	public void clearMessageEditor() throws Exception {
		FeProgressCtrlContextDto ctrlContextDto = getCtrlContextDto();
		PanelContext panelContext = ctrlContextDto.getPanelContext();
		String messageWidgetId = ctrlContextDto.getMessageWidgetId();

		if (panelContext != null && !CmnUtil.isStringEmpty(messageWidgetId)) {
			SetEditorValue.set(panelContext, messageWidgetId, null);
		}
	}

	/**
	 * 输出进度信息
	 *
	 * @param iProcess
	 * @param sMsg
	 * @param blNewLine
	 */
	private void _doSendProcess(int iProcess, String sMsg, boolean blNewLine) {
		try {
			_cacheProc = iProcess;
			if (sMsg == null) {
				sMsg = "";
			}

			String msg = sMsg + (blNewLine ? "\n" : "");
			getProgService().sendProgress(getCtrlUuid(), calcProcess(iProcess), msg);

			if (!CmnUtil.isStringEmpty(sMsg)) {
				appendMessageEditor(getTimePrefix() + msg);
			}
		} catch (Throwable err) {
			ToolUtilities.warnAndOutput(LOG, "Failed to send process : " + ToolUtilities.getFullExceptionStack(err));
		}
	}

	@Override
	public void sendProcess(int iProcess, String sMsg, boolean blNewLine) {
		_doSendProcess(iProcess, sMsg, blNewLine);
	}

	@Override
	public void sendProcess(int iProcess, String sMsg, boolean blNewLine, Object userObject) {
		_doSendProcess(iProcess, sMsg, blNewLine); // 暂时不支持发送对象到前端
	}

	@Override
	public void setMessage(String sMsg, boolean blNewLine) {
		_doSendProcess(_cacheProc, sMsg, blNewLine); // 使用缓存的进度
	}

	@Override
	public void sendStopProcess() {
		try {
			getProgService().finish(getCtrlUuid(), "");
			if(handlerResult == null)
				quitPopup();
		} catch (Throwable err) {
			ToolUtilities.warnAndOutput(LOG, "Failed to send process : " + ToolUtilities.getExceptionStackMessage(err));
		}
	}

	@Override
	public boolean isCanceled() {
		try {
			return getProgService().isCancelByGui(getCtrlUuid());
		} catch (Exception exp) {
			ToolUtilities.throwRuntimeException(exp);
			return true;
		}
	}

	@Override
	public boolean isTerminated() {
		return isCanceled();
	}

	/**
	 * 显示错误详情
	 *
	 * @param msg 错误详情
	 * @throws Exception
	 */
	public void reportErrorDetail(String msg) throws Exception {
		FeProgressCtrlContextDto ctrlContextDto = getCtrlContextDto();
		PanelContext panelContext = ctrlContextDto.getPanelContext();
		String errorDetailWidgetId = ctrlContextDto.getErrorDetailWidgetId();
		String errorDetailBtnWidgetId = ctrlContextDto.getErrorDetailBtnWidgetId();
		String closeBtnWidgetId = ctrlContextDto.getCloseBtnWidgetId();

		if (panelContext != null && !CmnUtil.isStringEmpty(errorDetailWidgetId)) {
			SetChildVisible.set(panelContext, ImmutableMap.of(
					errorDetailBtnWidgetId, true,
					closeBtnWidgetId, true
			));
			SetEditorValue.set(panelContext, errorDetailWidgetId, msg);
		}
	}

	/**
	 * 异常中止
	 * FE 进度特有功能，显示出错信息
	 *
	 * @param msg 异常信息
	 */
	public void finishError(String msg) {
		try {
			getProgService().finishError(getCtrlUuid(), msg);
			appendMessageEditor("运行出错，已终止，点击查看错误详情" + "\n");
			reportErrorDetail(msg);
		} catch (Throwable err) {
			ToolUtilities.warnAndOutput(LOG, "Failed to send process : " + ToolUtilities.getExceptionStackMessage(err));
		}
	}
	
	/**
	 * 异常中止
	 * FE 进度特有功能，显示出错信息
	 *
	 * @param msg 异常信息
	 */
	public void finishError(Throwable e) {
		try {
			String msg = ToolUtilities.getFullExceptionMessage(e, true);
			getProgService().finishError(getCtrlUuid(), msg);
			appendMessageEditor("运行出错，已终止，点击查看错误详情" + "\n");
			reportErrorDetail(msg);
		} catch (Throwable err) {
			ToolUtilities.warnAndOutput(LOG, "Failed to send process : " + ToolUtilities.getExceptionStackMessage(err));
		}
	}

	/**
	 * 退出进度条页面
	 *
	 * @throws Exception
	 */
	public void quitPopup() throws Exception {
		FeProgressCtrlContextDto ctrlContextDto = getCtrlContextDto();
		PanelContext panelContext = ctrlContextDto.getPanelContext();
		String closeBtnWidgetId = ctrlContextDto.getCloseBtnWidgetId();
		boolean isQuitWhenFinish = ctrlContextDto.isQuitWhenFinish();

		if (panelContext != null) {
			if (isQuitWhenFinish) {
				ToolUtilities.sleep(1500);
				QuitPopup.quit(panelContext);
			} else {
				SetChildVisible.set(panelContext, closeBtnWidgetId, true);
			}
		}
	}

	@Override
	public void onClose() {
		sendStopProcess();
	}

	@Override
	public void reset() {
		try {
			getProgService().sendProgress(getCtrlUuid(), 0, "");
			clearMessageEditor();
		} catch (Throwable err) {
			ToolUtilities.warnAndOutput(LOG, "Failed to send process : " + ToolUtilities.getExceptionStackMessage(err));
		}
	}

	
	@Override
	public void sendDataFrame(Object data) {
		if(data != null) {
			if(data != null) {
				if(data instanceof Exception) {
					FeProgressCtrlContextDto ctrlContextDto = getCtrlContextDto();
					PanelContext panelContext = ctrlContextDto.getPanelContext();
					try {
						handlerResult = ExceptionHandlerFactory.get().handle(getClass(),panelContext, (Exception)data);
						if(handlerResult.isHandled()) {
							setMessage(handlerResult.getErrorMsg(), true);
							finishError(handlerResult.getErrorDetail());
						}else {
							finishError(ToolUtilities.getFullExceptionStack((Exception)data));
						}
					} catch (Exception e) {
						Tracer tracer = TraceUtil.getCurrentTracer();
						tracer.error(ToolUtilities.getFullExceptionStack(e));
					}
				}else {
					setMessage(""+data, true);
				}
			}
		}
	}
	
}
