package com.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * SQL分页类，其他DAO类必须实现PageDao接口，实现select方法，BaseDao中增加一个getCount(String SQL)方法的实现
 * 
 */
public class SQLPage {
	private PageDao dao;
	private String tableName; /* 表名 */
	private String colStrs; /* 要查询的列名字符串, 不一定是*, 多表查询时, 可以是tblName.*, tblName.colName或者指定的表列名 */
	private String keyName; /* 主键名, 分页时 not in 条件 */
	private String orderKey; /* 排序键, 分页时 order by 使用 */

	/* SQL拼接使用的的Map参数集合 */
	private Map<String, Object> params = new HashMap<String, Object>();

	private int pageSize = 5; /* 每页显示数据条数 */
	private int maxPage = 1; /* 最大页数 */
	private int curPage = 1; /* 当前页 */
	private int rowBegin = 1; /* 起始行 */

	/*
	 * 构造函数
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

			/* 总记录数通用拼接语句 */
			/* 传字符串时不需要空格 */
			String countSQL = "select count(0) from " + tableName + " where 1=1 ";

			/* 根据参数列、值映射进行SQL拼接 */
			/* 构造函数入口已经判断params不为null */
			/* params不存在键-值映射(isEmpty()为true)时不会进入for */
			/* 遍历kv */
			for (Entry<String, Object> kv : params.entrySet()) {
				/* 确保key和value不为null&&不为空字符串 */
				if (null != kv.getKey() && 0 != kv.getKey().trim().length() && null != kv.getValue() && 0 != kv.getValue().toString().trim().length()) {
					countSQL = countSQL + " and " + kv.getKey() + "=" + kv.getValue();
				}
			}

			int count = dao.getCount(countSQL);

			/* 如果在查单条记录时没有查到, count为0 */
			/* 保证rowBegin不为负 */
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

		/* 父查询通用拼接语句 */
		/* 传字符串时不需要空格 */
		String SQL = "select top " + pageSize + " " + colStrs + " from " + tableName + " where 1=1 ";

		/* 子查询通用拼接语句 */
		/* 传字符串时不需要空格 */
		String subSQL = "select top " + rowBegin + " " + keyName + " from " + tableName + " where 1=1 ";

		/* 根据参数列、值映射进行SQL拼接 */
		/* 构造函数入口已经判断params不为null */
		/* params不存在键-值映射(isEmpty()为true)时不会进入for */
		/* 遍历kv */
		for (Entry<String, Object> kv : params.entrySet()) {
			/* 确保key和value不为null&&不为空 */
			if (null != kv.getKey() && 0 != kv.getKey().trim().length() && null != kv.getValue() && 0 != kv.getValue().toString().trim().length()) {
				SQL = SQL + " and " + kv.getKey() + "=" + kv.getValue();
				subSQL = subSQL + " and " + kv.getKey() + "=" + kv.getValue();
			}
		}

		/* 完成SQL拼接 */
		SQL = SQL + " and " + keyName + " not in (" + subSQL + " order by " + orderKey + " " + orderMethod + ") order by " + orderKey + " " + orderMethod;

		return dao.select(SQL, clazz);
	}

	/* 返回翻页工具条 */
	/* 以下同JavaScript分页 */
	/* linkTo如果没有跟参数, 需要传递一个?, 保证取得curPage参数 */
	/* pars为外部拼接好的名-值字符串数组, 形如 parName=parValue */
	public String getBar(String linkTo, int barType, String... pars) {
		StringBuilder parStr = new StringBuilder("");
		
		for (int i = 0; i < pars.length; i++) {
			parStr.append("&" + pars[i]);
		}
		
		/* 默认bar */
		String bar = "<a href=\"" + linkTo + "&curPage=" + (curPage - 1)
				+ parStr.toString() + "\">上一页</a>&nbsp|&nbsp<a href=\""
				+ linkTo + "&curPage=" + (curPage + 1) + parStr.toString()
				+ "\">下一页</a>";
		switch (barType) {
		case 1:
			/* 超链接翻页条 */
			bar = "<a href=\"" + linkTo + "&curPage=0" + parStr.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ (curPage - 1) + parStr.toString()
					+ "\">上一页</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ (curPage + 1) + parStr.toString()
					+ "\">下一页</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ maxPage + parStr.toString() + "\">末页</a>";
			break;
		case 2:
			/* 下拉框翻页条 */
			bar = "<a href=\"" + linkTo + "&curPage=0" + parStr.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\"" + linkTo + "&curPage="
					+ maxPage + parStr.toString()
					+ "\">末页</a>&nbsp|&nbsp跳到<select onchange=\"location='"
					+ linkTo + "&curPage='+this.value+'" + parStr.toString()
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
					+ "&curPage=0"
					+ parStr.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\""
					+ linkTo
					+ "&curPage="
					+ maxPage
					+ parStr.toString()
					+ "\">末页</a>&nbsp|&nbsp<input style='width:30;' id='cp' value="
					+ curPage
					+ "><input style='width:30' type='button' value='GO' onclick=\"location='"
					+ linkTo + "&curPage='+document.all.cp.value"
					+ parStr.toString() + "\">";
			break;
		}
		bar = bar + "(当前 第" + curPage + "页/总计" + maxPage + "页)";
		return bar;
	}

}
