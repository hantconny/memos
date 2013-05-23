package com.common;

import java.util.List;

import com.common.PageDao;

/*
 * SQL��ҳ�࣬����DAO�����ʵ��PageDao�ӿڣ�ʵ��select������BaseDao������һ��getCount(String SQL)������ʵ��
 * 
 */
public class SQLPage {
	private PageDao dao;
	private String tableName;		/* ���� */
	private String keyName;			/* ������ */
	private String orderKey;		/* ����� */
	private List<String> colNames;	/* ��������,������ʱ��null */
	private List<String> colValues;	/* ������ֵ,������ʱ��null */
	private int pageSize = 5;
	private int maxPage = 1;
	private int curPage = 1;
	private int rowBegin = 1; /* ��ʼ�� */

	public SQLPage(PageDao dao, String tableName, String keyName, String orderKey,
			List<String> colNames, List<String> colValues, int pageSize,
			String curPageStr) {
		if (dao != null) {
			this.dao = dao;
			this.tableName = tableName;
			this.keyName = keyName;
			this.orderKey = orderKey;
			this.colNames = colNames;
			this.colValues = colValues;
			this.pageSize = pageSize;

			/* ͨ��ƴ����� */
			String countSQL = "select count(0) from " + tableName
					+ " where 1=1 ";

			/* ���ݲ����к�ֵ����SQLƴ�� */
			if (null != colNames && null != colValues) {
				if (colNames.size() == colValues.size()) {
					for (int i = 0; i < colNames.size(); i++) {
						countSQL = countSQL + " and " + colNames.get(i) + "='"
								+ colValues.get(i) + "'";
					}
				}
			}

			int count = dao.getCount(countSQL);

			/* ͬJavaSript��ҳ */
			maxPage = count / pageSize;
			maxPage = count % pageSize != 0 ? maxPage + 1 : maxPage;

			curPageStr = (curPageStr == null || curPageStr.length() == 0) ? "1"
					: curPageStr;
			curPage = Integer.parseInt(curPageStr);
			curPage = curPage < 1 ? 1 : curPage;
			curPage = curPage > maxPage ? maxPage : curPage;

			rowBegin = (curPage - 1) * pageSize;
		}
	}

	public List getCurList(Class clazz, String orderMethod) {

		/* ����ѯͨ��ƴ����� */
		String SQL = "select top " + pageSize + " * from " + tableName
				+ " where 1=1 ";

		/* �Ӳ�ѯͨ��ƴ����� */
		String subSQL = "select top " + rowBegin + " " + keyName + " from "
				+ tableName + " where 1=1 ";

		/* ���ݲ����к�ֵ����SQLƴ�� */
		if (null != colNames && null != colValues) {
			if (colNames.size() == colValues.size()) {
				for (int i = 0; i < colNames.size(); i++) {
					SQL = SQL + " and " + colNames.get(i) + "='"
							+ colValues.get(i) + "'";
					subSQL = subSQL + " and " + colNames.get(i) + "='"
							+ colValues.get(i) + "'";
				}
			}
		}

		/* ���SQLƴ�� */
		SQL = SQL + " and " + keyName + " not in (" + subSQL + " order by " + orderKey + " " + orderMethod + ") order by " + orderKey + " " + orderMethod;

		return dao.select(SQL, clazz);
	}

	/* ���ط�ҳ������ */
	/* ����ͬJavaScript��ҳ */
	public String getBar(String linkTo, int barType, String... pars) {
		StringBuilder parStr = new StringBuilder("");
		if (null != pars) {
			for (int i = 0; i < pars.length; i++) {
				parStr.append("&" + pars[i]);
			}
		}
		/* Ĭ��bar */
		String bar = "<a href=\"" + linkTo + "?curPage=" + (curPage - 1)
				+ parStr.toString() + "\">��һҳ</a>&nbsp|&nbsp<a href=\""
				+ linkTo + "?curPage=" + (curPage + 1) + parStr.toString()
				+ "\">��һҳ</a>";
		switch (barType) {
		case 1:
			/* �����ӷ�ҳ�� */
			bar = "<a href=\"" + linkTo + "?curPage=0" + parStr.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage - 1) + parStr.toString()
					+ "\">��һҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage + 1) + parStr.toString()
					+ "\">��һҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + parStr.toString() + "\">ĩҳ</a>";
			break;
		case 2:
			/* ������ҳ�� */
			bar = "<a href=\"" + linkTo + "?curPage=0" + parStr.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + parStr.toString()
					+ "\">ĩҳ</a>&nbsp|&nbsp����<select onchange=\"location='"
					+ linkTo + "?curPage='+this.value+'" + parStr.toString()
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
					+ "?curPage=0"
					+ parStr.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\""
					+ linkTo
					+ "?curPage="
					+ maxPage
					+ parStr.toString()
					+ "\">ĩҳ</a>&nbsp|&nbsp<input style='width:30;' id='cp' value="
					+ curPage
					+ "><input style='width:30' type='button' value='GO' onclick=\"location='"
					+ linkTo + "?curPage='+document.all.cp.value"
					+ parStr.toString() + "\">";
			break;
		}
		bar = bar + "(��ǰ ��" + curPage + "/" + maxPage + "ҳ)";
		return bar;
	}

}
