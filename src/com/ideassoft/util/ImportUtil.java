package com.ideassoft.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.ideassoft.bean.imports.ErrorInfo;
import com.ideassoft.bean.imports.ImportConfig;
import com.ideassoft.core.bean.pageConfig.ColumnConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;

 
public class ImportUtil {
	
	private XSSFWorkbook wb;
	
	private XSSFSheet sheet;
	
	private XSSFRow row;
	
	private List<ImportConfig> titles;
	
	private List<Object> data;

	private Map<String, ErrorInfo> errors;
	
	private PageConfig pageConfig;
	
	private MultipartFile importFile;
	
	private GenericDAOImpl gdhimpl;
	
	private String defaultParam;
	
	public ImportUtil() {
		init();
	}
	
	public ImportUtil(PageConfig pageConfig, MultipartFile importFile, String defaultParam) {
		this.setPageConfig(pageConfig);
		this.setImportFile(importFile);
		this.setDefaultParam(defaultParam);
		init();
	}
	
	private void init() {
		titles = new ArrayList<ImportConfig>();
		data = new ArrayList<Object>();
		errors = new HashMap<String, ErrorInfo>();
		
		gdhimpl = (GenericDAOImpl) SpringUtil.getBean("genericDAOImpl");
	}
	
	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @param sheetPos sheet页编号
	 * @param titlePos 表头行编号
	 */
	private void readExcelTitle(InputStream is, int sheetPos, int titlePos) throws Exception {
		wb = new XSSFWorkbook(is);
		sheet = wb.getSheetAt(sheetPos);
		row = sheet.getRow(titlePos);
		
		String sheetName = wb.getSheetName(sheetPos);
		int colNum = row.getPhysicalNumberOfCells();

		/***封装Title***/
		ColumnConfig config;
		ImportConfig title;
		for (int i = 0; i < colNum; i++) {
			XSSFCell cell = row.getCell(i);
			if (cell != null) {
				String titleName = getStringCellValue(cell).trim();
				if (!StringUtils.isEmpty(titleName) || "null".equals(titleName)) {
					title = new ImportConfig();
					
					title.setTitleNo(titles.size());
					title.setTitleName(titleName);
					title.setSheetName(sheetName);
					
					config = findFitConfigByTitle(getPageConfig(), titleName);
					if (config != null) {
						title.setColumnConfig(config);
					} else {
						setErrorInfo(CHECKMSG_DATAFORMAT, i, titleName);
						break;
					}
					
					titles.add(titles.size(), title);
				}
			} else {
				title = new ImportConfig();
				
				title.setTitleNo(titles.size());
				title.setTitleName(BREAKER);
				title.setSheetName(sheetName);
				
				titles.add(titles.size(), title);
				
				colNum++;
			}
		}
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @param sheetPos sheet页编号
	 * @param rowPos 数据起始行编号
	 */
	private void readExcelContent(InputStream is, int sheetPos, int rowPos) throws Exception {
		wb = new XSSFWorkbook(is);
		sheet = wb.getSheetAt(sheetPos);
		int rowNum = sheet.getLastRowNum();

		row = sheet.getRow(rowPos);
		
		String cellValue, checkMsg = "";
		ColumnConfig config;
		Object bean;
		
		outter: for (int i = rowPos; i <= rowNum; i++) {
			row = sheet.getRow(i);
			
			if (row != null) {
				int colNum = row.getPhysicalNumberOfCells();
				int j = 0, breakcnt = 0;
				bean = null;
				
				while (j < colNum) {
					XSSFCell cell = row.getCell(j);
					
					if (cell != null) {
						cellValue = getStringCellValue(cell);
						if (cellValue != null && BOTTOM_TAG.equals(cellValue.trim())) {
							break outter;
						}
						
						/***封装数据***/
						config = titles.get(cell.getColumnIndex()).getColumnConfig();
						
						if (config != null) {
							if (bean == null) {
								bean = ReflectUtil.getBeanByName(getPageConfig().getTarget());
							}
							
							packageData(bean, config, cellValue, j + 1, checkMsg);
						}
							
					} else {
						breakcnt++;
						colNum++;
					}
					j++;
				}
				
				if (bean != null) {
					setBeanId(bean);
					setDefaultValues(bean);
					data.add(bean);
				}
			}
		}
		
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(XSSFCell cell) {
		String strCell = "";

		switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				strCell = cell.getRichStringCellValue().toString();
				break;
				
			case XSSFCell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(cell.getNumericCellValue());
				if (strCell.endsWith(".0")) {
					strCell.replace(".0", "");
				}
				break;
				
			case XSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
				
			case XSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
				
			default:
				strCell = "";
				break;
		}
		
		if ("".equals(strCell) || strCell == null) {
			return "";
		}
		
		if (cell == null) {
			return "";
		}
		
		return strCell;
	}
	
	private boolean isCellEmpty(String cellValue) {
		return cellValue == null || "null".equalsIgnoreCase(cellValue.trim()) 
			|| "".equals(cellValue.trim());
	}
	
	private void readTxtTitle(InputStream is, int startLine) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String[] titleNames;
		ImportConfig title;
		ColumnConfig config;
		int pos = 0;
		
		try {
			while ((line = reader.readLine()) != null) {
				if (pos < startLine) {
					pos++;
					continue;
				}
				
				titleNames = line.split("[|]");
				
				for (int i = 0; i < titleNames.length; i++) {
					title = new ImportConfig();
					title.setTitleNo(titles.size());
					title.setTitleName(titleNames[i]);
					title.setSheetName(FIRST_PAGE);
					
					config = findFitConfigByTitle(getPageConfig(), titleNames[i]);
					
					if (config != null) {
						title.setColumnConfig(config);
					} else {
						setErrorInfo(CHECKMSG_DATAFORMAT, i, titleNames[i]);
						break;
					}
					
					titles.add(titles.size(), title);
				}
				
				if (pos == startLine) {
					break;
				}
			}
		} finally {
			is.close();
		}
	}
	
	private void readTxtContent(InputStream is, int startLine) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null, checkMsg = "";
		String[] datas;
		
		ColumnConfig config;
		Object bean;
		
		int pos = 0;
		try {
			while ((line = reader.readLine()) != null) {
				if (pos < startLine) {
					pos++;
					continue;
				}
				
				datas = line.split("[|]");
				bean = null;
				
				for (int i = 0; i < datas.length; i++) {
					config = titles.get(i).getColumnConfig();
					
					if (config != null) {
						if (bean == null) {
							bean = ReflectUtil.getBeanByName(getPageConfig().getTarget());
						}
						
						packageData(bean, config, datas[i], i + 1, checkMsg);
					}
				}
				
				if (bean != null) {
					setBeanId(bean);
					setDefaultValues(bean);
					data.add(bean);
				}
			}
		} finally {
			is.close();
		}
	}
	
	private ColumnConfig findFitConfigByTitle(PageConfig pageConfig, String title) {
		List<ColumnConfig> columns = pageConfig.getColumns();
		
		ColumnConfig columnConfig = null;
		for (ColumnConfig column : columns) {
			if (column.getName().equals(title)) {
				columnConfig = column;
				break;
			}
		}
		
		return columnConfig;
	}
	
	private void packageData(Object bean, ColumnConfig config, String cellValue, int pos, String checkMsg) throws Exception {
		String value;
		cellValue = cellValue != null ? cellValue.trim() : "";
		
		checkMsg = checkCellValue(config, cellValue);
		
		if (checkMsg.length() == 0) {
			if ("date".equals(config.getEditType())) {
				Date date;
				if (isCellEmpty(cellValue))
					date = new Date();
				else 
					date = HSSFDateUtil.getJavaDate(Double.parseDouble(cellValue) - 365 * 4 - 2, true);
				
				ReflectUtil.setValue(bean, config.getColumnId(), date);
			} else if ("datetime".equals(config.getEditType())) {
				Date date;
				if (isCellEmpty(cellValue))
					date = new Date();
				else 
					date = HSSFDateUtil.getJavaDate(Double.parseDouble(cellValue), true);

				ReflectUtil.setValue(bean, config.getColumnId(), date);
			} else if ("checkbox".equals(config.getEditType()) || "select".equals(config.getEditType())) {
				if (!"".equals(cellValue)) {
					value = getTransValue(config, cellValue, checkMsg);
					if (value.length() > 0) {
						ReflectUtil.setValue(bean, config.getColumnId(), value);
					} else {
						checkMsg = CHECKMSG_NOFIT;
					}
				} else {
					checkMsg = CHECKMSG_NOTNULL;
				}
			} else if ("dialog".equals(config.getEditType())) {
				if (!"".equals(cellValue)) {
					value = getDialogValue(config, cellValue, checkMsg);
					if (value.length() > 0) {
						ReflectUtil.setValue(bean, config.getColumnId(), value);
					} else {
						checkMsg = CHECKMSG_NOFIT;
					}
				} else {
					checkMsg = CHECKMSG_NOTNULL;
				}
			} else if ("number".equals(config.getEditType()) || "textarea".equals(config.getEditType()) 
					|| config.getEditType() == null) {
				ReflectUtil.setValue(bean, config.getColumnId(), cellValue);
			} else {
				checkMsg = CHECKMSG_DATAILLEGAL;
			}
		}
		
		if (checkMsg.length() > 0) {
			setErrorInfo(checkMsg, pos, cellValue);
		}
	}
	
	private void setDefaultValues(Object bean) throws Exception {
		JSONObject jo = new JSONObject(getDefaultParam());
		String[] names = JSONObject.getNames(jo);
		String fieldName;
		for (String name : names) {
			fieldName = ReflectUtil.getTruelyFieldName(bean, name);
			if (fieldName != null && ReflectUtil.getValue(bean, fieldName) == null) {
				ReflectUtil.setValue(bean, name, jo.get(name));
			}
		}
	}
	
	private String checkCellValue(ColumnConfig config, String cellValue) throws Exception {
		String checkMsg = "";
		String validateRule = config.getValidateRule();
		
		if (validateRule != null) {
			String[] rules = validateRule.split(",");
			for (String rule : rules) {
				if (rule.startsWith("MAX")) {
					if (Double.parseDouble(cellValue) > Double.parseDouble(rule.split(":")[1])) {
						checkMsg = CHECKMSG_MAX + "[" + rule.split(":")[0] + "]";
					}
				} else if (rule.startsWith("MIN")) {
					if (Double.parseDouble(cellValue) < Double.parseDouble(rule.split(":")[1])) {
						checkMsg = CHECKMSG_MIN + "[" + rule.split(":")[0] + "]";
					}
				} else if (rule.startsWith("LENGTH")) {
					if (cellValue.trim().length() > Integer.parseInt(rule.split(":")[1])) {
						checkMsg = CHECKMSG_LENGTH + "[" + rule.split(":")[0] + "]";
					}
				} else if("UNIQUE".equalsIgnoreCase(rule)) {
					if(isDuplicate(config.getColumnId(), cellValue)) {
						checkMsg = CHECKMSG_UNIQUE;
					}
				} else {
					if (!RegExUtil.match(ReflectUtil.getFieldInitValue(new RegExUtil(), rule).toString(), cellValue)) {
						if ("NOTNULL".equalsIgnoreCase(rule)) {
							checkMsg = CHECKMSG_NOTNULL;
						} else if("NUMBER".equalsIgnoreCase(rule)) {
							checkMsg = CHECKMSG_NUMBER;
						} else if("EMAIL".equalsIgnoreCase(rule)) {
							checkMsg = CHECKMSG_EMAIL;
						} else if("DATE".equalsIgnoreCase(rule) || "TIME".equalsIgnoreCase(rule)) {
							checkMsg = CHECKMSG_DATE;
						} else if("IP".equalsIgnoreCase(rule)) {
							checkMsg = CHECKMSG_IP;
						} else if("IDCARD".equalsIgnoreCase(rule)) {
							checkMsg = CHECKMSG_IDCARD;
						} else if("MOBILE".equalsIgnoreCase(rule)) {
							checkMsg = CHECKMSG_MOBILE;
						}
					} else {
						if("DATE".equalsIgnoreCase(rule) || "TIME".equalsIgnoreCase(rule)) {
							String el = "DATE".equalsIgnoreCase(rule) ? "yyyy/MM/dd" : "yyyy/MM/dd HH:mm:ss";
							try {
								new SimpleDateFormat(el).parse(cellValue.trim());
							} catch (Exception e) {
								checkMsg = CHECKMSG_DATE;
							}
						}
					}
				}
			}
		}
		
		return checkMsg;
	}
	
	private void setErrorInfo(String checkMsg, int position, String cellValue) {
		ErrorInfo ei = new ErrorInfo();
		
		if (!titles.isEmpty()) {
			ImportConfig config = titles.get(position - 1);
			ei.setErrorLocation(config.getSheetName());
			ei.setTitle(config.getTitleName());
			ei.setPostion(position);
			ei.setErrorData(cellValue);
			ei.setErrorMsg(checkMsg);
		} else {
			ei.setErrorLocation(FIRST_PAGE);
			ei.setTitle(FIRST_COLUMN);
			ei.setPostion(1);
			ei.setErrorData(cellValue);
			ei.setErrorMsg(checkMsg);
		}
		
		errors.put(String.valueOf(errors.size()), ei);
	}
	
	private String getDialogValue(ColumnConfig config, String cellValue, String checkMsg) throws Exception {
		String dialogColumns = config.getDialogColumns(), value = "";
		
		String hql = " from " + config.getDialogTarget() + " where status = 1";
		List<?> result = gdhimpl.findByHQL(hql);
		
		if (result != null) {
			for (Object object : result) {
				if(cellValue.equals(ReflectUtil.getValue(object, dialogColumns.split(",")[1]))) {
					value = ReflectUtil.getValue(object, dialogColumns.split(",")[0]).toString();
					break;
				}
			}
		}

		return value;
	}
	
	private String getTransValue(ColumnConfig config, String cellValue, String checkMsg) throws Exception {
		String editValue = config.getEditValue(), value = "";
		cellValue = cellValue.trim();
		
		if (editValue == null) {
			editValue = config.getValue();
			
			if (editValue != null) {
				String executeSql = getPageConfig().getDataFormats().get(editValue);
				String sqlType = getPageConfig().getType();
				
				List<?> result = getTransValues(executeSql, sqlType);
				
				if (result != null) {
					for (Object object : result) {
						if(cellValue.equals(ReflectUtil.getValue(object, "label"))
							|| cellValue.equals(ReflectUtil.getValue(object, "value"))) {
							value = ReflectUtil.getValue(object, "value").toString();
							break;
						}
					}
				}
			} else {
				checkMsg = CHECKMSG_DATAILLEGAL;
			}
		} else {
			String[] values = editValue.split(";");
			for (String v : values) {
				if (v.split(":")[0].equalsIgnoreCase(cellValue) || v.split(":")[1].equalsIgnoreCase(cellValue)) {
					value = v.split(":")[0];
					break;
				}
			}
		}
		
		return value;
	}
	
	private List<?> getTransValues(String executeSqll, String sqlType) throws Exception {
		if (SystemConstants.SQLType.SQL.equals(sqlType)) {
			return gdhimpl.findBySQL(executeSqll);
		} else {
			return gdhimpl.findByHQL(executeSqll);
		}
	}
	
	private boolean isDuplicate(String colName, String value) throws Exception {
		Object target = ReflectUtil.getBeanByName(getPageConfig().getTarget());
		colName = ReflectUtil.getTruelyFieldName(target, colName);
		return gdhimpl.findOneByProperties(target.getClass(), colName, value) != null;
	}
	
	private void setBeanId(Object bean) throws Exception {
		List<ColumnConfig> configs = getPageConfig().getColumns();
		JSONObject jo = new JSONObject();
		String idName = "", tempId = null;
		
		for (ColumnConfig config : configs) {
			if (config.getComplexKey() != null) {
				if (!"".equals(idName)) {
					idName += "|" + config.getColumnId();
				} else {
					tempId = ReflectUtil.getTruelyFieldName(bean, config.getComplexKey());
					idName += config.getComplexKey() + "(" + config.getColumnId();
				}
			} else if (config.isPrimaryKey()) {
				jo.put(config.getColumnId(), gdhimpl.getSequence(config.getSequence()));
				idName = config.getColumnId();
				tempId = ReflectUtil.getTruelyFieldName(bean, idName);
			}
		}
		
		if (idName.indexOf("|") != -1) {
			idName += ")";
		}
		
		if (tempId != null && ReflectUtil.getValue(bean, tempId) == null) {
			ReflectUtil.setBeanId(jo, bean, idName, null, null);
		}
		
	}

	private void saveData() {
		gdhimpl.getHibernateTemplate().saveOrUpdateAll(data);
	}
	
	public Map<String, ErrorInfo> importData() throws Exception {
		String fileName = getImportFile().getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if (SystemConstants.ImportFileType.EXCEL.equalsIgnoreCase(fileType)) {
			importExcel();
		} else {
			importTxt();
		}
		
		if (errors.isEmpty()) {
			saveData();
		}

		return errors;
	}
	
	private void importExcel() throws Exception {
		InputStream readTitle = getImportFile().getInputStream();
		readExcelTitle(readTitle, 0, 0);
		readTitle.close();

		if (errors.isEmpty()) {
			InputStream readContent = getImportFile().getInputStream();
			readExcelContent(readContent, 0, 1);
			readContent.close();
		}
	}
	
	private void importTxt() throws Exception {
		InputStream readTitle = getImportFile().getInputStream();
		readTxtTitle(readTitle, 0);

		if (errors.isEmpty()) {
			InputStream readContent = getImportFile().getInputStream();
			readTxtContent(readContent, 1);
		}
	}
	
	public void setPageConfig(PageConfig pageConfig) {
		this.pageConfig = pageConfig;
	}

	public PageConfig getPageConfig() {
		return pageConfig;
	}

	public void setImportFile(MultipartFile importFile) {
		this.importFile = importFile;
	}

	public MultipartFile getImportFile() {
		return importFile;
	}

	public void setDefaultParam(String defaultParam) {
		this.defaultParam = defaultParam;
	}

	public String getDefaultParam() {
		return defaultParam;
	}

	private final static String FIRST_PAGE = "第一页";
	
	private final static String FIRST_COLUMN = "第一列";
	/**excel表间断隔**/
	private final static String BREAKER = "#breaker#";
	/**excel数据底部标识**/
	private final static String BOTTOM_TAG = "END";
	/**错误信息**/
	private final static String CHECKMSG_DATAILLEGAL = "包含非法数据！";
	
	private final static String CHECKMSG_DATAFORMAT = "文件数据格式错误！";
	
	private final static String CHECKMSG_NOTNULL = "数据不可为空！";

	private final static String CHECKMSG_NUMBER = "数据不是数字！";
	
	private final static String CHECKMSG_EMAIL = "EMAIl格式错误";
	
	private final static String CHECKMSG_DATE = "日期格式错误！";
	
	private final static String CHECKMSG_IP = "IP地址格式错误";
	
	private final static String CHECKMSG_IDCARD = "身份信息验证失败！";

	private final static String CHECKMSG_MOBILE = "手机号码格式错误！";
	
	private final static String CHECKMSG_MAX = "超过最大值";
	
	private final static String CHECKMSG_MIN = "小于最小值";
	
	private final static String CHECKMSG_LENGTH = "数据长度过长,最大长度";

	private final static String CHECKMSG_UNIQUE = "数据重复！";
	
	private final static String CHECKMSG_NOFIT = "无匹配数据！";
	
}

