package com.common;

import java.util.List;

import com.common.PageDao;

/*
 * SQL分页类，其他DAO类必须实现PageDao接口，实现select方法，BaseDao中增加一个getCount(String SQL)方法的实现
 * 
 */
public class SQLPage {
	private PageDao dao;
	private String tableName;		/* 表名 */
	private String keyName;			/* 主键名 */
	private String orderKey;		/* 排序键 */
	private List<String> colNames;	/* 条件列名,无条件时传null */
	private List<String> colValues;	/* 条件列值,无条件时传null */
	private int pageSize = 5;
	private int maxPage = 1;
	private int curPage = 1;
	private int rowBegin = 1; /* 起始行 */

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

			/* 通用拼接语句 */
			String countSQL = "select count(0) from " + tableName
					+ " where 1=1 ";

			/* 根据参数列和值进行SQL拼接 */
			if (null != colNames && null != colValues) {
				if (colNames.size() == colValues.size()) {
					for (int i = 0; i < colNames.size(); i++) {
						countSQL = countSQL + " and " + colNames.get(i) + "='"
								+ colValues.get(i) + "'";
					}
				}
			}

			int count = dao.getCount(countSQL);

			/* 同JavaSript分页 */
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

		/* 父查询通用拼接语句 */
		String SQL = "select top " + pageSize + " * from " + tableName
				+ " where 1=1 ";

		/* 子查询通用拼接语句 */
		String subSQL = "select top " + rowBegin + " " + keyName + " from "
				+ tableName + " where 1=1 ";

		/* 根据参数列和值进行SQL拼接 */
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

		/* 完成SQL拼接 */
		SQL = SQL + " and " + keyName + " not in (" + subSQL + " order by " + orderKey + " " + orderMethod + ") order by " + orderKey + " " + orderMethod;

		return dao.select(SQL, clazz);
	}

	/* 返回翻页工具条 */
	/* 以下同JavaScript分页 */
	public String getBar(String linkTo, int barType, String... pars) {
		StringBuilder parStr = new StringBuilder("");
		if (null != pars) {
			for (int i = 0; i < pars.length; i++) {
				parStr.append("&" + pars[i]);
			}
		}
		/* 默认bar */
		String bar = "<a href=\"" + linkTo + "?curPage=" + (curPage - 1)
				+ parStr.toString() + "\">上一页</a>&nbsp|&nbsp<a href=\""
				+ linkTo + "?curPage=" + (curPage + 1) + parStr.toString()
				+ "\">下一页</a>";
		switch (barType) {
		case 1:
			/* 超链接翻页条 */
			bar = "<a href=\"" + linkTo + "?curPage=0" + parStr.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage - 1) + parStr.toString()
					+ "\">上一页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage + 1) + parStr.toString()
					+ "\">下一页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + parStr.toString() + "\">末页</a>";
			break;
		case 2:
			/* 下拉框翻页条 */
			bar = "<a href=\"" + linkTo + "?curPage=0" + parStr.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + parStr.toString()
					+ "\">末页</a>&nbsp|&nbsp跳到<select onchange=\"location='"
					+ linkTo + "?curPage='+this.value+'" + parStr.toString()
					+ "'\">";
			for (int i = 1; i <= maxPage; i++) {
				bar = bar + "<option value=" + i + " "
						+ (i == curPage ? "selected" : "") + ">第" + i
						+ "页</option>";
			}
			bar = bar + "</select>";
			break;
		case 3:
			/* 文本框翻页条 */
			bar = "<a href=\""
					+ linkTo
					+ "?curPage=0"
					+ parStr.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\""
					+ linkTo
					+ "?curPage="
					+ maxPage
					+ parStr.toString()
					+ "\">末页</a>&nbsp|&nbsp<input style='width:30;' id='cp' value="
					+ curPage
					+ "><input style='width:30' type='button' value='GO' onclick=\"location='"
					+ linkTo + "?curPage='+document.all.cp.value"
					+ parStr.toString() + "\">";
			break;
		}
		bar = bar + "(当前 第" + curPage + "/" + maxPage + "页)";
		return bar;
	}

}
