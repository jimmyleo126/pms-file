package com.ideassoft.core.jdbc.datasource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{  

	@Override  
	protected Object determineCurrentLookupKey() {  
		return DataSourceContextHolder.getCustomerType();  
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
