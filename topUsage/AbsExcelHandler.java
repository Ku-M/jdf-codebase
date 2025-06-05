package gpf.dc.basic.excel;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.dfc.gui.LvUtil;

import cmn.util.TraceUtil;
import cmn.util.Tracer;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.RowUtil;
import cn.hutool.poi.excel.cell.CellLocation;
import cn.hutool.poi.excel.cell.CellUtil;
import gpf.dc.basic.anotation.excel.ExcelCell;
import gpf.dc.basic.anotation.excel.ExcelColumn;
import gpf.dc.basic.anotation.excel.ExcelRange;
import gpf.dc.basic.anotation.excel.ExcelRow;
import gpf.dc.basic.anotation.excel.ExcelRowMap;
import gpf.dc.basic.expression.excel.ExcelCellLocation;
import gpf.dc.basic.expression.excel.ExcelRuleIntf;
import gpf.dc.basic.expression.excel.ExcelRule_Location;
import gpf.dc.basic.expression.excel.ExcelRule_LocationMatchColValue;
import gpf.dc.basic.expression.excel.ExcelRule_LocationMatchEmptyCol;
import gpf.dc.basic.expression.excel.ExcelRule_LocationMatchEmptyRow;
import gpf.dc.basic.expression.excel.ExcelRule_LocationNotNull;
import gpf.dc.basic.expression.excel.ExcelRule_LocationRange;
import gpf.dc.basic.expression.excel.ExcelRule_MaxLocation;
import gpf.dc.basic.expression.excel.ExcelRule_RelativeLocation;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.util.GpfDCBasicUtil;
import gpf.exception.VerifyException;
/**
 * 通过继承AbsExcelHandler可实现基础Excel的导入导出。
使用说明：
	1.定义导入导出的Excel模板
	2.定义Excel模板每个sheet对应的DTO类，并通过注解为DTO类的每个属性声明在Excel模板中的类型，类型声明如下：
		● ExcelCell 属性为Sheet页中的单元格, 类型为String,Double,Long
		● ExcelRow	属性为Sheet页中的行记录，类型为List<xxxRowDto>
		● ExcelColumn	属性为行记录的列, 类型为String,Double,Long
		● ExcelMap	属性为为行记录中不确定数量的列,类型为Map<String,String> ,其中key对应列的抬头，value为列的值
		● ExcelRange	属性为Sheet页中的某个区域，类型为List<xxxRangeDto>,需要单独为此类数据声明ExcelHandler用于处理区间内的值映射
	3.定义SheetDto、RangeDto的ExcelHandler，通过坐标表达式初始化属性读取或写入Sheet页的坐标位置，如：
		● ExcelCell 取具体坐标位置，如 表达式 坐标('A1')，表示取单元格 A1的数据
		● ExcelRow 取具体坐标区间，如表达式 区间('A1','B3'),表示取[A1,B3]之间的数据作为行记录
		● ExcelRange 取具体坐标区间，如表达式 区间('A1','B3'),表示取[A1,B3]之间的数据作为行记录
		       ExcelRange如果存在多个区间的值记录时，通过设置表达式 属性Start，属性End，属性NextStart的坐标表达式，用于下一个区间值的计算，直到属性NextStart的坐标为空值。如：
		       solutionsStart : 匹配列值($privilegeSolutionStart,'数据方案','动作方案')
		       solutionsNextStart, 匹配列值($solutionsStart,'数据方案','动作方案')
		       solutionsEnd, 匹非空值(匹配列值($solutionsStart,'数据方案','动作方案'),匹配空行($solutionsStart))
	4.可用的坐标表达式规则如下：
		● 坐标('A1') 坐标(0,0)
		● 相对坐标($坐标变量,1,1) 表示相对指定坐标, x+1,y+1
		● 匹配列值($坐标变量,'匹配值1','匹配值2') 表示在指定坐标下，找到该列往下匹配指定值的坐标
		● 匹配空列($坐标变量) 表示在指定坐标下的指定列的空值坐标
		● 匹配空行($坐标变量) 表示在指定坐标下的指定行的空值坐标
		● 非空值($坐标变量1,$坐标变量2) 
		● 区间($坐标变量1,$坐标变量2) 表示取坐标区间
		● 最小坐标($坐标变量1,$坐标变量2) 取两个坐标中x,y的最小值
		● 最大坐标($坐标变量1,$坐标变量2) 取两个坐标中x,y的最大值
		● $maxRow	当前sheet页的最大行数
		● $maxCol	当前sheet页的最大列数
		● $startLoc	在读取ExcelRange时生效，用于标识读取ExcelRange的开始坐标
		● $endLoc	在读取ExcelRange时生效，用于标识读取ExcelRange的结束坐标
 * @author chenxb
 *
 * @param <T>
 */
public abstract class AbsExcelHandler<T>{
//	public final static String MODE_WRITE = "WRITE";
//	public final static String MODE_READ = "READ";
	Workbook wb;
	ExcelWriter writer;
	ExcelReaderExt reader;
	String defaultSheetName;
	Map<String,String> readFieldValueLocExprs = new LinkedHashMap<>();
	Map<String,String> writeFieldValueLocExprs = new LinkedHashMap<>();
	/**
	 * 需要合并单元格的属性
	 */
	Map<String,List<String>> writeMergeFields = new LinkedHashMap<>();
//	String mode;
	public AbsExcelHandler(Workbook wb,String sheetName) {
//		this.mode = mode;
		this.wb = wb;
//		if(CmnUtil.isStringEqual(MODE_WRITE, mode))
			this.writer = new ExcelWriter(wb, sheetName);
//		if(CmnUtil.isStringEqual(MODE_READ, mode))
			this.reader = new ExcelReaderExt(wb, sheetName);
		this.defaultSheetName = sheetName;
		this.readFieldValueLocExprs = initReadFieldValueLocExprs();
		this.writeFieldValueLocExprs = initWriteFieldValueLocExprs();
		this.writeMergeFields = initWriteMergeFields();
	}
	
	public void verifyTemplateVersion() {
		Object versionValue =reader.readCellValue(1, 0);
		String version = versionValue == null ? "" : versionValue.toString();
		if(!CmnUtil.isStringEqual(version, getTemplateVersion())) {
			throw new VerifyException("模板版本不正确，当前版本：" + getTemplateVersion());
		}
	}
	
	public String getDefaultSheetName() {
		return defaultSheetName;
	}
	
	public abstract String getTemplateVersion();
	
	public abstract Map<String,String> initReadFieldValueLocExprs();
	
	public abstract Map<String,String> initWriteFieldValueLocExprs();
	
	public Map<String,List<String>> initWriteMergeFields(){
		return new LinkedHashMap<>();
	}
	
	public abstract Class<T> getBeanType();
	
	public Map<String, String> getReadFieldValueLocExprs() {
		return readFieldValueLocExprs;
	}
	
	public Map<String, String> getWriteFieldValueLocExprs() {
		return writeFieldValueLocExprs;
	}
	
	public Map<String, List<String>> getWriteMergeFields() {
		return writeMergeFields;
	}
	
	public ExcelWriter getWriter() {
		return writer;
	}
	public ExcelReader getReader() {
		return reader;
	}
	
	public void copyCellWithStyle(ExcelReader reader,String location,Row targetRow) {
		CellLocation startCell = ExcelUtil.toLocation(location);
		Cell sourceCell = reader.getCell(startCell.getX(), startCell.getY());
		Cell targetCell = targetRow.createCell(startCell.getX());
		copyCellWithStyle(sourceCell, targetCell);
	}
	
	public void copyCellWithStyle(Cell sourceCell,Cell targetCell) {
		if(sourceCell == null)
			return;
		Object cellValue = CellUtil.getCellValue(sourceCell);
		CellUtil.setCellValue(targetCell, cellValue);
		// 复制单元格样式
		CellStyle cellStyle = writer.getWorkbook().createCellStyle();
		cellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		targetCell.setCellStyle(cellStyle);
	}
	
	public void copyRowWithStyle(ExcelReader reader,String locationRange) throws Exception {
		Tracer tracer = TraceUtil.getCurrentTracer();
		tracer.info("copyRowWithStyle = " + locationRange);
		List<CellLocation> ranges = convertRange(locationRange);
		CellLocation startCell = ranges.get(0);
		CellLocation endCell = ranges.get(1);
		Sheet sourceSheet = reader.getSheet();
		Sheet targetSheet = writer.getSheet();
		int targetRowStartIndex = writer.getCurrentRow() ;
//		if(IGpfDebug.get().isDebug()) {
//			LvUtil.trace("targetSheet = " +targetSheet.getSheetName());
//			LvUtil.trace("targetRowStartIndex = " +targetRowStartIndex);
//			LvUtil.trace("endCell Y = " +endCell.getY());
//			LvUtil.trace("startCell Y = " +startCell.getY());
//		}
		tracer.info("目标位置 = " + "A"+(targetRowStartIndex+1));
		if(targetRowStartIndex < writer.getRowCount()) {
			int insertRows = endCell.getY() - startCell.getY() + 1;
			RowUtil.insertRow(targetSheet, targetRowStartIndex, insertRows);
			tracer.info("插入行数  = " + insertRows);
		}
//		int sourceRowStartIndex = startCell.getY();
//		int sourceRowEndIndex = endCell.getY();
		int rowCnt = targetRowStartIndex;
		for(int y=startCell.getY();y<=endCell.getY();y++) {
			Row row = writer.getOrCreateRow(rowCnt++);
			for(int x = startCell.getX();x<=endCell.getX();x++) {
				Cell sourceCell = reader.getCell(x, y);
				Cell targetCell = row.createCell(x);
				copyCellWithStyle(sourceCell, targetCell);
			}
			writer.passCurrentRow();
		}
		CellRangeAddress rangeAddress = new CellRangeAddress(startCell.getY(), endCell.getY(), startCell.getX(), endCell.getX());
		// 复制合并单元格
		int rowSep = -1;
		for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			CellRangeAddress mergedRegion = sourceSheet.getMergedRegion(i);
			if(rangeAddress.isInRange(mergedRegion.getFirstRow(),mergedRegion.getFirstColumn())
					&& mergedRegion.isInRange(mergedRegion.getFirstRow(), mergedRegion.getLastColumn())
					&& mergedRegion.isInRange(mergedRegion.getLastRow(), mergedRegion.getFirstColumn())
					&& mergedRegion.isInRange(mergedRegion.getLastRow(), mergedRegion.getLastColumn())
					) {
				if(rowSep == -1) {
					rowSep = mergedRegion.getFirstRow();
				}
				int targetRowIndex = mergedRegion.getFirstRow() + targetRowStartIndex;
				int targetRowEndIndex = mergedRegion.getLastRow() +targetRowStartIndex;
				tracer.debug("targetRowIndex="+targetRowIndex);
				tracer.debug("targetRowEndIndex="+targetRowEndIndex);
				CellRangeAddress newMergedRegion = new CellRangeAddress(targetRowIndex, targetRowEndIndex, 
						mergedRegion.getFirstColumn(), mergedRegion.getLastColumn());
				try {
					tracer.info("来源sheet合并单元格：" + mergedRegion+",目标sheet合并单元格:" + newMergedRegion);
					targetSheet.addMergedRegion(newMergedRegion);
				}catch (Exception e) {
					tracer.warning("合并单元格失败：" + newMergedRegion);
				}
			}
		}
		//设置行宽
//		for(int i =startCell.getX();i<=endCell.getX();i++) {
//			int columnWidth = sourceSheet.getColumnWidth(i);
//			targetSheet.setColumnWidth(i,columnWidth);
//		}
	}
	
	public void addMegerRegion(String locationRange) throws Exception {
		Sheet sheet = writer.getSheet();
		List<CellLocation> ranges = convertRange(locationRange);
		CellLocation startCell = ranges.get(0);
		CellLocation endCell = ranges.get(1);
		CellRangeAddress newMergedRegion = new CellRangeAddress(startCell.getY(), endCell.getY(), 
				startCell.getX(), endCell.getX());
		try {
		sheet.addMergedRegion(newMergedRegion);
		}catch (Exception e) {
			LvUtil.trace("合并单元格[" + locationRange+"]失败！");
		}
	}
	
	public int getCurrentRow() {
		return writer.getCurrentRow();
	}
	
	public int getNextRow() {
		writer.passCurrentRow();
		return writer.getCurrentRow();
	}
	
	public void flush() {
		writer.flush();
	}
	
	public void flush(File destFile) {
		writer.flush(destFile);
	}
	
	public void flush(OutputStream out) {
		writer.flush(out);
	}
	
	public void flush(OutputStream out,boolean isCloseOut) {
		writer.flush(out, isCloseOut);
	}
	
	public void setDestFile(File destFile) {
		writer.setDestFile(destFile);
	}
	
	public void close() {
		writer.close();
	}
	
	public Map<String,String> getFieldHeaderAliasMap(Class excelRowClass){
		Map<String,Field> fieldMap = ToolUtilities.getAllFieldMap(excelRowClass, false);
		Map<String,String> headerAliasMap = new LinkedHashMap<>();
		for(String fieldName : fieldMap.keySet()) {
			Field field = fieldMap.get(fieldName);
			ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
			if(excelColumn == null)
				continue;
			headerAliasMap.put(excelColumn.value(), fieldName);
		}
		return headerAliasMap;
	}
	
	public Map<String,Field> getExcelRowMapFields(Class excelRowClass){
		Map<String,Field> fieldMap = ToolUtilities.getAllFieldMap(excelRowClass, false);
		Map<String,Field> excelRowMapFields = new LinkedHashMap<>();
		for(String fieldName : fieldMap.keySet()) {
			Field field = fieldMap.get(fieldName);
			ExcelRowMap excelRowMap = field.getAnnotation(ExcelRowMap.class);
			if(excelRowMap == null)
				continue;
			excelRowMapFields.put(fieldName,field);
		}
		return excelRowMapFields;
	}
	
	public void writeRows(List<T> rows)throws Exception{
		Map<String,Object> env = new LinkedHashMap<>();
		ExcelRuleIntf.prepareEnv(env, reader);
		String locationExpr = writeFieldValueLocExprs.get("this");
		ExcelCellLocation location = (ExcelCellLocation) caculateFieldValueLocation(env, "$this",locationExpr);
		writeRows(location, getBeanType(), rows);
	}
	
	public void writeRows(String locationExpr,Class<?> excelRowClass,List<T> rows)throws Exception{
		Map<String,Object> env = new LinkedHashMap<>();
		ExcelRuleIntf.prepareEnv(env, reader);
		ExcelCellLocation location = (ExcelCellLocation) caculateFieldValueLocation(env, "$this",locationExpr);
		writeRows(location, getBeanType(), rows);
	}
	
	public void writeRows(ExcelCellLocation location,Class<?> excelRowClass,List<T> rows)throws Exception{
		Tracer tracer = TraceUtil.getCurrentTracer();
		Sheet sheet = writer.getSheet();
		if(CmnUtil.isCollectionEmpty(rows)) {
			writer.writeCellValue(location.getX(), location.getY(), "");
		}else {
			int insertNumber = rows.size()-1;
			writer.resetRow();
			int lastRowNum = sheet.getLastRowNum();
			if(location.getY() <= lastRowNum) {
				writer.passRows(location.getY());
				tracer.info("当前sheet:"+sheet.getSheetName()+"，最后一行："+lastRowNum+",当前行："+location.getY()+",新增行数：" + insertNumber);
				try {
					insertRow(sheet, location.getY(), insertNumber);
				}catch (Exception e) {
					tracer.warning("插入新行出错：" + ToolUtilities.getFullExceptionStack(e));
				}
			}else {
				tracer.info("当前sheet:"+sheet.getSheetName()+"，跳到最后一行："+lastRowNum+"");
				writer.passRows(sheet.getLastRowNum());
				while(lastRowNum<=location.getY()) {
					lastRowNum++;
					RowUtil.getOrCreateRow(sheet, lastRowNum);
				}
				writer.setCurrentRow(location.getY());
				insertRow(sheet, location.getY(), insertNumber);
			}
			Map<String,String> fieldHeaderAlias = getFieldHeaderAliasMap(excelRowClass);
			Map<String,Field> rowFieldMap = getExcelRowMapFields(excelRowClass);
			if(rowFieldMap.isEmpty()) {
				writer.setHeaderAlias(fieldHeaderAlias);
				for(Object rowBean : rows) {
					writer.writeRow(rowBean, false);
				}
			}else {
				boolean writeHeader = false;
				Map<String,Object> rowTmplt = new LinkedHashMap<>();
				for(String fieldHeader : fieldHeaderAlias.keySet()) {
					rowTmplt.put(fieldHeader,"");
				}
				for(Object rowBean : rows) {
					for(String rowMapField : rowFieldMap.keySet()) {
						Map<String,String> rowMapValue = (Map<String, String>) ToolUtilities.getFieldValue(rowBean, rowMapField);
						if(!CmnUtil.isMapEmpty(rowMapValue)) {
							for(String dynamicField : rowMapValue.keySet()) {
								rowTmplt.put(dynamicField, "");
							}
						}
					}
				}
				
				for(Object rowBean : rows) {
					Map<String,Object> rowMap = ToolUtilities.clone(rowTmplt);
					for(String fieldHeader : fieldHeaderAlias.keySet()) {
						Object fieldValue = ToolUtilities.getFieldValue(rowBean, fieldHeaderAlias.get(fieldHeader));
						rowMap.put(fieldHeader, fieldValue);
					}
					for(String rowMapField : rowFieldMap.keySet()) {
						Map<String,String> rowMapValue = (Map<String, String>) ToolUtilities.getFieldValue(rowBean, rowMapField);
						if(rowMapValue != null)
							rowMap.putAll(rowMapValue);
					}
					if(!writeHeader) {
						writer.writeHeadRow(rowMap.keySet());
						writeHeader = true;
					}
					writer.writeRow(rowMap, false);
				}
			}
			location.setX2(location.getX2());
			location.setY2(location.getY()+insertNumber);
		}
	}
	
	/**
	 * 替换修改hutool工具包的RowUtil.insertRow
	 * 插入行
	 *
	 * @param sheet        工作表
	 * @param startRow     插入的起始行
	 * @param insertNumber 插入的行数
	 * @since 5.4.2
	 */
	public static void insertRow(Sheet sheet, int startRow, int insertNumber) {
		if (insertNumber <= 0) {
			return;
		}
		// 插入位置的行，如果插入的行不存在则创建新行
		Row sourceRow = RowUtil.getOrCreateRow(sheet, startRow);
		// 从插入行开始到最后一行向下移动
		sheet.shiftRows(startRow, sheet.getLastRowNum(), insertNumber, true, false);

//		// 填充移动后留下的空行
//		IntStream.range(startRow, startRow + insertNumber).forEachOrdered(i -> {
//			Row row = sheet.createRow(i);
//			row.setHeightInPoints(sourceRow.getHeightInPoints());
//			short lastCellNum = sourceRow.getLastCellNum();
//			IntStream.range(0, lastCellNum).forEachOrdered(j -> {
//				Cell cell = row.createCell(j);
//				cell.setCellStyle(sourceRow.getCell(j).getCellStyle());
//			});
//		});
	}
	
	public void writeSheet(String sheetName,T data)throws Exception{
		Map<String,Object> env = new LinkedHashMap<>();
		writeSheet(sheetName, data, env);
	}
	public void writeSheet(String sheetName,T data,Map<String,Object> env)throws Exception{
		Tracer tracer = TraceUtil.getCurrentTracer();
		if(!CmnUtil.isStringEmpty(sheetName))
			writer.setSheet(sheetName);
		//插入行
		ExcelRuleIntf.prepareEnv(env, reader);
		Map<String,Field> fieldMap = ToolUtilities.getAllFieldMap(data.getClass(), false);
		for(String fieldName : writeFieldValueLocExprs.keySet()) {
			String locationExpression = writeFieldValueLocExprs.get(fieldName);
			ExcelCellLocation location = caculateFieldValueLocation(env, fieldName,locationExpression);
			Field field = fieldMap.get(fieldName);
			if(field == null)
				continue;
			if(location == null)
				continue;
			Object value = ToolUtilities.getFieldValue(data, field);
			ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
			ExcelRow excelRow = field.getAnnotation(ExcelRow.class);
			ExcelRange excelTable = field.getAnnotation(ExcelRange.class);
			if(excelCell != null) {
				if(!CmnUtil.isStringEmpty(location.getSheetName()))
					writer.setSheet(location.getSheetName());
				int x = location.getX();
				int y = location.getY();
				String realValue = value == null ? "" : value.toString();
				writer.writeCellValue(x, y, realValue);
				if(writeMergeFields.containsKey(fieldName)) {
					List<String> mergeRegions = writeMergeFields.get(fieldName);
					String mergeRegion = mergeRegions.get(0);
					String[] regions = mergeRegion.split(":");
					addMegerRegion(regions[0]+(y+1)+":"+regions[1]+(y+1));
				}
			}else if(excelRow != null){
				List list = (List) value;
				writeRows(location, excelRow.value(), list);
				tracer.info(location);
				if(writeMergeFields.containsKey(fieldName)) {
					List<String> mergeRegions = writeMergeFields.get(fieldName);
					for(int i =0;i<mergeRegions.size();i++) {
						String mergeRegion = mergeRegions.get(0);
						String[] regions = mergeRegion.split(":");
						for(int megerRow=location.getY();megerRow<location.getY2();megerRow++) {
							addMegerRegion(regions[0]+(megerRow+1)+":"+regions[1]+(megerRow+1));
						}
					}
				}
				env.put("$"+fieldName, location);
			}else if(excelTable != null) {
				Class<? extends AbsExcelHandler> handlerClass = excelTable.handler();
				AbsExcelHandler excelTableHandler = (AbsExcelHandler) ToolUtilities.getBestConstructor(handlerClass, wb,excelTable.tmpltSheet()).newInstance(wb,excelTable.tmpltSheet());
				List list = (List) value;
				writer.setCurrentRow(location.getY());
				for(int i =0;i<list.size();i++) {
					Map<String,Object> rangeEnv = new LinkedHashMap<>();
					rangeEnv.putAll(env);
					Object obj = list.get(i);
					// 复制一个新的sheet页做excel范围内容的更新
					Sheet newSheet = wb.cloneSheet(wb.getSheetIndex(excelTableHandler.getDefaultSheetName()));
					excelTableHandler.writeRange(newSheet.getSheetName(), obj, rangeEnv);
					//将复制的sheet更新到当前sheet中
					ExcelReader rangeSheetReader = new ExcelReader(newSheet);
					int maxRow = rangeSheetReader.getRowCount();
					int maxCol = rangeSheetReader.getColumnCount();
					for(int y = 0;y<maxRow;y++) {
						int col = rangeSheetReader.getColumnCount(y);
						if(col> maxCol) {
							maxCol = col;
						}
					}
					if(maxCol > 0 && maxRow > 0) {
						String endLoc = convert2Location(maxCol-1, maxRow-1);
						copyRowWithStyle(rangeSheetReader, "A1:"+endLoc);
//						if(!IGpfDebug.get().isDebug())
							wb.removeSheetAt(wb.getSheetIndex(newSheet));
					}
				}
				location.setY2(writer.getCurrentRow());
				wb.removeSheetAt(wb.getSheetIndex(excelTable.tmpltSheet()));
			}
		}
	}
	
	public void writeRange(String sheetName,T data,Map<String,Object> env) throws Exception {
		writeSheet(sheetName, data, env);
	}
	
	public <E> E trimObject(E e) throws Exception {
		return GpfDCBasicUtil.trimObject(e);
	}
	public T readSheet() throws Exception{
		Map<String,Object> env = new LinkedHashMap<>();
		return readSheet(env,null);
	}
	public T readSheet(T data) throws Exception{
		Map<String,Object> env = new LinkedHashMap<>();
		return readSheet(env,data);
	}

	public T readSheet(Map<String,Object> env,T data) throws Exception{
		Tracer tracer = TraceUtil.getCurrentTracer();
		if(data == null)
			data = getBeanType().newInstance();
		int maxRow = reader.getRowCount();
		int maxCol = reader.getColumnCount();
		for(int y = 0;y<maxRow;y++) {
			int col = reader.getColumnCount(y);
			if(col> maxCol) {
				maxCol = col;
			}
		}
		env.put("$maxRow", maxRow);
		env.put("$maxCol", maxCol);
		Map<String,Field> fieldMap = ToolUtilities.getAllFieldMap(getBeanType(), false);
		ExcelRuleIntf.prepareEnv(env, reader);
		for(String fieldName : readFieldValueLocExprs.keySet()) {
			String locationExpression = readFieldValueLocExprs.get(fieldName);
			ExcelCellLocation location = caculateFieldValueLocation(env, fieldName,locationExpression);
			if(location == null) {
				continue;
			}
			if(!fieldMap.containsKey(fieldName))
				continue;
			Field field = fieldMap.get(fieldName);
			ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
			ExcelRow excelRow = field.getAnnotation(ExcelRow.class);
			ExcelRange excelTable = field.getAnnotation(ExcelRange.class);
			if(excelCell != null) {
				String cellSheetName = location.getSheetName();
				int x = location.getX();
				int y = location.getY();
				if(!CmnUtil.isStringEmpty(cellSheetName))
					reader.setSheet(cellSheetName);
				Object value = reader.readCellValue(x, y);
				String sValue = value == null ? null : value.toString().trim();
				if(excelCell.require()) {
					if(CmnUtil.isStringEmpty(sValue)) {
						throw new Exception(GpfDCBasicI18n.getString("单元格[{}]不能为空！",excelCell.value())); 
					}
				}
				ToolUtilities.setFieldValue(data, field, sValue);
			}else if(excelRow != null) {
				Class rowBeanClass = excelRow.value();
				Map<String,String> headerAliasMap = getFieldHeaderAliasMap(rowBeanClass);
				Map<String, Field> rowMapFields = getExcelRowMapFields(rowBeanClass);
				if(rowMapFields.isEmpty()) {
					List<Object> rowBeans = readRows(location, rowBeanClass,headerAliasMap);
					ToolUtilities.setFieldValue(data, field, rowBeans);
				}else {
					List<Object> rowBeans = readRowMap(location, rowBeanClass, headerAliasMap, rowMapFields);
					ToolUtilities.setFieldValue(data, field, rowBeans);
				}
			}else if(excelTable != null) {
				Class<? extends AbsExcelHandler> handlerClass = excelTable.handler();
				List<Object> list = new ArrayList<>();
				AbsExcelHandler excelTableHandler = (AbsExcelHandler) ToolUtilities.getBestConstructor(handlerClass, wb,defaultSheetName).newInstance(wb,defaultSheetName);
				while(true) {
					Map<String,Object> rangeEnv = new LinkedHashMap<>();
					tracer.info("$startLoc = " + "$"+fieldName+"Start");
					tracer.info("$endLoc = " + "$"+fieldName+"End");
					rangeEnv.put("$startLoc", env.get("$"+fieldName+"Start"));
					rangeEnv.put("$endLoc", env.get("$"+fieldName+"End"));
					tracer.info("rangeEnv = " + rangeEnv);
					Object rangeData = excelTableHandler.readRange(rangeEnv);
					list.add(rangeData);
//					System.out.println(env);
					String nextRange = fieldName+"NextStart";
					if(!readFieldValueLocExprs.containsKey(nextRange)) {
						break;
					}
					ExcelCellLocation nextStart = (ExcelCellLocation) caculateFieldValueLocation(env, fieldName+"Start", "$"+fieldName+"NextStart");
					if(nextStart == null) {
						break;
					}
					if(readFieldValueLocExprs.containsKey(fieldName+"End")) {
						ExcelCellLocation nextEnd = (ExcelCellLocation) caculateFieldValueLocation(env, fieldName+"End", readFieldValueLocExprs.get(fieldName+"End"));
						if(nextEnd == null) {
							break;
						}
					}else {
						break;
					}
					if(readFieldValueLocExprs.containsKey(nextRange)) {
						ExcelCellLocation nextLoc = (ExcelCellLocation) caculateFieldValueLocation(env, nextRange, readFieldValueLocExprs.get(nextRange));
//						if(nextLoc == null)
//							break;
					}else {
						break;
					}
//					System.out.println(env);
				}
				ToolUtilities.setFieldValue(data, field, list);
			}
		}
		data = trimObject(data);
		return data;
	}
	
	public List<T> readRows() throws Exception{
		String locationExpr = readFieldValueLocExprs.get("this");
		Class beanType = getBeanType();
		Map<String,Object> env = new LinkedHashMap<>();
		ExcelRuleIntf.prepareEnv(env, reader);
		ExcelCellLocation location = caculateFieldValueLocation(env, "$this",locationExpr);
		Map<String,String> headerAliasMap = getFieldHeaderAliasMap(beanType);
		return readRows(location, beanType, headerAliasMap);
	}
	
	public List<T> readRows(String locationExpr) throws Exception{
		Class beanType = getBeanType();
		Map<String,Object> env = new LinkedHashMap<>();
		ExcelRuleIntf.prepareEnv(env, reader);
		ExcelCellLocation location = (ExcelCellLocation) caculateFieldValueLocation(env, "$this",locationExpr);
		Map<String,String> headerAliasMap = getFieldHeaderAliasMap(beanType);
		return readRows(location, beanType, headerAliasMap);
	}
	
	public <E> List<E> readRows(ExcelCellLocation rangeCell,Class<E> clazz,Map<String,String> headerAliasMap)throws Exception{
		String sheetName = rangeCell.getSheetName();
		if(!CmnUtil.isStringEmpty(sheetName)) {
			reader.setSheet(sheetName);
		}
		int headerRowIndex = rangeCell.getY()-1;
		int startRowIndex = rangeCell.getY();
		int endRowIndex = rangeCell.getY2();
		reader.setHeaderAlias(headerAliasMap);
		List<E> list = reader.read(headerRowIndex, startRowIndex, endRowIndex, clazz);
		//
		Map<String,Field> fieldMap = ToolUtilities.getAllFieldMap(clazz, false);
		List<ExcelColumn> requireExcelColumns = new ArrayList<>();
		for(String fieldName : fieldMap.keySet()) {
			Field field = fieldMap.get(fieldName);
			ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
			if(excelColumn == null)
				continue;
			if(excelColumn.require())
				requireExcelColumns.add(excelColumn);
		}
		for(E element : list) {
			for(ExcelColumn excelColumn : requireExcelColumns) {
				String cellName = excelColumn.value();
				String fieldName = headerAliasMap.get(cellName);
				Object fieldValue = ToolUtilities.getFieldValue(element, fieldName);
				if(CmnUtil.isObjectEmpty(fieldValue)) {
					throw new VerifyException(GpfDCBasicI18n.getString("列[{1}]值不能为空！", cellName)) ;
				}
			}
		}
		return list;
		
	}
	
	public <E> List<E> readRowMap(ExcelCellLocation rangeCell,Class<E> clazz,Map<String,String> headerAliasMap,Map<String,Field> rowMapFields)throws Exception{
		String sheetName = rangeCell.getSheetName();
		if(!CmnUtil.isStringEmpty(sheetName)) {
			reader.setSheet(sheetName);
		}
		int headerRowIndex = rangeCell.getY()-1;
		int startRowIndex = rangeCell.getY();
		int endRowIndex = rangeCell.getY2();
		reader.setHeaderAlias(null);
		List<Map<String,Object>> rows = reader.read(headerRowIndex, startRowIndex, endRowIndex);
		List<E> list = new ArrayList<>();
		Map<String,Field> javaFieldMap = ToolUtilities.getAllFieldMap(clazz, false);
		for(Map<String,Object> row : rows) {
			E bean = clazz.newInstance();
			for(String header : headerAliasMap.keySet()) {
				String fieldName = headerAliasMap.get(header);
				Object value = row.remove(header);
				setFieldValue(bean, javaFieldMap.get(fieldName), value);
//				ToolUtilities.setFieldValue(bean, fieldName, value);
			}
			for(String rowMapField : rowMapFields.keySet()) {
				ToolUtilities.setFieldValue(bean, rowMapField, row);
			}
			list.add(bean);
		}
		return list;
	}
	
	public void setFieldValue(Object bean,Field f,Object value) throws Exception {
		if(f.getType() == Integer.class || f.getType() == int.class) {
			ToolUtilities.setFieldValue(bean, f.getName(), CmnUtil.getInteger(value));
		}else if(f.getType() == Long.class || f.getType() == long.class) {
			ToolUtilities.setFieldValue(bean, f.getName(), CmnUtil.getLong(value));
		}else if(f.getType() == Double.class || f.getType() == double.class) {
			ToolUtilities.setFieldValue(bean, f.getName(), CmnUtil.getDouble(value));
		}else if(f.getType() == Float.class || f.getType() == float.class) {
			ToolUtilities.setFieldValue(bean, f.getName(), CmnUtil.getFloat(value));
		}else if(f.getType() == Boolean.class || f.getType() == boolean.class) {
			ToolUtilities.setFieldValue(bean, f.getName(), CmnUtil.getBoolean(value));
		}else if(f.getType() == String.class) {
			ToolUtilities.setFieldValue(bean, f.getName(), CmnUtil.getString(value,""));
		}else {
			ToolUtilities.setFieldValue(bean, f.getName(), value);
		}
	}
	
	public T readRange(Map<String,Object> env) throws Exception{
//		List<T> list = new ArrayList<>();
//		while(location != null) {
//			env.put("$"+tableVar+"Start", new ExcelCellLocation(defaultSheetName, location.getX(),location.getY()));
			T data = readSheet(env,null);
//			list.add(data);
//			String nextTable = tableVar+"Next";
//			String nextTableVar = "$"+tableVar+"Next";
//			if(readFieldValueLocExprs.containsKey(nextTableVar)) {
//				location = (ExcelCellLocation) caculateFieldValueLocation(env, nextTable, readFieldValueLocExprs.get(nextTableVar));
//			}else {
//				location = null;
//			}
//		}
//		return list;
			return data;
	}
	
	/**
	 * 转换位置信息，格式：A10
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public CellLocation convertPosition(String position) throws Exception{
		return ExcelUtil.toLocation(position);
	}
	
	public String convert2Location(int x,int y) {
		String colName = ExcelUtil.indexToColName(x);
		return colName + (y+1);
	}
	
	/**
	 * 转换范围
	 * @param range
	 * @return
	 * @throws Exception
	 */
	public List<CellLocation> convertRange(String range) throws Exception{
		String[] rangeArr = range.split(":"); 
		if(rangeArr.length != 2) {
			throw new Exception("范围格式不正确，格式：A1:B2");
		}
		List<CellLocation> rangeList = new ArrayList<>();
		rangeList.add(convertPosition(rangeArr[0]));
		rangeList.add(convertPosition(rangeArr[1]));
		return rangeList;
	}
	
	public boolean registed = false;
	/**
	 * 注册excel规则函数
	 * @throws Exception
	 */
	public void registExcelRuleFunction() throws Exception {
		if(registed)
			return;
		List<Class> ruleClasses = new ArrayList<>();
		ruleClasses.add(ExcelRule_Location.class);
		ruleClasses.add(ExcelRule_RelativeLocation.class);
		ruleClasses.add(ExcelRule_LocationMatchColValue.class);
		ruleClasses.add(ExcelRule_LocationMatchEmptyRow.class);
		ruleClasses.add(ExcelRule_LocationMatchEmptyCol.class);
		ruleClasses.add(ExcelRule_MaxLocation.class);
		ruleClasses.add(ExcelRule_LocationRange.class);
		ruleClasses.add(ExcelRule_LocationNotNull.class);
		
		for(Class ruleClass : ruleClasses) {
			Object object = ruleClass.newInstance();
			AviatorEvaluator.addFunction((AviatorFunction) object);
		}
		registed = true;
	}
	/**
	 * 计算属性取值坐标
	 * @param env
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public ExcelCellLocation caculateFieldValueLocation(Map<String,Object> env,String fieldName,String expression) throws Exception{
		registExcelRuleFunction();
		Tracer tracer = TraceUtil.getCurrentTracer();
		tracer.info("属性："+ fieldName + ",坐标表达式："+expression);
		ExcelCellLocation result = null;
		try{
			result = (ExcelCellLocation) AviatorEvaluator.execute(expression, env);
		}catch (Exception e) {
			throw new RuntimeException("坐标规则计算出错："
					+"fieldName = " + fieldName 
					+ ",expression = " + expression
					+ ",env = "+env,e);
		}
		if(result == null) {
			updateFieldValueLocation(env, fieldName, result);
			return null;
		}
		if(result.isCell()) {
			tracer.info("计算结果：" + result+",坐标:"+convert2Location(result.getX(), result.getY()));
			if(result.getX() < 0 || result.getY() < 0) {
				throw new VerifyException("坐标值非法：" + result);
			}
		}else {
			tracer.info("计算结果：" + result+",区间:["+convert2Location(result.getX(), result.getY())+","+convert2Location(result.getX2(), result.getY2())+"]");
			if(result.getX() < 0 || result.getY() < 0 || result.getX2() < 0 || result.getY2() < 0 
					||result.getX2() < result.getX() || result.getY2() < result.getY()) {
				throw new VerifyException("坐标值非法：" + result);
			}
		}
		updateFieldValueLocation(env, fieldName, result);
		return result;
	}
	
	public Map<String,ExcelCellLocation> caculateCellLocationMap(Map<String,String> expressionMap) throws Exception{
		//插入行
		Map<String,Object> env = new LinkedHashMap<>();
		ExcelRuleIntf.prepareEnv(env, reader);
		Map<String,ExcelCellLocation> varMap = new LinkedHashMap<>();
		for(String fieldName : expressionMap.keySet()) {
			String locationExpression = expressionMap.get(fieldName);
			ExcelCellLocation location = caculateFieldValueLocation(env, fieldName,locationExpression);
			varMap.put(fieldName, location);
		}
		return varMap;
	}
	/**
	 * 更新属性值坐标
	 * @param env
	 * @param fieldName
	 * @param locations
	 */
	public void updateFieldValueLocation(Map<String,Object> env,String fieldName,ExcelCellLocation location) {
		env.put("$"+fieldName, location);
	}
}
