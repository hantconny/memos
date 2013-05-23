package com.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.IConst.IConst;

public class BaseDao {
	private static Configuration cf;
	private static SessionFactory sf;
	/* 实例化线程池 */
	private ThreadLocal<Session> thread = new ThreadLocal<Session>();

	static {
		init();
	}

	/* 
	 * 单例模式 
	 * */
	private static synchronized void init() {
		try {
			if (null == cf || null == sf) {
				cf = new Configuration().configure();
				sf = cf.buildSessionFactory();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	/*
	 * open方法
	 * */
	private void open() throws Exception {
		/* 先从线程池中取 */
		Session session = thread.get();
		/* 取不到，利用工厂新建 */
		if (null == session) {
			session = sf.openSession();
			/* 将新建的session放入线程池 */
			thread.set(session);
		}
	}

	/* 
	 * close方法
	 *  */
	private void close() {
		try {
			/* 从线程池中取 */
			Session session = thread.get();
			/* 取到，关闭 */
			if (null != session) {
				session.close();
				/* 清空线程池 */
				thread.set(null);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 执行增\删\改
	 * */
	public boolean update(Object obj, int type) {
		Session session = null;
		Transaction tran = null;
		try {
			open();
			
			/* 取出session */
			session = thread.get();
			
			/* 打开事务 */
			tran = session.beginTransaction();
			switch (type) {
			case IConst.DELETE:
				session.delete(obj);
				break;

			case IConst.INSERT:
				session.save(obj);
				break;

			case IConst.UPDATE:
				session.update(obj);
				break;
			}
			
			/* Hibernate的事务机制是先将其保存在缓存中 */
			/* 只有commit以后，才将事务提交 */
			/* 提交事务 */
			tran.commit();
			return true;
		} catch (Exception e) {
			if (null != tran) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	/* 
	 * 查询方法
	 * */
	public List select(String sql) {
		Session session = null;
		try {
			open();
			session = thread.get();
			return session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}
}
