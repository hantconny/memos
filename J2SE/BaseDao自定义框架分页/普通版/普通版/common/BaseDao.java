package com.common;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
	private Statement stm;
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
	 */
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
	 */
	private static void init() throws NamingException, ClassNotFoundException {
		if ("jndi".equalsIgnoreCase(tools.getValue("type"))) {
			/* 使用JNDI */
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/"
					+ tools.getValue("jndi-name"));
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
	 */
	private void open() throws SQLException {
		if ("jndi".equalsIgnoreCase(tools.getValue("type"))) {
			con = ds.getConnection();
		} else {
			con = DriverManager.getConnection(url, username, password);
		}
		stm = con.createStatement();
	}

	/*
	 * close
	 * 
	 */
	private void close() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (null != stm) {
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
	 * 使用Statement进行查询
	 * 
	 */
	/* (non-Javadoc)
	 * @see com.common.Dao#select(java.lang.String, java.lang.Class)
	 */
	public List select(String sql, Class clazz) {
		List list = new ArrayList();
		try {
			open();
			rs = stm.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			String[] values = new String[count];
			while (rs.next()) {
				for (int i = 0; i < count; i++) {
					values[i] = rs.getString(i + 1);
				}
				Constructor[] cs = clazz.getConstructors();
				for (Constructor c : cs) {
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
	 */
	/* (non-Javadoc)
	 * @see com.common.Dao#select(java.lang.String, java.lang.Class, java.lang.String)
	 */
	public List select(String sql, Class clazz, String... pars) {
		List list = new ArrayList();
		try {
			open();
			pstm = con.prepareStatement(sql);
			for (int i = 0; i < pars.length; i++) {
				pstm.setString(i + 1, pars[i]);
			}
			rs = pstm.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			String[] values = new String[count];
			while (rs.next()) {
				for (int i = 0; i < count; i++) {
					values[i] = rs.getString(i + 1);
				}
				Constructor[] cs = clazz.getConstructors();
				for (Constructor c : cs) {
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
	 * 使用Statement进行查询
	 * 
	 */
	/* (non-Javadoc)
	 * @see com.common.Dao#select(java.lang.String)
	 */
	public Result select(String sql) {
		try {
			open();
			rs = stm.executeQuery(sql);
			return ResultSupport.toResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/*
	 * 使用PreparedStatement进行查询
	 * 
	 */
	/* (non-Javadoc)
	 * @see com.common.Dao#select(java.lang.String, java.lang.String)
	 */
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
	 * 使用Statement进行增/删/改
	 * 
	 */
	/* (non-Javadoc)
	 * @see com.common.Dao#update(java.lang.String)
	 */
	public boolean update(String sql) {
		try {
			open();
			return stm.executeUpdate(sql) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	/*
	 * 使用PreparedStatement进行增/删/改
	 * 
	 */
	/* (non-Javadoc)
	 * @see com.common.Dao#update(java.lang.String, java.lang.String)
	 */
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
	 */
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
