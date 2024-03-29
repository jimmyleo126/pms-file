package com.ideassoft.core.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ideassoft.core.bean.pageConfig.ColumnConfig;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RegExUtil;

@Service
public class PageBuilder extends GenericDAOImpl implements IPageBuilder{
	private static Logger logger = Logger.getLogger(PageBuilder.class);
	
	protected static Map<String, ModelConfig> modelConfigs = null;
	
	public PageBuilder() {
		init();
	}
	
	public void init() {
		if (modelConfigs == null) {
			try {
				loadConfigs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void buildBegin(String modelId, String pageId) throws Exception {
		
	}
	
	public void buildConditions(String modelId, String pageId) throws Exception {
		PageConfig pageConfig = getPageConfigByPageId(modelId, pageId);
		
		Map<String, String> conditions = pageConfig.getConditions();
		if (conditions != null) {
			Map<String, String> seperators = new HashMap<String, String>();
			String condition;
			int i = 0;
			Entry<String, String> entry;
			for (Iterator<Entry<String, String>> it = conditions.entrySet().iterator(); it.hasNext(); i++) {
				entry = (Entry<String, String>) it.next();
				if (i != 0 && i % 3 == 0) {
					condition = entry.getKey();
					seperators.put(condition.toLowerCase() + "span", "</tr><tr>");
				}
			}
			pageConfig.setSeperators(seperators);
		}
	}

	@SuppressWarnings("unchecked")
	public void buildRequiredData(String modelId, String pageId, Map<String, Object> commonParams) throws Exception {
		PageConfig pageConfig = getPageConfigByPageId(modelId, pageId);
		Map<String, Object> requiredData = pageConfig.getRequiredData();

		String value;
		List<?> result;
		if (requiredData != null) {
			Map<String, String> dataFormats = pageConfig.getDataFormats();
			
			Iterator<Entry<String, Object>> it = requiredData.entrySet().iterator();
			Entry<String, Object> entry;
			Map<String, Object> data;
			while (it.hasNext()) {
				value = "";
				entry = it.next();
				
				if (pageConfig.getLazy() == null || (pageConfig.getLazy().containsKey(entry.getKey())
						&& !pageConfig.getLazy().get(entry.getKey()))) {
					try {
						result = findBySQL(dataFormats.get(entry.getKey()), pageConfig.getStrategy() != null);
						for (int i = 0; i < result.size(); i++) {
							data = (Map<String, Object>) result.get(i);
							if (i > 0) value += ";";
							value += data.get("value") + (data.get("label") == null ? "" : ":" + data.get("label"));
						}
						requiredData.put(entry.getKey(), value);
					} catch (Exception e) {
						continue;
					}
				}
			}
		} else {
			requiredData = new HashMap<String, Object>();
		}
		
		//dialog editType
		List<ColumnConfig> columns = pageConfig.getColumns();
		if (columns != null && !columns.isEmpty()) {
			for (ColumnConfig column : columns) {
				if (column.getEditType() != null && RegExUtil.match("dialog|dialog-multi|dialog-radio", column.getEditType())) {
					value = "";
					result = queryCustomDialogData(column.getDialogTarget(), column.getDialogColumns(),
							column.getDialogConditions(), null, null, commonParams);
					if (result != null && !result.isEmpty()) {
						for (Object obj : result) {
							value += ReflectUtil.getValue(obj, "value") + ":" + ReflectUtil.getValue(obj, "label") + ";";
						}
						requiredData.put(column.getColumnId(), value.substring(0, value.length() - 1));
					}
				}
			}
		}
		
		if (requiredData != null && !requiredData.isEmpty()) {
			pageConfig.setRequiredData(requiredData);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> buildData(String modelId, String pageId, Map<String, Object> params, 
			Map<String, Object> commonParams) throws Exception {
		Pagination pagination = (Pagination) params.get("pagination");
		Map<String, Object> conds = (Map<String, Object>) params.get("params");
		return queryData(modelId, pageId, conds, pagination, false, 
				SystemConstants.QueryType.QUERY, commonParams);
	}

	public void buildEnd(String modelId, String pageId) throws Exception {
		PageConfig pageConfig = getPageConfigByPageId(modelId, pageId);
		pageConfig.setLoaded(true);
	}

	public void construct(String modelId, String pageId, Map<String, Object> commonParams) {
		PageConfig pageConfig = getPageConfigByPageId(modelId, pageId);
		try {
			if (!pageConfig.isLoaded()) {
				buildBegin(modelId, pageId);
				buildConditions(modelId, pageId);
				buildEnd(modelId, pageId);
			}
			buildRequiredData(modelId, pageId, commonParams);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Map<String, Object> queryData(String modelId, String pageId, Map<String, Object> params, 
			String dataType, Map<String, Object> commonParams) throws Exception {
		return queryData(modelId, pageId, params, null, false, dataType, commonParams);
	}
	
	public Map<String, Object> queryData(String modelId, String pageId, Map<String, Object> params, 
			boolean chartFlag, String dataType, Map<String, Object> commonParams) throws Exception {
		return queryData(modelId, pageId, params, null, chartFlag, dataType, commonParams);
	}
	
	public Map<String, Object> queryData(String modelId, String pageId, Map<String, Object> params, 
			Pagination pagination, boolean chartFlag, String dataType, Map<String, Object> commonParams) throws Exception {
		PageConfig pageConfig = getPageConfigByPageId(modelId, pageId);
		if (pageConfig == null) {
			logger.error("page config error! check the configration!");
			return null;
		}

		Integer rows = 20, records = 0;
		if (pagination != null) {
			rows = pagination.getRows();
		}
		
		String queryType = pageConfig.getType();
		String sqlOrProc = null;
		
		if (pageConfig.getDataFormats() != null) {
			if (SystemConstants.QueryType.EXCEL.equals(dataType)) {
				sqlOrProc = pageConfig.getDataFormats().get(SystemConstants.QueryType.EXCEL);
			} else if (SystemConstants.QueryType.CHART.equals(dataType)) {
				sqlOrProc = pageConfig.getDataFormats().get(SystemConstants.QueryType.CHART);
			}
		}
		
		if (sqlOrProc == null) {
			sqlOrProc = pageConfig.getSql();
		}
		
		if (pageConfig.getStrategy() != null) {
			if (SystemConstants.SqlStrategy.DEFAULT.equals(pageConfig.getStrategy())) {
				sqlOrProc = findQuerySQL(sqlOrProc, true);
			} else {
				sqlOrProc = findQuerySQL(SqlBuilder.getSqlStrategy(commonParams, pageConfig.getStrategy(), sqlOrProc), true);
			}
		}
		
		List<?> result = null;
		
		if (SystemConstants.SQLType.SQL.equals(queryType)) {
			sqlOrProc = SqlBuilder.setDefaultInfo(commonParams, sqlOrProc);
			//使用SQL查询
			if (pagination != null) {
				//获取查询结果总数
				records = findSQLRecordsCount(sqlOrProc, params);
				
				pagination.setRecords(records);
				pagination.setTotal(records / rows + 1);
				sqlOrProc = SqlBuilder.buildSqlByPagination(sqlOrProc, pagination);
			} else if (chartFlag) {
				sqlOrProc = SqlBuilder.buildChartSql(sqlOrProc, pageConfig.getCharts().get("latitude"));
			}
			
			result = findBySQL(sqlOrProc, params);
			
		} else if(SystemConstants.SQLType.HQL.equals(queryType)) {
			if (sqlOrProc == null) {
				sqlOrProc = SqlBuilder.buildQueryHql(pageConfig.getTarget());
			}
			
			sqlOrProc = SqlBuilder.setDefaultInfo(commonParams, sqlOrProc);
			
			if (pagination != null) {
				records = findRecordsCount(sqlOrProc, params);
				
				pagination.setRecords(records);
				pagination.setTotal(records / rows + 1);
				
				result = findByHQLWithPagination(sqlOrProc, params, pagination.getPageNum(), pagination.getRows());
			} else {
				result = findByHQL(sqlOrProc, params);
			}
			
			result = ReflectUtil.transObjectToMap(result);
		} else if (SystemConstants.SQLType.PROCEDURE.equals(queryType)) {
			//TODO 调用用过程查询
			result = callProc(sqlOrProc, "");
		} else {
			logger.error("page config error! page id " + pageId);
			return null;
		}
		
		if (pageConfig.getFunctions() != null && pageConfig.getFunctions().get("EDIT") != null) {
			result = SqlBuilder.rebuildResult(result);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", pagination);
		map.put("result", result);
		
		return map;
	}
	
	public void loadConfigs() throws Exception {
		Map<String, ModelConfig> xmlConfigs = ConfigLoader.loadConfig();
		setModelConfigs(xmlConfigs);
	}
	
	public Object queryMultiData(String modelId, String pageId, String param, String value) {
		PageConfig pageConfig = getPageConfigByPageId(modelId, pageId);
		
		List<?> result = findBySQL(pageConfig.getDataFormats().get(param), new String[] { value });

		Map<?, ?> data;
		Object ret = new Object();
		for (int i = 0; i < result.size(); i++) {
			data = (Map<?, ?>) result.get(i);
			if (data.get("label") != null && data.get("value") != null) {
				if (i > 0) ret = ret.toString() + ";";
				ret = (i == 0 ? "" : ret.toString()) + data.get("value") + ":" + data.get("label");
			} else {
				ret = data;
				break;
			}
		}
		
		return ret;
	}
	
	private void setModelConfigs (Map<String, ModelConfig> configs) {
		modelConfigs = configs;
	}
	
	public Map<String, ModelConfig> getModelConfigs() throws Exception {
		if (modelConfigs == null) {
			loadConfigs();
		}
		return modelConfigs;
	}
	
	public ModelConfig getModelConfig(String modelId) {
		return modelConfigs.get(modelId);
	}

	public Map<String, PageConfig> getPageConfigsByModelId(String modelId) {
		return getModelConfig(modelId).getPageConfigs();
	}
	
	public PageConfig getPageConfigByPageId(String modelId, String pageId) {
		return getPageConfigsByModelId(modelId).get(pageId);
	}
	
	public ModelConfig findModleByPageId(String pageId) {
		int i = 0;
		ModelConfig model = null;
		Entry<String, ModelConfig> entry;
		Map<String, PageConfig> pageConfigs;
		for (Iterator<Entry<String, ModelConfig>> it = modelConfigs.entrySet().iterator(); it.hasNext(); i++) {
			entry = (Entry<String, ModelConfig>) it.next();
			pageConfigs = entry.getValue().getPageConfigs();
			if (pageConfigs.containsKey(pageId)) {
				model = entry.getValue(); 
				break;
			}
		}
		return model;
	}
	
	public List<?> queryCustomDialogData(String dialogTarget, String dialogColumns, 
			String dialogConditions, String queryData, Pagination pagination, Map<String, Object> commonParams) {
		List<Object> result = new ArrayList<Object>();
		List<?> tempList;
		String[] targets = dialogTarget.split(","),
				 beanColumns = dialogColumns.split("[|]");
		
		for (int i = 0; i < targets.length; i++) {
			String[] columns = beanColumns[i].split(",");
			String hql = " select new DialogBean(" + columns[0] + " as value, " + columns[1] + " as label) from " + targets[i];
			if (queryData != null && queryData.trim().length() > 0) {
				hql += " where " + columns[0] + " like '" + queryData + "%' or " 
					+ columns[1] + " like '" + queryData + "%' and ";
			} else {
				hql += " where ";
			}
			
			if (!StringUtils.isEmpty(dialogConditions)) {
				hql += dialogConditions;
			} else {
				hql += "status = 1";
			}
			
			hql += " order by " + columns[0];
			
			hql = SqlBuilder.setDefaultInfo(commonParams, hql);
			
			if (pagination != null) {
				int records = findRecordsCount(hql, new Object[0]);
				pagination.setRecords(records);
				pagination.setTotal(records / pagination.getRows() + 1);
				
				tempList = findByHQLWithPagination(hql, new Object[0], pagination.getPageNum(), pagination.getRows());
			} else {
				tempList = findByHQL(hql);
			}
			
			if (tempList != null && !tempList.isEmpty()) {
				result.addAll(tempList);
			}
		}
		
		return result;
	}
	
	public Object findByCond(String beanName, String colName, String colValue, 
			String cond) throws Exception {
		String hql = " from " + beanName + " where " + colName + " = '" + colValue + "'" + cond;
		List<?> result =  findByHQL(hql);
		return result != null && !result.isEmpty() ? result : null;
	}
}

