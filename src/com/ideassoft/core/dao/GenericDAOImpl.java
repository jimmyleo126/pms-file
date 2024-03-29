package com.ideassoft.core.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.ideassoft.bean.SqlInfo;
import com.ideassoft.core.exception.DAOException;
import com.ideassoft.core.jdbc.SqlBuilder;
import com.ideassoft.core.jdbc.datasource.DataSourceContextHolder;
import com.ideassoft.core.page.Pagination;

@Service
@SuppressWarnings("unchecked")
public class GenericDAOImpl extends BaseDAO implements IGenericDAO {

	private static Logger logger = Logger.getLogger(GenericDAOImpl.class);
	    
    public String getSequence() throws DAOException {
        return getSequence("SEQ_DEFAULT", null);
    }
    
    public String getSequence(String seqName) throws DAOException {
	    return getSequence(seqName, null);
    }

    public String getSequence(String seqName, String prefix) throws DAOException {
    	if (prefix == null) {
    		prefix = "";
    	}

    	String sqlNV, SqlCV;
		String dbType = DataSourceContextHolder.getCustomerType();
		List<Object> list;
		
		if (dbType == null || DataSourceContextHolder.DS_ORACLE.equals(dbType)) {
			sqlNV = "select " + seqName + ".nextval val from dual";
			
			list = findBySQL(sqlNV);
	        String val = ((Map) list.get(0)).get("val").toString();
	    	
	        String fm = "fm";
	        for (int i = 0; i < val.length(); i++) {
	        	fm += "0";
			}
	        
			SqlCV = "select '" + prefix + "'|| to_char(" + seqName + ".currval,'" + fm + "') val from dual";
		} else if (DataSourceContextHolder.DS_MYSQL.equals(dbType)) {
			SqlCV = "select concat('" + prefix + "', nextval('" + seqName + "')) as val";
		} else {
			return UUID.randomUUID().toString();
		}
    	
    	list = findBySQL(SqlCV);
        return ((Map) list.get(0)).get("val").toString();
    }
    
    public Object findById(Class persistentClass, Serializable id) throws DAOException {
        logger.debug("根据ID [" + id + "] 查询对象 " + persistentClass.getName());
        return this.getHibernateTemplate().get(persistentClass, id);
    }
    
    public List findAll(Class persistentClass) throws DAOException {
        logger.debug("查询所有对象 " + persistentClass.getName());
        try {
            String queryString = "from " + persistentClass.getName();
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List findByHQL(String hql) throws DAOException {
    	return findByHQL(hql, new Object[0]);
    }
    
    public List findByHQL(String hql, Object[] ids) throws DAOException {
        logger.debug("根据ID [" + ToStringBuilder.reflectionToString(ids) + "] 查询HQL: " + hql);
        try {
            return getHibernateTemplate().find(hql, ids);
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List findByHQL(String hql, Map<String, Object> param) throws DAOException {
        logger.debug("查询HQL: " + hql);
    	try {
    		String[] paramName = {};
    		Object[] value = {};
    		
    		if (param != null) {
        		paramName = new String[param.size()];
        		value = new Object[param.size()];
        		
        		int pos = 0;
        		Entry<String, Object> entry;
        		for (Iterator<Entry<String, Object>> it = param.entrySet().iterator(); it.hasNext(); pos++) {
    				entry = it.next();
    				paramName[pos] = entry.getKey();
    				value[pos] = entry.getValue();
    			}
			}
    		
            return getHibernateTemplate().findByNamedParam(hql, paramName, value);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByHQLWithPagination(final String hql, final Object[] ids, 
    		final Integer pageNum, final Integer rowNum) throws DAOException {
        logger.debug("查询HQL: " + hql);
        try {
        	List results = getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(hql);
					if (ids != null) {
						for (int i = 0; i < ids.length; i++) {
							query.setParameter(i, ids[i]);
						}
					}
					query.setFirstResult((pageNum - 1) * rowNum);
					query.setMaxResults(rowNum);
					
					List list = query.list();
					
					return list;
				}
			});
                  
            return results;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List findByHQLWithPagination(final String hql, final Map<String, Object> param, 
    		final Integer pageNum, final Integer rowNum) throws DAOException {
        logger.debug("查询HQL: " + hql);
        try {
        	List results = getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(hql);
					if (param != null && !param.isEmpty()) {
			    		int pos = 0;
		        		Entry<String, Object> entry;
		        		for (Iterator<Entry<String, Object>> it = param.entrySet().iterator(); it.hasNext(); pos++) {
		    				entry = it.next();
		    				query.setParameter(entry.getKey(), entry.getValue());
		    			}
					}
					query.setFirstResult((pageNum - 1) * rowNum);
					query.setMaxResults(rowNum);
					
					List list = query.list();
					
					return list;
				}
			});
                  
            return results;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public Integer findRecordsCount(String hql, Object[] ids) throws DAOException {
        try {
        	List results = getHibernateTemplate().find(hql, ids);
            return results != null ? results.size() : 0;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public Integer findRecordsCount(String hql, Map<String, Object> param) throws DAOException {
        try {
        	List results = findByHQL(hql, param);
            return results != null ? results.size() : 0;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List findByHQLWithMax(String hql, Object[] ids, int max) throws DAOException {
        try {
            HibernateTemplate hibernateTemplate = getHibernateTemplate();
            int oldMax = hibernateTemplate.getMaxResults();
            hibernateTemplate.setMaxResults(max);
            List list = hibernateTemplate.find(hql, ids);
            hibernateTemplate.setMaxResults(oldMax);
            return list;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public Object findOneByProperties(Class persistentClass, String propertyName,
    		Object value, Object... args) throws DAOException {
        List list = findByProperties(persistentClass, propertyName, value, args);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            return null;
        }

        String params = propertyName + "=" + value + ",";
        for (int i = 0; args != null && i < args.length; i++) {
            if (i == args.length - 2) {
                params += args[i] + "=" + args[i++];
            } else {
                params += args[i] + "=" + args[i++] + ",";
            }
        }

        logger.warn(persistentClass.getName() + " findOneByProperties 找到多个匹配的记录! " + params);

        return list.get(0);
    }
    
    public List findByProperties(Class persistentClass, String propertyName, Object value, Object... args) throws DAOException {
        return findByPropertiesWithSort(persistentClass, "id", null, propertyName, value, args);
    }
    
    public List findByPropertiesWithSort(Class persistentClass, String propertyNameToSort, String sortBy,
           String propertyName, Object value, Object... args) throws DAOException {

        logger.debug("查询 " + persistentClass.getName() + ", 排序条件: " + propertyNameToSort + ","
                + (!"desc".equals(sortBy) ? "asc" : "desc") + ", 条件: " + propertyName + "=" + value + ", 其他条件: "
                + ToStringBuilder.reflectionToString(args));

        String objectName = persistentClass.getName();
        try {
            if (!"desc".equals(sortBy)) {
                sortBy = "asc";
            }

            Object[] values = new Object[args.length / 2 + 1];
            int i = 0;
            StringBuilder sb = new StringBuilder("from ");
            sb.append(objectName);
            sb.append(" as o where ");
            sb.append(propertyName);
            values[i++] = value;
            sb.append("=?");
            for (int j = 0, k = i; j < args.length; j += 2, k++) {
                sb.append(" and o.");
                sb.append(args[j]);
                sb.append("=?");
                values[k] = args[j + 1];
            }
            if (propertyNameToSort != null && propertyNameToSort.trim().length() > 0) {
                sb.append(" order by o.");
                sb.append(propertyNameToSort);
                sb.append(' ');
                sb.append(sortBy);
            }

            if (logger.isDebugEnabled()) {
                String valueStr = "[";
                for (int j = 0; j < values.length; j++) {
                    valueStr += values[j];

                    if (j < values.length - 1) {
                        valueStr += ", ";
                    }
                }

                valueStr += "]";

                logger.debug("HQL: " + sb.toString() + "\r\n参数: " + valueStr);
            }

            return getHibernateTemplate().find(sb.toString(), values);
        } catch (RuntimeException re) {
            throw re;
        }

    }

    public Serializable save(Object newInstance) throws DAOException {
        SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
        ClassMetadata md = sf.getClassMetadata(newInstance.getClass());
        String idPropertyName = md.getIdentifierPropertyName();

        try {
            Field idField = newInstance.getClass().getDeclaredField(idPropertyName);
            idField.setAccessible(true);
            if (idField.get(newInstance) == null || idField.get(newInstance).toString().trim().length() == 0) {
                idField.set(newInstance, getSequence());
            }
        } catch (NoSuchFieldException e) {
            throw new DAOException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new DAOException(e.getMessage());
        }

        logger.debug("保存对象: \r\n" + ToStringBuilder.reflectionToString(newInstance, ToStringStyle.MULTI_LINE_STYLE));

        Serializable s = this.getHibernateTemplate().save(newInstance);

        return s;
    }

    public void update(Object transientObject) throws DAOException {
        this.getHibernateTemplate().update(transientObject);
    }

    public void merge(Object transientObject) throws DAOException {
        this.getHibernateTemplate().merge(transientObject);
    }

    public void mergeAndUpdate(Object transientObject) throws DAOException {
        SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
        ClassMetadata md = sf.getClassMetadata(transientObject.getClass());
        String idPropertyName = md.getIdentifierPropertyName();

        try {
            Field idField = transientObject.getClass().getDeclaredField(idPropertyName);
            idField.setAccessible(true);
            Object oldObject = findById(transientObject.getClass(), (Serializable) idField.get(transientObject));

            Method methods[] = transientObject.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                methods[i].setAccessible(true);
                if (methods[i].getName().startsWith("get") && methods[i].invoke(transientObject) != null) {
                    String setMethodName = "set" + methods[i].getName().substring("get".length());
                    Method setMethod = transientObject.getClass().getDeclaredMethod(setMethodName,
                            methods[i].getReturnType());

                    setMethod.setAccessible(true);
                    setMethod.invoke(oldObject, methods[i].invoke(transientObject));
                }
            }

            transientObject = oldObject;

        } catch (InvocationTargetException e) {
            throw new DAOException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new DAOException(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new DAOException(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            throw new DAOException(e.getMessage(), e);
        }

        logger.debug("更新对象: \r\n" + ToStringBuilder.reflectionToString(transientObject, ToStringStyle.MULTI_LINE_STYLE));
        this.getHibernateTemplate().update(transientObject);
    }

    public void delete(Object persistentObject) throws DAOException {
        if (persistentObject != null) {
            logger.debug("删除对象: \r\n" + ToStringBuilder.reflectionToString(persistentObject, ToStringStyle.MULTI_LINE_STYLE));
            this.getHibernateTemplate().delete(persistentObject);
        }
    }

    public List executeQueryHQL(String hql) throws DAOException {
    	return executeQueryHQL(hql, null);
    }
    
    public List executeQueryHQL(String hql, Map<String, Object> params) throws DAOException {
        logger.debug("HQL查询: " + hql);
        try {
            Query query = getSession().createQuery(hql);
            if (params != null) {
                Entry<String, Object> entry;
    			for (Iterator<Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
    				entry = it.next();
    				query.setParameter(entry.getKey(), entry.getValue());
    			}
			}
            return query.list();
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void executeUpdateHQL(String hql, String[] names, Object[] values) throws DAOException {
        logger.debug("HQL更新: " + hql + "\r\n参数: " + ToStringBuilder.reflectionToString(names));
        try {
            Query query = getSession().createQuery(hql);
            for (int i = 0; values != null && i < values.length; i++) {
                query.setParameter(names[i], values[i]);
            }
            query.executeUpdate();
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void executeUpdateSQL(String sql) throws DAOException {
        logger.debug("SQL更新: " + sql);
        try {
            getJdbcTemplate().execute(sql);
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public Integer findSQLRecordsCount(String sql) throws DAOException {
        try {
			return getJdbcTemplate().queryForInt(SqlBuilder.buildCountSql(sql));
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public Integer findSQLRecordsCount(String sql, Object[] params) throws DAOException {
    	sql = SqlBuilder.buildCountSql(sql);
        try {
        	if (params != null && params.length > 0) {
				return getJdbcTemplate().queryForInt(sql, params);
			} else {
				return getJdbcTemplate().queryForInt(sql);
			}
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public Integer findSQLRecordsCount(String sql, Map<String, Object> params) throws DAOException {
    	sql = SqlBuilder.buildCountSql(sql);
        try {
        	return getNamedJdbcTemplate().queryForInt(sql, params);
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public Object[] findQuerySQL(String sql, Object[] params, boolean... inDB) throws DAOException {
    	return SqlBuilder.rebulidSqlByParams(findQuerySQL(sql, inDB), params);
    }
    
    public Object[] findQuerySQL(String sql, Map<String, Object> params, boolean... inDB) throws DAOException {
    	return SqlBuilder.rebulidSqlByParams(findQuerySQL(sql, inDB), params);
    }
    
    public String findQuerySQL(String sql, boolean... inDB) throws DAOException {
    	if (inDB != null && inDB.length > 0 && inDB[0]) {
    		String dbType = DataSourceContextHolder.getCustomerType();
    		Object si = findOneByProperties(SqlInfo.class, "sqlName", sql, new Object[] { "dbType", dbType });
        	return ((SqlInfo) si).getSqlInfo();
		}
    	return sql;
    }
    
    public List findBySQLWithPagination(String sql, Pagination pagination, boolean... inDB) throws DAOException {
    	sql = findQuerySQL(sql, inDB);
    	if (pagination != null) {
        	SqlBuilder.buildPagination(pagination, findSQLRecordsCount(sql));
			sql = SqlBuilder.buildSqlByPagination(sql, pagination);
    	}
		return findBySQL(sql);
    }
    
    public List findBySQLWithPagination(String sql, Object[] params, Pagination pagination, boolean... inDB) throws DAOException {
    	Object[] sqlInfo = findQuerySQL(sql, params, inDB);
    	sql = sqlInfo[0].toString();
    	params = (Object[]) sqlInfo[1];
    	
        logger.debug("查询SQL: " + sql);
    	try {
        	if (pagination != null) {
            	SqlBuilder.buildPagination(pagination, findSQLRecordsCount(sql, params));
				sql = SqlBuilder.buildSqlByPagination(sql, pagination);
			}
	    	if (params != null && params.length > 0) {
	    		return getJdbcTemplate().queryForList(sql, params);
			} else {
				return getJdbcTemplate().queryForList(sql);
			}
    	} catch (RuntimeException re) {
            throw re;
        }
    }
	
    public List findBySQLWithPagination(String sql, Map<String, Object> params, Pagination pagination, boolean... inDB) throws DAOException {
    	Object[] sqlInfo = findQuerySQL(sql, params, inDB);
    	sql = sqlInfo[0].toString();
    	params = (Map<String, Object>) sqlInfo[1];
    	
        logger.debug("查询SQL: " + sql);
    	try {
        	if (pagination != null) {
            	SqlBuilder.buildPagination(pagination, findSQLRecordsCount(sql, params));
				sql = SqlBuilder.buildSqlByPagination(sql, pagination);
			}
        	return getNamedJdbcTemplate().queryForList(sql, params);
    	} catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List findBySQL(String sql, Class<?> cls, boolean... inDB) throws DAOException {
    	sql = findQuerySQL(sql, inDB);
        logger.debug("查询SQL: " + sql);
    	try {
			return getJdbcTemplate().queryForList(sql, cls);
		} catch (RuntimeException re) {
            throw re;
		}
    }
    
	public List findBySQL(String sql, boolean... inDB) throws DAOException {
		sql = findQuerySQL(sql, inDB);
    	return findBySQL(sql, new Object[0]);
	}
	
	public List findBySQL(String sql, Object[] params, boolean... inDB) throws DAOException {
		Object[] sqlInfo = findQuerySQL(sql, params, inDB);
    	sql = sqlInfo[0].toString();
    	params = (Object[]) sqlInfo[1];
    	
        logger.debug("查询SQL: " + sql);
    	try { 
	    	if (params != null && params.length > 0) {
	    		return getJdbcTemplate().queryForList(sql, params);
			} else {
				return getJdbcTemplate().queryForList(sql);
			}
    	} catch (RuntimeException re) {
            throw re;
        }
    }
	
    public List findBySQL(String sql, Map<String, Object> params, boolean... inDB) throws DAOException {
    	Object[] sqlInfo = findQuerySQL(sql, params, inDB);
    	sql = sqlInfo[0].toString();
    	params = (Map<String, Object>) sqlInfo[1];
    	
        logger.debug("查询SQL: " + sql);
    	try {
        	return getNamedJdbcTemplate().queryForList(sql, params);
    	} catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List<?> callProc(String procName, String params) throws DAOException {
    	List<?> result = null;
    	try {
    		result = (List<Map>) getJdbcTemplate().execute(
    				new ProcCallableStatementCreator(procName, params),  
        			new ProcCallableStatementCallback()); 
		} catch (RuntimeException e) {
			throw e;
		}
		return result;
    }

    //可分页的存储过程
	public List<?> callProcByPagination(String procName, String params, Pagination pagination) throws DAOException {
		List<?> resultListMap = null;
		if (pagination != null) {
			String dbType = DataSourceContextHolder.getCustomerType();
			if (dbType == null || DataSourceContextHolder.DS_ORACLE.equals(dbType)) {
				resultListMap = callProc(procName, params);
				SqlBuilder.buildPagination(pagination, Integer.valueOf(((Map) (resultListMap.get(resultListMap.size() - 1))).get("recordCount").toString()));
			} else if (DataSourceContextHolder.DS_MYSQL.equals(dbType)) {

			} else if (DataSourceContextHolder.DS_SQLSERVER.equals(dbType)) {

			}
		}
		return resultListMap;
	}
	
	private class ProcCallableStatementCreator implements CallableStatementCreator {
		private String storedProc;
		private String params;
		
		public ProcCallableStatementCreator(String storedProc, String params) {
			this.params = params;
			this.storedProc = storedProc;
		}
		
		public CallableStatement createCallableStatement(Connection conn) {
			StringBuffer storedProcName = new StringBuffer("call ");
			storedProcName.append(storedProc + "(");
			storedProcName.append("?, ?, ");
			String[] paramArrays = params.split(",");
			for (int i = 0; i < paramArrays.length; i++) {
				storedProcName.append("?").append(i < (paramArrays.length - 1) ? ", " : "");
			}
			storedProcName.append(")");

			CallableStatement cs = null;
			try {
				cs = conn.prepareCall(storedProcName.toString());
				cs.registerOutParameter(1, OracleTypes.CURSOR);
				cs.registerOutParameter(2, OracleTypes.INTEGER);
				for (int j = 0; j < paramArrays.length; j++) {
					cs.setString((j + 3), paramArrays[j]);
				}
			} catch (SQLException e) {
				throw new RuntimeException("create CallableStatement method Error : SQLException " + e.getMessage());
			}
			return cs;
		}

	}

	private class ProcCallableStatementCallback implements CallableStatementCallback {
		public Object doInCallableStatement(CallableStatement cs) {
			List<Map> resultsMap = new ArrayList<Map>();
			try {
				cs.execute();
				ResultSet rs = (ResultSet) cs.getObject(1);
				String columnName;
				while (rs.next()) {
					Map<String, Object> rowMap = new HashMap<String, Object>();
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						columnName = rs.getMetaData().getColumnName(i);
						rowMap.put(columnName, rs.getObject(columnName));
					}
					resultsMap.add(rowMap);
				}
				rs.close();
				//
				Integer recordCount = (Integer) cs.getObject(2);
				Map<String, Object> recordCountMap = new HashMap<String, Object>();
				recordCountMap.put("recordCount", recordCount);
				resultsMap.add(recordCountMap);
			} catch (SQLException e) {
				throw new RuntimeException("doInCallableStatement method error : SQLException " + e.getMessage());
			}
			return resultsMap;
		}
	}

}