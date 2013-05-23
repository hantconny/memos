package com.common;

import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

public interface Dao extends PageDao {

	public abstract List select(String sql, Class clazz);

	public abstract List select(String sql, Class clazz, String... pars);

	public abstract Result select(String sql);

	public abstract Result select(String sql, String... pars);

	public abstract boolean update(String sql);

	public abstract boolean update(String sql, String... pars);

}