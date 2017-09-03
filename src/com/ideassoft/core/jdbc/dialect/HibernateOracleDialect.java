package com.ideassoft.core.jdbc.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class HibernateOracleDialect extends Oracle10gDialect {
	
	public HibernateOracleDialect() {
		super();
	    registerFunction("decode", new StandardSQLFunction("decode", Hibernate.STRING));
	    registerFunction("nvl", new StandardSQLFunction("nvl", Hibernate.STRING));
	    registerFunction("to_char", new StandardSQLFunction("to_char", Hibernate.STRING));
	    registerFunction("to_date", new StandardSQLFunction("to_date", Hibernate.STRING));
	    registerFunction("to_number", new StandardSQLFunction("to_number", Hibernate.STRING));
	}
	
}
