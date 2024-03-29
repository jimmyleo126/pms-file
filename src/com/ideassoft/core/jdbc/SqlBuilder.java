
package com.ideassoft.core.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.jdbc.datasource.DataSourceContextHolder;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.util.ReflectUtil;

public class SqlBuilder {
	
	public static String buildSqlByPagination(String sql, Pagination pagination) {
		Integer pageNum = pagination.getPageNum();
		Integer rows = pagination.getRows();
		
		StringBuilder builder = new StringBuilder();
		String dbType = DataSourceContextHolder.getCustomerType();
		
		if (dbType == null || DataSourceContextHolder.DS_ORACLE.equals(dbType)) {
			builder.append("select r.* from (select t.*, rownum rn from (");
			builder.append(sql).append(") t) r where r.rn > ").append((pageNum - 1) * rows);
			builder.append(" and r.rn <= ").append(pageNum * rows);
		} else if (DataSourceContextHolder.DS_MYSQL.equals(dbType)) {
			builder.append(sql).append("limit ").append((pageNum - 1) * rows).append(", ").append(rows);
		} else if (DataSourceContextHolder.DS_SQLSERVER.equals(dbType)) {
			
		}
		
		return builder.toString();
	}
	
	public static String buildCountSql(String sql) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) cnt from (");
		builder.append(sql).append(")");
		return builder.toString();
	}
	
	public static String buildQueryHql(String target) {
		return " from " + target;
	}
	
	public static String buildChartSql(String sql, String latitudes) {
		String[] latitude = latitudes.split("[:]");
		StringBuilder builder = new StringBuilder();
		builder.append("select ").append(latitude[0]).append(", sum(");
		builder.append(latitude[1]).append(")").append(latitude[1]);
		builder.append(" from (").append(sql).append(") group by ").append(latitude[0]);
		return builder.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static List<?> rebuildResult(List result) {
		if (result != null) {
			Map cursor;
			for (int i = 0; i < result.size(); i++) {
				cursor = ((Map) result.get(i));
				cursor.put("id", i + 1);
				result.remove(result.get(i));
				result.add(i, cursor);
			}
		}
		return result;
	}
	
	public static Pagination buildPagination(HttpServletRequest request) throws Exception {
		Pagination pagination = (Pagination) ReflectUtil.setBeanFromRequest(request, Pagination.class);
		if (pagination != null && pagination.getPageNum() == null) {
			pagination.setPageNum(SystemConstants.Pagination.FIRST_PAGE);
			pagination.setRows(SystemConstants.Pagination.DEFAULT_ROWS);
		}
		return pagination;
	}
	
	public static void buildPagination(Pagination pagination, Integer count) {
		pagination.setTotal(count % pagination.getRows() == 0 ? count / pagination.getRows() : count / pagination.getRows() + 1);
		pagination.setRecords(count);
	}
	
	public static String[] buildHqlCondition(Map<String, Object> params) {
		String[] conditions = new String[params.size()];
		
		return conditions;
	}
	
	public static Object[] rebulidSqlByParams(String sql, Object[] params) {
		if (params != null && params.length > 0) {
			StringBuilder tempSql = new StringBuilder();
			String[] sqlArr = sql.split("[{}]");
			if (sqlArr.length > 1) {
				tempSql.append(sqlArr[0]);
				
				int count = 0;
				List<Object> rxParams = new ArrayList<Object>();
				for (int i = 1; i < sqlArr.length; i++) {
					if (sqlArr[i].indexOf("?") != -1) {
						if (count < params.length) {
							if (params[count] != null && !StringUtils.isEmpty(params[count].toString())) {
								tempSql.append(sqlArr[i]);
								rxParams.add(params[count]);
							}
							count++;
						}
					} else {
						tempSql.append(sqlArr[i]);
					}
				}
				
				params = rxParams.toArray();
				sql = tempSql.toString();
			}
		}
		
		return new Object[] { sql, params };
	}
	
	public static Object[] rebulidSqlByParams(String sql, Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			StringBuilder tempSql = new StringBuilder();
			String[] sqlArr = sql.split("[{}]");
			if (sqlArr.length > 1) {
				tempSql.append(sqlArr[0]);
				
				String bindParam;
				for (int i = 1; i < sqlArr.length; i++) {
					if (sqlArr[i].indexOf(":") != -1) {
						bindParam = sqlArr[i].split("[:]")[1].split(" ")[0];
						if (!StringUtils.isEmpty(params.get(bindParam).toString())) {
							tempSql.append(sqlArr[i]);
						} else {
							params.remove(bindParam);
						}
					} else {
						tempSql.append(sqlArr[i]);
					}
				}
				
				sql = tempSql.toString();
			}
		}
		
		return new Object[] { sql, params };
	}
	
	public static String setDefaultInfo(Map<String, Object> commonParams, String sql) {
		if (commonParams != null) {
			String userId = ((LoginUser) commonParams.get("loginUser")).getStaff().getStaffId();
			sql = sql.replace("$STAFFID", userId);
			
//			String departId = ((LoginUser) commonParams.get("loginUser")).getStaff().getDepartId();
//			if (!StringUtils.isEmpty(departId)) {
//				sql = sql.replace("$DEPARTID", departId);
//			}	
			
			String branchId = ((LoginUser) commonParams.get("loginUser")).getStaff().getBranchId();
			sql = sql.replace("$BRANCHID", branchId);
		}
		
		return sql;
	}
	
	public static String getSqlStrategy(Map<String, Object> commonParams, String strategy, String sql) throws Exception {
		String sqlName = null;
		
//		User user = ((LoginUser) commonParams.get("loginUser")).getUser();
		if (SystemConstants.SqlStrategy.RIGHT_LEVEL.equals(strategy)) {
			
			
		}
		
		
		return sqlName;
	}
	
}
