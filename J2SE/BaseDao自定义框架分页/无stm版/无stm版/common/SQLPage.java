package com.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * SQL��ҳ�࣬����DAO�����ʵ��PageDao�ӿڣ�ʵ��select������BaseDao������һ��getCount(String SQL)������ʵ��
 * 
 */
public class SQLPage {
	private PageDao dao;
	private String tableName; /* ���� */
	private String colStrs; /* Ҫ��ѯ�������ַ���, ��һ����*, ����ѯʱ, ������tblName.*, tblName.colName����ָ���ı����� */
	private String keyName; /* ������, ��ҳʱ not in ���� */
	private String orderKey; /* �����, ��ҳʱ order by ʹ�� */

	/* SQLƴ��ʹ�õĵ�Map�������� */
	private Map<String, Object> params = new HashMap<String, Object>();

	private int pageSize = 5; /* ÿҳ��ʾ�������� */
	private int maxPage = 1; /* ���ҳ�� */
	private int curPage = 1; /* ��ǰҳ */
	private int rowBegin = 1; /* ��ʼ�� */

	/*
	 * ���캯��
	 */
	public SQLPage(PageDao dao, String tableName, String colStrs, String keyName, String orderKey, Map<String, Object> parmas, int pageSize, String curPageStr) {
		if (null != dao && null != params) {
			this.dao = dao;
			this.tableName = tableName;
			this.colStrs = colStrs;
			this.keyName = keyName;
			this.orderKey = orderKey;
			this.params = parmas;
			this.pageSize = pageSize;

			/* �ܼ�¼��ͨ��ƴ����� */
			/* ���ַ���ʱ����Ҫ�ո� */
			String countSQL = "select count(0) from " + tableName + " where 1=1 ";

			/* ���ݲ����С�ֵӳ�����SQLƴ�� */
			/* ���캯������Ѿ��ж�params��Ϊnull */
			/* params�����ڼ�-ֵӳ��(isEmpty()Ϊtrue)ʱ�������for */
			/* ����kv */
			for (Entry<String, Object> kv : params.entrySet()) {
				/* ȷ��key��value��Ϊnull&&��Ϊ���ַ��� */
				if (null != kv.getKey() && 0 != kv.getKey().trim().length() && null != kv.getValue() && 0 != kv.getValue().toString().trim().length()) {
					countSQL = countSQL + " and " + kv.getKey() + "=" + kv.getValue();
				}
			}

			int count = dao.getCount(countSQL);

			/* ����ڲ鵥����¼ʱû�в鵽, countΪ0 */
			/* ��֤rowBegin��Ϊ�� */
			count = count == 0 ? count + 1 : count;
			
			maxPage = count / pageSize;
			maxPage = count % pageSize != 0 ? maxPage + 1 : maxPage;

			curPageStr = (curPageStr == null || curPageStr.trim().length() == 0) ? "1" : curPageStr;
			curPage = Integer.parseInt(curPageStr);
			curPage = curPage < 1 ? 1 : curPage;
			curPage = curPage > maxPage ? maxPage : curPage;

			rowBegin = (curPage - 1) * pageSize;
		}
	}

	public List getCurList(Class clazz, String orderMethod) {

		/* ����ѯͨ��ƴ����� */
		/* ���ַ���ʱ����Ҫ�ո� */
		String SQL = "select top " + pageSize + " " + colStrs + " from " + tableName + " where 1=1 ";

		/* �Ӳ�ѯͨ��ƴ����� */
		/* ���ַ���ʱ����Ҫ�ո� */
		String subSQL = "select top " + rowBegin + " " + keyName + " from " + tableName + " where 1=1 ";

		/* ���ݲ����С�ֵӳ�����SQLƴ�� */
		/* ���캯������Ѿ��ж�params��Ϊnull */
		/* params�����ڼ�-ֵӳ��(isEmpty()Ϊtrue)ʱ�������for */
		/* ����kv */
		for (Entry<String, Object> kv : params.entrySet()) {
			/* ȷ��key��value��Ϊnull&&��Ϊ�� */
			if (null != kv.getKey() && 0 != kv.getKey().trim().length() && null != kv.getValue() && 0 != kv.getValue().toString().trim().length()) {
				SQL = SQL + " and " + kv.getKey() + "=" + kv.getValue();
				subSQL = subSQL + " and " + kv.getKey() + "=" + kv.getValue();
			}
		}

		/* ���SQLƴ�� */
		SQL = SQL + " and " + keyName + " not in (" + subSQL + " order by " + orderKey + " " + orderMethod + ") order by " + orderKey + " " + orderMethod;

		return dao.select(SQL, clazz);
	}

	/* ���ط�ҳ������ */
	/* ����ͬJavaScript��ҳ */
	/* linkTo���û�и�����, ��Ҫ����һ��?, ��֤ȡ��curPage���� */
	/* parsΪ�ⲿƴ�Ӻõ���-ֵ�ַ�������, ���� parName=parValue */
	public String getBar(String linkTo, int barType, String... pars) {
		StringBuilder parStr = new StringBuilder("");
		
		for (int i = 0; i < pars.length; i++) {
			parStr.append("&" + pars[i]);
		}
		
		/* Ĭ��bar */
		String bar = "<a href=\"" + linkTo + "&curPage=" + (curPage - 1)
				+ parStr.toString() + "\">��һҳ</a>&nbsp|&nbsp<a href=\""
				+ linkTo + "&curPage=" + (curPage + 1) + parStr.toString()
				+ "\">��һҳ</a>";
		switch (barType) {
		case 1:
			/* �����ӷ�ҳ�� */
			bar = "<a href=\"" + linkTo + "&curPage=0" + parStr.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ (curPage - 1) + parStr.toString()
					+ "\">��һҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ (curPage + 1) + parStr.toString()
					+ "\">��һҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ maxPage + parStr.toString() + "\">ĩҳ</a>";
			break;
		case 2:
			/* ������ҳ�� */
			bar = "<a href=\"" + linkTo + "&curPage=0" + parStr.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ maxPage + parStr.toString()
					+ "\">ĩҳ</a>&nbsp|&nbsp����<select onchange=\"location='"
					+ linkTo + "&curPage='+this.value+'" + parStr.toString()
					+ "'\">";
			for (int i = 1; i <= maxPage; i++) {
				bar = bar + "<option value=" + i + " "
						+ (i == curPage ? "selected" : "") + ">��" + i
						+ "ҳ</option>";
			}
			bar = bar + "</select>";
			break;
		case 3:
			/* �ı���ҳ�� */
			bar = "<a href=\""
					+ linkTo
					+ "&curPage=0"
					+ parStr.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\""
					+ linkTo
					+ "&curPage="
					+ maxPage
					+ parStr.toString()
					+ "\">ĩҳ</a>&nbsp|&nbsp<input style='width:30;' id='cp' value="
					+ curPage
					+ "><input style='width:30' type='button' value='GO' onclick=\"location='"
					+ linkTo + "&curPage='+document.all.cp.value"
					+ parStr.toString() + "\">";
			break;
		}
		bar = bar + "(��ǰ ��" + curPage + "ҳ/�ܼ�" + maxPage + "ҳ)";
		return bar;
	}

}
