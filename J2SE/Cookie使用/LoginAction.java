package com.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bo.impl.FoodBoImpl;
import com.bo.impl.UserBoImpl;
import com.entity.Cart;
import com.entity.FoodBean;
import com.entity.UserBean;
import com.web.Action;
import com.web.ActionForm;

public class LoginAction implements Action {

	public void execute(ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		/* 无论何时登录, 都从request中取出cookie */
		Cookie[] cs = request.getCookies();
		
		/* 恢复购物车状态 */
		Cart cart = new Cart();
		if (null != cs) {
			/* 使用Cookie生成session */
			int i = 1;
			String foodId = null;
			String number = null;
			for (Cookie c : cs) {
				/* c.getName()取得cookie名 */
				if (("restaurant_id_" + i).equals(c.getName())) {
					/* c.getValue()取得对应cookie值 */
					foodId = c.getValue();
				}
				if (("restaurant_num_" + i).equals(c.getName())) {
					number = c.getValue();
				}
				if (null != foodId && null != number) {
					FoodBean foodBean = new FoodBean();
					foodBean.setFoodId(foodId);
					/* 调用Bo查出foodBean */
					foodBean = new FoodBoImpl().selectById(foodBean);
					/* 使用Cookie中的信息加载 */
					/* 恢复关闭页面前的购物车状态 */
					cart.addItem(foodBean, number);
					request.getSession().setAttribute("cart", cart);
					/* 置空 */
					foodId = null;
					number = null;
					i++;
				}
			}
		}
		
		/* Cookie可用 */
		/* 使用Cookie中的用户名密码 */
		/* 用户不再登录 */
		String userName = null;
		String userPass = null;
		UserBean userBean = null;
		if (null != cs) {
			for (Cookie cookie : cs) {
				if ("restaurant_nameCookie".equals(cookie.getName())) {
					userName = cookie.getValue();
				}
				if ("restaurant_passCookie".equals(cookie.getName())) {
					userPass = cookie.getValue();
				}
				/* 将Cookie中的用户信息设置到userBean */
				if (null != userName && null != userPass) {
					userBean = new UserBean();
					userBean.setUserName(userName);
					userBean.setUserPass(userPass);
					
					/* 调用Bo方法 */
					UserBean loginBean = new UserBoImpl().login(userBean);
					
					if (null != loginBean) {
						/* 使用cookie中的信息登录成功 */
						/* 保存到session, 重定向到Init.do */
						request.getSession().setAttribute("login", loginBean);
						response.sendRedirect("Init.do");
						return;
					}
				}
			}
		}

		/* Cookie不可用 */
		/* 使用form登录 */
		/* 判断form不为null, 从form中取得userBean */
		if (null != form) {
			userBean = new UserBoImpl().login((UserBean)form.getEntity());
		}
		if (null != userBean) {
			/* 创建cookie */
			Cookie nameCookie = new Cookie("restaurant_nameCookie", userBean.getUserName());
			Cookie passCookie = new Cookie("restaurant_passCookie", userBean.getUserPass());
			
			int keepTime = 0;
			if (null != request.getParameter("keepTime") && "" != request.getParameter("keepTime")) {
				keepTime = Integer.parseInt(request.getParameter("keepTime"));
			}
			
			/* 设定保存时间并添加到response中 */
			nameCookie.setMaxAge(keepTime);
			/* 设定cookie路径, 保存到默认寻址的根目录 */
			nameCookie.setPath("/restaurant");
			response.addCookie(nameCookie);
			
			/* 设定保存时间并添加到response中 */
			passCookie.setMaxAge(keepTime);
			/* 设定cookie路径, 保存到默认寻址的根目录 */
			passCookie.setPath("/restaurant");
			response.addCookie(passCookie);
			
			/* 保存到session */
			request.getSession().setAttribute("login", userBean);
			/* 转发 */
			response.sendRedirect("Init.do");
			return;
		}
		
		/* Cookie和session中都没有信息 */
		/* 重定向到login页面要求登录 */
		response.sendRedirect("/restaurant/jsp/login.jsp");
	}

}
