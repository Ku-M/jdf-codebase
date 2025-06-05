package gpf.dc.expimp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kwaidoo.ms.tool.CmnUtil;

public class ExportModelBriefInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5642377293118162366L;
	public final static String DATA_EXPORT_ALL = "ALL";
	public final static String DATA_EXPORT_NONE = "NONE";
	public final static String DATA_EXPORT_PART = "PART";
	String id;
	String label;
	String description;
	boolean exportModel = true;
	String dataExportMode = DATA_EXPORT_NONE;
	List<ExportDataBriefInfo> dataList = new ArrayList<>();
	public String getId() {
		return id;
	}
	public ExportModelBriefInfo setId(String id) {
		this.id = id;
		return this;
	}
	public String getLabel() {
		return label;
	}
	public ExportModelBriefInfo setLabel(String label) {
		this.label = label;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public ExportModelBriefInfo setDescription(String description) {
		this.description = description;
		return this;
	}
	public boolean isExportModel() {
		return exportModel;
	}
	public ExportModelBriefInfo setExportModel(boolean exportModel) {
		this.exportModel = exportModel;
		return this;
	}
	public String getDataExportMode() {
		return dataExportMode;
	}
	public boolean isExportAllData() {
		return CmnUtil.isStringEqual(DATA_EXPORT_ALL, dataExportMode);
	}
	public boolean isExportNoneData() {
		return CmnUtil.isStringEqual(DATA_EXPORT_NONE, dataExportMode);
	}
	public boolean isExportPartData() {
		return CmnUtil.isStringEqual(DATA_EXPORT_PART, dataExportMode);
	}
	public ExportModelBriefInfo setDataExportMode(String dataExportMode) {
		this.dataExportMode = dataExportMode;
		return this;
	}
	public List<ExportDataBriefInfo> getDataList() {
		return dataList;
	}
	public ExportModelBriefInfo setDataList(List<ExportDataBriefInfo> dataList) {
		this.dataList = dataList;
		return this;
	}
	
	public Map<String,ExportDataBriefInfo> getDataMap() {
		Map<String,ExportDataBriefInfo> map = new LinkedHashMap<>();
		for(ExportDataBriefInfo data : dataList) {
			map.put(data.getId(), data);
		}
		return map;
	}
	
	public void addData(ExportDataBriefInfo dataInfo) {
		for(ExportDataBriefInfo briefInfo : dataList) {
			if(CmnUtil.isStringEqual(briefInfo.getId(), dataInfo.getId()))
				return;
		}
		dataList.add(dataInfo);
	}
	
	public void mergeModelInfo(ExportModelBriefInfo modelInfo) {
		if(modelInfo.isExportModel()) {
			setExportModel(true);
		}
		if(modelInfo.isExportAllData()) {
			setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_ALL);
			setDataList(new ArrayList<>());
		}else if(modelInfo.isExportPartData()) {
			if(isExportAllData()) {
				
			}else if(isExportPartData()){
				Map<String,ExportDataBriefInfo> dataMap = getDataMap();
				Map<String,ExportDataBriefInfo> newDataMap = modelInfo.getDataMap();
				dataMap.putAll(newDataMap);
				setDataList(new ArrayList<>(dataMap.values()));
			}else if(isExportNoneData()) {
				setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
				setDataList(modelInfo.getDataList());
			}
		}
	}
	
}
