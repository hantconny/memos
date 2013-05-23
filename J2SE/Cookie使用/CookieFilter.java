package com.web;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Cart;
import com.entity.CartItem;

/*
 * �ù���������session�еĹ��ﳵ��Ϣ��ӵ�����Cookie��
 * 
 * */

public class CookieFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {

		/* ת��ΪHTTP������\��Ӧ\session */
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession(false);

		/* ����ù�����ʱ, session���Դ���, cart���ܲ����� */
		/* ��¼��ֱ�ӽ��빺�ﳵ, ���ﳵ������ */
		/* ������, ֱ��ʵ���� */
		Cart cart = (Cart) session.getAttribute("cart");
		if (null == cart) {
			cart = new Cart();
		}
		
		/* ��AddToCart.jsp ��������> ����session��cart */
		/* �޹��ﳵCookie */
		
		/* ���Ա�ʶcookie�ĺ�׺ */
		int i = 1;
		
		/* �ɶ���ҳ����빺�ﳵ */
		/* �����ݱ��浽Cookie */
		Iterator<CartItem> it = cart.getItems().values().iterator();
		while (it.hasNext()) {
			CartItem item = it.next();

			/* �½�������idCookie */
			Cookie idCookie = new Cookie("restaurant_id_" + i, item.getFoodBean().getFoodId());
			/* ����� */
			/* Ҫ����cookie, ����ʹ��setMaxAge(0) */
			idCookie.setMaxAge(30 * 24 * 60 * 60);
			/* �趨����·��, ��ʱ�Զ������·�����ܲ��ڸ�Ŀ¼�� */
			idCookie.setPath("/restaurant");
			/* ��cookie���ӵ�response�� */
			response.addCookie(idCookie);

			/* �½�������numCookie */
			Cookie numCookie = new Cookie("restaurant_num_" + i, item.getNumber());
			numCookie.setMaxAge(30 * 24 * 60 * 60);
			numCookie.setPath("/restaurant");
			response.addCookie(numCookie);

			i++;
		}
		/* ��ջ */
		chain.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
