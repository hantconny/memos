package com.common;

import java.util.List;

/*
 * ����ҳ��JavaScript���з�ҳ
 * */
public class JSPage {
	private int pageSize = 5; /* ÿҳ�������� */
	private List all; /* �ܼ�¼�� */
	private int curPage = 1; /* ��ǰҳ */
	private int maxPage = 1; /* ��ҳ�� */

	/* ���ù��캯����ʼ����Ҫ���� */
	public JSPage(int pageSize, List all, String curPageStr) {

		/* ���pageSizeС��1����Ĭ��ֵ5 */
		this.pageSize = pageSize < 1 ? 5 : pageSize;

		/* �ܼ�¼��Ϊnull */
		if (null != all) {
			this.all = all;
			/* ������ҳ�� */
			maxPage = all.size() / pageSize;
			maxPage = all.size() % pageSize == 0 ? maxPage : (maxPage + 1);
		}

		/* ��ǰҳ�ַ�����Ϊnull���Ҳ����ڿ��ַ��� */
		if (null != curPageStr && !"".equals(curPageStr)) {
			/* �ó���ǰҳ */
			curPage = Integer.parseInt(curPageStr);
			curPage = curPage < 1 ? 1 : curPage;
			curPage = curPage > maxPage ? maxPage : curPage;
		}

	}

	/* ���ط�ҳ�Ӽ� */
	public List getList() {
		if (null != all) {
			int start = (curPage - 1) * pageSize;
			int end = start + pageSize;
			end = end > all.size() ? all.size() : end;
			return all.subList(start, end);
		}
		return null;
	}

	/* ���ط�ҳ������ */
	/* ����ͬJavaScript��ҳ */
	public String getBar(String linkTo, int barType, String... pars) {
		StringBuilder par = new StringBuilder("");
		if (null != pars) {
			for (int i = 0; i < pars.length; i++) {
				par.append("&" + pars[i]);
			}
		}
		/* Ĭ��bar */
		String bar = "<a href=\"" + linkTo + "?curPage=" + (curPage - 1)
				+ par.toString() + "\">��һҳ</a>&nbsp|&nbsp<a href=\"" + linkTo
				+ "?curPage=" + (curPage + 1) + par.toString() + "\">��һҳ</a>";
		switch (barType) {
		case 1:
			/* �����ӷ�ҳ�� */
			bar = "<a href=\"" + linkTo + "?curPage=0" + par.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage - 1) + par.toString()
					+ "\">��һҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ (curPage + 1) + par.toString()
					+ "\">��һҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + par.toString() + "\">ĩҳ</a>";
			break;
		case 2:
			/* ������ҳ�� */
			bar = "<a href=\"" + linkTo + "?curPage=0" + par.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\"" + linkTo + "?curPage="
					+ maxPage + par.toString()
					+ "\">ĩҳ</a>&nbsp|&nbsp<select onchange=\"location='"
					+ linkTo + "?curPage='+this.value+'" + par.toString() + "'\">";
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
					+ par.toString()
					+ "\">��ҳ</a>&nbsp|&nbsp<a href=\""
					+ linkTo
					+ "?curPage="
					+ maxPage
					+ par.toString()
					+ "\">ĩҳ</a>&nbsp|&nbsp<input style='width:30;' id='cp' value="
					+ curPage
					+ "><input style='width:30' type='button' value='GO' onclick=\"location='"
					+ linkTo + "?curPage='+document.all.cp.value" + par.toString() + "\">";
			break;
		}
		bar = bar + "(��ǰ ��" + curPage + "/" + maxPage + "ҳ)";
		return bar;
	}

}
