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
	/* ʵ�����̳߳� */
	private ThreadLocal<Session> thread = new ThreadLocal<Session>();

	static {
		init();
	}

	/* 
	 * ����ģʽ 
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
	 * open����
	 * */
	private void open() throws Exception {
		/* �ȴ��̳߳���ȡ */
		Session session = thread.get();
		/* ȡ���������ù����½� */
		if (null == session) {
			session = sf.openSession();
			/* ���½���session�����̳߳� */
			thread.set(session);
		}
	}

	/* 
	 * close����
	 *  */
	private void close() {
		try {
			/* ���̳߳���ȡ */
			Session session = thread.get();
			/* ȡ�����ر� */
			if (null != session) {
				session.close();
				/* ����̳߳� */
				thread.set(null);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ִ����\ɾ\��
	 * */
	public boolean update(Object obj, int type) {
		Session session = null;
		Transaction tran = null;
		try {
			open();
			
			/* ȡ��session */
			session = thread.get();
			
			/* ������ */
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
			
			/* Hibernate������������Ƚ��䱣���ڻ����� */
			/* ֻ��commit�Ժ󣬲Ž������ύ */
			/* �ύ���� */
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
	 * ��ѯ����
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
