package com.common;

import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

public interface Dao extends PageDao {
	public List select(String sql, Class clazz, String...pars);
	
	public boolean update(String sql, String...pars);
	
	public Result select(String sql, String...pars);
}
