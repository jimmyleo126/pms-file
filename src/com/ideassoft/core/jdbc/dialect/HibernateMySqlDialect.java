package com.ideassoft.core.jdbc.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class HibernateMySqlDialect extends Oracle10gDialect {
	
	public HibernateMySqlDialect() {
		super();
	    registerFunction("decode", new StandardSQLFunction("decode", Hibernate.STRING));
	    registerFunction("ifnull", new StandardSQLFunction("ifnull", Hibernate.STRING));
	}
	
}
