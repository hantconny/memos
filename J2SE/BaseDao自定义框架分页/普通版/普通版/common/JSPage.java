package com.common;

import java.util.List;

/*
 * 利用页面JavaScript进行分页
 * */
public class JSPage {
	private int pageSize = 5; /* 每页数据条数 */
	private List all; /* 总记录数 */
	private int curPage = 1; /* 当前页 */
	private int maxPage = 1; /* 总页数 */

	/* 利用构造函数初始化必要数据 */
	public JSPage(int pageSize, List all, String curPageStr) {

		/* 如果pageSize小于1，给默认值5 */
		this.pageSize = pageSize < 1 ? 5 : pageSize;

		/* 总记录不为null */
		if (null != all) {
			this.all = all;
			/* 计算总页数 */
			maxPage = all.size() / pageSize;
			maxPage = all.size() % pageSize == 0 ? maxPage : (maxPage + 1);
		}

		/* 当前页字符串不为null并且不等于空字符串 */
		if (null != curPageStr && !"".equals(curPageStr)) {
			/* 得出当前页 */
			curPage = Integer.parseInt(curPageStr);
			curPage = curPage < 1 ? 1 : curPage;
			curPage = curPage > maxPage ? maxPage : curPage;
		}

	}

	/* 返回分页子集 */
	public List getList() {
		if (null != all) {
			int start = (curPage - 1) * pageSize;
			int end = start + pageSize;
			end = end > all.size() ? all.size() : end;
			return all.subList(start, end);
		}
		return null;
	}

	/* 返回翻页工具条 */
	/* 以下同JavaScript分页 */
	public String getBar(String linkTo, int barType, String... pars) {
		StringBuilder par = new StringBuilder("");
		if (null != pars) {
			for (int i = 0; i < pars.length; i++) {
				par.append("&" + pars[i]);
			}
		}
		/* 默认bar */
		String bar = "<a href=\"" + linkTo + "?curPage=" + (curPage - 1)
				+ par.toString() + "\">上一页</a>&nbsp|&nbsp<a href=\"" + linkTo
				+ "?curPage=" + (curPage + 1) + par.toString() + "\">下一页</a>";
		switch (barType) {
		case 1:
			/* 超链接翻页条 */
			bar = "<a href=\"" + linkTo + "?curPage=0" + par.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage - 1) + par.toString()
					+ "\">上一页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage + 1) + par.toString()
					+ "\">下一页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + par.toString() + "\">末页</a>";
			break;
		case 2:
			/* 下拉框翻页条 */
			bar = "<a href=\"" + linkTo + "?curPage=0" + par.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + par.toString()
					+ "\">末页</a>&nbsp|&nbsp<select onchange=\"location='"
					+ linkTo + "?curPage='+this.value+'" + par.toString() + "'\">";
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
					+ par.toString()
					+ "\">首页</a>&nbsp|&nbsp<a href=\""
					+ linkTo
					+ "?curPage="
					+ maxPage
					+ par.toString()
					+ "\">末页</a>&nbsp|&nbsp<input style='width:30;' id='cp' value="
					+ curPage
					+ "><input style='width:30' type='button' value='GO' onclick=\"location='"
					+ linkTo + "?curPage='+document.all.cp.value" + par.toString() + "\">";
			break;
		}
		bar = bar + "(当前 第" + curPage + "/" + maxPage + "页)";
		return bar;
	}

}
