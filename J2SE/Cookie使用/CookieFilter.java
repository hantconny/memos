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
 * 该过滤器负责将session中的购物车信息添加到本地Cookie中
 * 
 * */

public class CookieFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {

		/* 转换为HTTP的请求\响应\session */
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession(false);

		/* 进入该过滤器时, session绝对存在, cart可能不存在 */
		/* 登录后直接进入购物车, 购物车不存在 */
		/* 不存在, 直接实例化 */
		Cart cart = (Cart) session.getAttribute("cart");
		if (null == cart) {
			cart = new Cart();
		}
		
		/* 由AddToCart.jsp ————> 存在session和cart */
		/* 无购物车Cookie */
		
		/* 用以标识cookie的后缀 */
		int i = 1;
		
		/* 由订购页面进入购物车 */
		/* 将内容保存到Cookie */
		Iterator<CartItem> it = cart.getItems().values().iterator();
		while (it.hasNext()) {
			CartItem item = it.next();

			/* 新建并保存idCookie */
			Cookie idCookie = new Cookie("restaurant_id_" + i, item.getFoodBean().getFoodId());
			/* 以秒计 */
			/* 要销毁cookie, 可以使用setMaxAge(0) */
			idCookie.setMaxAge(30 * 24 * 60 * 60);
			/* 设定保存路径, 有时自动保存的路径可能不在根目录下 */
			idCookie.setPath("/restaurant");
			/* 将cookie增加到response中 */
			response.addCookie(idCookie);

			/* 新建并保存numCookie */
			Cookie numCookie = new Cookie("restaurant_num_" + i, item.getNumber());
			numCookie.setMaxAge(30 * 24 * 60 * 60);
			numCookie.setPath("/restaurant");
			response.addCookie(numCookie);

			i++;
		}
		/* 出栈 */
		chain.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
