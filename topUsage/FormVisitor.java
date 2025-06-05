package gpf.dc.fe.component.adur.data.field.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cell.fe.IFileService;
import fe.cmn.data.BeFile;
import fe.cmn.data.ByteArrayDto;
import gpf.adur.data.AttachData;
import gpf.adur.data.Form;
import gpf.adur.data.TableData;
import gpf.dc.fe.component.adur.AttachDtoPanel;

public class FormVisitor implements Serializable {

	/**
	  * 附件属性缓存key
	  */
	 public final static String EXT_FIELD_KEY_BEFILE_MAP = "beFileMap";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8831255912229668546L;

	public void visit(Form cdo) throws Exception {
		Map<String, List<BeFile>> attachMap = (Map<String, List<BeFile>>) cdo
				.getExtField(EXT_FIELD_KEY_BEFILE_MAP);
		if (attachMap != null) {
			for (String key : attachMap.keySet()) {
				List<AttachData> lstAttach = convertBeFile2CDoAttach(attachMap.get(key));
				cdo.setAttrValueByCode(key, lstAttach);
			}
		}
		for (String key : cdo.getData().keySet()) {
			Object value = cdo.getAttrValueByCode(key);
			if (value instanceof TableData) {
				if (!((TableData) value).isEmtpy()) {
					for (Form slaveObj : ((TableData) value).getRows()) {
						visit(slaveObj);
					}
				}
			}
		}
		for(Form additionalForm : cdo.getAdditionalSaveForm().values()) {
			visit(additionalForm);
		}
	}

	public void convertBeFile2CDoAttach(Form cdo) throws Exception {
		Map<String, List<BeFile>> fileMap = (Map<String, List<BeFile>>) cdo
				.getExtField(EXT_FIELD_KEY_BEFILE_MAP);
		if (fileMap != null) {
			for (String key : fileMap.keySet()) {
				List<BeFile> files = fileMap.get(key);
				List<AttachData> lstAttach = convertBeFile2CDoAttach(files);
				cdo.setAttrValueByCode(key, lstAttach);
			}
		}
	}
	
	public static List<AttachData> convertBeFile2CDoAttach(List<BeFile> files) throws Exception {
		List<AttachData> lstAttach = new ArrayList<>();
		for (BeFile beFile : files) {
			AttachData attach = new AttachData();
			attach.setName(beFile.getName());
			if (beFile.getStorPath().startsWith(AttachDtoPanel.CUSTOM_FILE_RESOURCE)) {
				String path = beFile.getStorPath().replaceAll(AttachDtoPanel.CUSTOM_FILE_RESOURCE, "");
				String[] pathArr = path.split("/");
				if(pathArr.length == 3) {
					attach.setFormUuid(pathArr[0]);
					attach.setAttrName(pathArr[1]);
				}
//					ByteArrayDto byteArray = IGpfFeAttachFileService.get().getResource(beFile.getStorPath());
//					attach.setContentBin(byteArray.getData());
			} else {
				ByteArrayDto byteArray = IFileService.get().getResource(beFile.getStorPath());
				attach.setContent(byteArray.getData());
			}
			lstAttach.add(attach);
		}
		return lstAttach;
	}

}
