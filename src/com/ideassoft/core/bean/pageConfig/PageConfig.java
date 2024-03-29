package com.ideassoft.core.bean.pageConfig;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Transient;


/**
 * 页面配置类
 * @author ZenShin
 * @time 2014-04-16
 */
public class PageConfig implements Cloneable, Serializable{
	
	@Transient
	private static final long serialVersionUID = -7213241338537011467L;

	/***页面ID***/
	private String pageId;

	/***页面名称***/
	private String name;
	
	/***功能图标***/
	private String icon;
	
	/***以窗口打开, 默认为tab打开, 参数为self/blank***/
	private String openWin;
	
	/***查询条件，以','分隔***/
	private Map<String, String> conditions;
	
	/***页面功能，以','分隔***/
	private Map<String, String> functions;
	
	/***页面打印功能调用的打印模板***/
	private String printTemplate;
	
	/***页面展示的列***/
	private List<ColumnConfig> columns;
	
	/***查询类型 sql/hql/procedure***/
	private String type;
	
	/***查询调用的sql/hql或者存储过程名,或配置SqlInfo中的sqlName(配置多个时以,分隔)***/
	private String sql;
	
	/***当sql需对应SqlInfo,并配置多项sql时,可配置sql的查询切换策略***/
	/***切换策略配置于SqlBuilder的getStrategy方法中***/
	/***若无需切换sql策略,仅作SqlInfo中的配置时,必须配置默认值default***/
	private String strategy;
	
	/***保存数据时所需页面外的额外数据***/
	private TreeMap<String, String> params;
	
	/***页面展示的图表  Pie,Column,Line,另配置参数latitude用于数据展示维度  [维度:数值]***/
	private Map<String, String> charts;
	
	/***图表说明页***/
	private String chartInfo;
	
	/***图表点击事件***/
	private String chartEvent;
	
	/***图表数据点击事件***/
	private String chartDataEvent;
	
	/***查询条件分隔符***/
	private Map<String, String> seperators;
	
	/***自定义页面url***/
	private String url;
	
	/***页面数据保存时对应的javaBean，以','分隔***/
	private String target;
	
	/***对应dataFormat配置的键值，用于表格初始化时的取值***/
	private Map<String, Object> requiredData;
	
	/***页面数据取值***/
	private Map<String, String> dataFormats;
	
	/***是否为tab页,默认false***/
	private boolean tab;
	
	/***页面为tab子页时对应的主页面id***/
	private String containerId;
	
	/***页面下有tab页时，下属的tab页id，以','分隔***/
	private Map<String, String> tabs;
	
	/***页面为自定义tab子页时，跳转的url***/
	private Map<String, String> tabUrls;
	
	/***编辑方式 form/line/other 不配置则不可编辑***/
	private String editing;
	
	/***判断页面配置是否已经加载***/
	private boolean loaded;
	
	/***自定义编辑方法***/
	private String editFunc;
	
	/***自定义新增方法***/
	private String addFunc;
	
	/***自定义保存方法***/
	private String saveFunc;
	
	/***自定义删除方法,删除后必需调用refreshGrid刷新表格***/
	private String delFunc;
	
	/***自定义查询方法***/
	private String queryFunc;
	
	/***统计列***/
	private String sumCols;
	
	/***表格长宽  示例 800,600***/
	private String gridSquare;
	
	/***功能栏和条件栏显示比例，总值100  示例 40,60***/
	private String funcCondRate;
	
	/***父节点***/
	private String parentNode;
	
	/***参数页***/
	private String paramUrl;
	
	/***form表单编辑页提交URL***/
	private String formSubmitUrl;
	
	/***记录日志,值为LogService中调用的方法名***/
	private String recordLog;
	
	/***表格加载后的回调函数***/
	private String afterLoad;
	
	/***查询数据后的回调函数***/
	private String afterQuery;
	
	/***数据处理前置***/
	private String beforeDealingData;
	
	/***是否子页,默认false***/
	private boolean subWin;
	
	/***dataFormats下的SQL是否需要懒加载***/
	private Map<String, Boolean> lazy;
	
	/***展示类型 grid,list***/
	private String showType;
	
	/***关闭表单宽度自适应, 默认false***/
	private boolean noShrink;
	
	/***初始化时是否查询数据, 默认false***/
	private boolean loadOnRender;
	
	/***初始展示列数***/
	private Integer rowNum;
	
	/***是否展示,默认true***/
	private boolean show;
	
	/***展示为首页,用于自定义页面,唯一配置项,配置同ModelAndView的setViewName***/
	private String firstPage;
	
	/***是否使用右键菜单,默认false***/
	private boolean showMenu;

	/***上传文件类型,类型有image、file、data,image支持图片格式,file支持docx和txt格式,data为默认,支持xslx和txt***/
	private String uploadFileType;
	
	/***上传文件名称***/
	private String uploadFileName;

	/***上传文件存放文件夹***/
	private String uploadFileDir;
	
	/***设置页面是否可编辑,若为true,页面即使配置EDIT也只能进行新增,无法进行编辑***/
	private boolean readOnly;
	
	/***当页面新增/编辑的dialog可以直接新增数据,并且指向的bean的ID和NAME字段命名不规范时,需指定该项***/
	private String idAndName;
	
	public PageConfig() {

	}

	public PageConfig(String pageId, String name, String icon, String openWin, Map<String, String> conditions,
			Map<String, String> functions, String printTemplate, List<ColumnConfig> columns, String type, 
			String sql, String strategy, TreeMap<String, String> params, Map<String, String> charts, String url, 
			String target, Map<String, Object> requiredData, Map<String, String> dataFormats, boolean isTab, 
			Map<String, String> tabs, Map<String, String> tabUrls, String containerId, String editing, 
			String editFunc, boolean isLoaded, String sumCols, String gridSquare, String parentNode, 
			String paramUrl, String formSubmitUrl, String recordLog, String afterQuery, String beforeDealingData, 
			String saveFunc, String addFunc, String delFunc, boolean subWin, String chartInfo, 
			Map<String, Boolean> lazy, String showType, boolean noShrink, String afterLoad, boolean loadOnRender, 
			Integer rowNum, boolean show, String queryFunc, String firstPage, String funcCondRate, boolean showMenu,
			String uploadFileType, String uploadFileName, String uploadFileDir, boolean readOnly, String idAndName) {
		this.setPageId(pageId);
		this.setName(name);
		this.setIcon(icon);
		this.setOpenWin(openWin);
		this.setConditions(conditions);
		this.setFunctions(functions);
		this.setPrintTemplate(printTemplate);
		this.setColumns(columns);
		this.setType(type);
		this.setSql(sql);
		this.setStrategy(strategy);
		this.setParams(params);
		this.setCharts(charts);
		this.setUrl(url);
		this.setTarget(target);
		this.setRequiredData(requiredData);
		this.setDataFormats(dataFormats);
		this.setTab(isTab);
		this.setTabs(tabs);
		this.setTabUrls(tabUrls);
		this.setContainerId(containerId);
		this.setEditing(editing);
		this.setLoaded(isLoaded);
		this.setEditFunc(editFunc);
		this.setSumCols(sumCols);
		this.setGridSquare(gridSquare);
		this.setParentNode(parentNode);
		this.setParamUrl(paramUrl);
		this.setFormSubmitUrl(formSubmitUrl);
		this.setRecordLog(recordLog);		
		this.setAfterQuery(afterQuery);
		this.setBeforeDealingData(beforeDealingData);
		this.setSaveFunc(saveFunc);
		this.setAddFunc(addFunc);
		this.setDelFunc(delFunc);
		this.setSubWin(subWin);
		this.setChartInfo(chartInfo);
		this.setLazy(lazy);
		this.setShowType(showType);
		this.setNoShrink(noShrink);
		this.setAfterLoad(afterLoad);
		this.setLoadOnRender(loadOnRender);
		this.setRowNum(rowNum);
		this.setShow(show);
		this.setQueryFunc(queryFunc);
		this.setFirstPage(firstPage);
		this.setFuncCondRate(funcCondRate);
		this.setShowMenu(showMenu);
		this.setUploadFileType(uploadFileType);
		this.setUploadFileName(uploadFileName);
		this.setUploadFileDir(uploadFileDir);
		this.setReadOnly(readOnly);
		this.setIdAndName(idAndName);
	}
	
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageId() {
		return pageId;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	public Map<String, String> getConditions() {
		return this.conditions;
	}

	public void setColumns(List<ColumnConfig> columns) {
		this.columns = columns;
	}

	public List<ColumnConfig> getColumns() {
		return columns;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setCharts(Map<String, String> charts) {
		this.charts = charts;
	}

	public Map<String, String> getCharts() {
		return charts;
	}

	public void setFunctions(Map<String, String> functions) {
		this.functions = functions;
	}

	public Map<String, String> getFunctions() {
		return functions;
	}

	public void setPrintTemplate(String printTemplate) {
		this.printTemplate = printTemplate;
	}

	public String getPrintTemplate() {
		return printTemplate;
	}

	public void setParams(TreeMap<String, String> params) {
		this.params = params;
	}

	public TreeMap<String, String> getParams() {
		return params;
	}

	public void setSeperators(Map<String, String> seperators) {
		this.seperators = seperators;
	}

	public Map<String, String> getSeperators() {
		return seperators;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}

	public void setRequiredData(Map<String, Object> requiredData) {
		this.requiredData = requiredData;
	}

	public Map<String, Object> getRequiredData() {
		return requiredData;
	}

	public void setDataFormats(Map<String, String> dataFormats) {
		this.dataFormats = dataFormats;
	}

	public Map<String, String> getDataFormats() {
		return dataFormats;
	}

	public void setTabs(Map<String, String> tabs) {
		this.tabs = tabs;
	}

	public Map<String, String> getTabs() {
		return tabs;
	}
	
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setTab(boolean tab) {
		this.tab = tab;
	}

	public boolean isTab() {
		return tab;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setTabUrls(Map<String, String> tabUrls) {
		this.tabUrls = tabUrls;
	}

	public Map<String, String> getTabUrls() {
		return tabUrls;
	}

	public void setEditing(String editing) {
		this.editing = editing;
	}

	public String getEditing() {
		return editing;
	}

	public void setEditFunc(String editFunc) {
		this.editFunc = editFunc;
	}

	public String getEditFunc() {
		return editFunc;
	}

	public void setOpenWin(String openWin) {
		this.openWin = openWin;
	}

	public String getOpenWin() {
		return openWin;
	}

	public void setSumCols(String sumCols) {
		this.sumCols = sumCols;
	}

	public String getSumCols() {
		return sumCols;
	}

	public void setGridSquare(String gridSquare) {
		this.gridSquare = gridSquare;
	}

	public String getGridSquare() {
		return gridSquare;
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}

	public String getParentNode() {
		return parentNode;
	}

	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}

	public String getParamUrl() {
		return paramUrl;
	}

	public void setFormSubmitUrl(String formSubmitUrl) {
		this.formSubmitUrl = formSubmitUrl;
	}

	public String getFormSubmitUrl() {
		return formSubmitUrl;
	}

	public void setRecordLog(String recordLog) {
		this.recordLog = recordLog;
	}

	public String getRecordLog() {
		return recordLog;
	}

	public void setAfterQuery(String afterQuery) {
		this.afterQuery = afterQuery;
	}

	public String getAfterQuery() {
		return afterQuery;
	}

	public void setBeforeDealingData(String beforeDealingData) {
		this.beforeDealingData = beforeDealingData;
	}

	public String getBeforeDealingData() {
		return beforeDealingData;
	}

	public void setAddFunc(String addFunc) {
		this.addFunc = addFunc;
	}

	public String getAddFunc() {
		return addFunc;
	}

	public void setSaveFunc(String saveFunc) {
		this.saveFunc = saveFunc;
	}

	public String getSaveFunc() {
		return saveFunc;
	}

	public void setDelFunc(String delFunc) {
		this.delFunc = delFunc;
	}

	public String getDelFunc() {
		return delFunc;
	}

	public void setSubWin(boolean subWin) {
		this.subWin = subWin;
	}

	public boolean isSubWin() {
		return subWin;
	}

	public void setChartInfo(String chartInfo) {
		this.chartInfo = chartInfo;
	}

	public String getChartInfo() {
		return chartInfo;
	}

	public void setLazy(Map<String, Boolean> lazy) {
		this.lazy = lazy;
	}

	public Map<String, Boolean> getLazy() {
		return lazy;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getShowType() {
		return showType;
	}

	public void setNoShrink(boolean noShrink) {
		this.noShrink = noShrink;
	}

	public boolean isNoShrink() {
		return noShrink;
	}

	public void setChartEvent(String chartEvent) {
		this.chartEvent = chartEvent;
	}

	public String getChartEvent() {
		return chartEvent;
	}

	public void setChartDataEvent(String chartDataEvent) {
		this.chartDataEvent = chartDataEvent;
	}

	public String getChartDataEvent() {
		return chartDataEvent;
	}

	public void setAfterLoad(String afterLoad) {
		this.afterLoad = afterLoad;
	}

	public String getAfterLoad() {
		return afterLoad;
	}

	public void setLoadOnRender(boolean loadOnRender) {
		this.loadOnRender = loadOnRender;
	}

	public boolean isLoadOnRender() {
		return loadOnRender;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isShow() {
		return show;
	}

	public void setQueryFunc(String queryFunc) {
		this.queryFunc = queryFunc;
	}

	public String getQueryFunc() {
		return queryFunc;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFuncCondRate(String funcCondRate) {
		this.funcCondRate = funcCondRate;
	}

	public String getFuncCondRate() {
		return funcCondRate;
	}

	public void setShowMenu(boolean showMenu) {
		this.showMenu = showMenu;
	}

	public boolean isShowMenu() {
		return showMenu;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileType(String uploadFileType) {
		this.uploadFileType = uploadFileType;
	}

	public String getUploadFileType() {
		return uploadFileType;
	}

	public void setUploadFileDir(String uploadFileDir) {
		this.uploadFileDir = uploadFileDir;
	}

	public String getUploadFileDir() {
		return uploadFileDir;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setIdAndName(String idAndName) {
		this.idAndName = idAndName;
	}

	public String getIdAndName() {
		return idAndName;
	}

	public PageConfig clone() {
		PageConfig config = null;
		try {
			config = (PageConfig) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return config;
	}
	
}
