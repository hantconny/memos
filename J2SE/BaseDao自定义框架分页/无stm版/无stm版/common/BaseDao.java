package com.common;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import javax.sql.DataSource;

public class BaseDao implements Dao {
	private Connection con;
	private PreparedStatement pstm;
	private ResultSet rs;
	
	private static DataSource ds;
	
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	private static Tools tools = Tools.newInstance("db");

	/*
	 * 静态块初始化
	 * 
	 * */
	static {
		try {
			init();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 使用JNDI或JDBC
	 * 
	 * */
	private static void init() throws NamingException, ClassNotFoundException {
		if ("jndi".equalsIgnoreCase(tools.getValue("type"))) {
			/* 使用JNDI */
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/" + tools.getValue("jndi-name"));
		} else {
			/* 使用JDBC */
			driver = tools.getValue("driver");
			url = tools.getValue("url");
			username = tools.getValue("username");
			password = tools.getValue("password");
			Class.forName(driver);
		}
	}

	/*
	 * open
	 * 
	 * */
	private void open() throws SQLException {
		if ("jndi".equalsIgnoreCase(tools.getValue("type"))) {
			con = ds.getConnection();
		} else {
			con = DriverManager.getConnection(url, username, password);
		}
	}

	/*
	 * close
	 * 
	 * */
	private void close() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (null != pstm) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (null != con) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 使用PreparedStatement进行查询
	 * 
	 * */
	public List select(String sql, Class clazz, String... pars) {
		List list = new ArrayList();
		try {
			open();
			pstm = con.prepareStatement(sql);
			for (int i = 0; i < pars.length; i++) {
				/* parameterIndex - 第一个参数是 1，第二个参数是 2 */
				pstm.setString(i + 1, pars[i]);
			}
			rs = pstm.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			String[] values = new String[count];
			while (rs.next()) {
				for (int i = 0; i < count; i++) {
					/* columnIndex - 第一个列是 1，第二个列是 2 */
					values[i] = rs.getString(i + 1);
				}
				Constructor[] cs = clazz.getConstructors();
				for (Constructor c : cs) {
					int len = c.getParameterTypes().length;
					if (c.getParameterTypes().length == count) {
						Object entity = c.newInstance(values);
						list.add(entity);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	/*
	 * 使用PreparedStatement进行查询
	 * 
	 * */
	public Result select(String sql, String... pars) {
		try {
			open();
			pstm = con.prepareStatement(sql);
			for (int i = 0; i < pars.length; i++) {
				pstm.setString(i + 1, pars[i]);
			}
			rs = pstm.executeQuery();
			return ResultSupport.toResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/*
	 * 使用PreparedStatement进行增/删/改
	 * 
	 * */
	public boolean update(String sql, String... pars) {
		try {
			open();
			pstm = con.prepareStatement(sql);
			for (int i = 0; i < pars.length; i++) {
				pstm.setString(i + 1, pars[i]);
			}
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	/*
	 * PageDao实现
	 * 
	 * */
	public int getCount(String sql) {
		try {
			open();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}

}
