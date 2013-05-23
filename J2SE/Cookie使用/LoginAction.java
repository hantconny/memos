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
		
		/* ���ۺ�ʱ��¼, ����request��ȡ��cookie */
		Cookie[] cs = request.getCookies();
		
		/* �ָ����ﳵ״̬ */
		Cart cart = new Cart();
		if (null != cs) {
			/* ʹ��Cookie����session */
			int i = 1;
			String foodId = null;
			String number = null;
			for (Cookie c : cs) {
				/* c.getName()ȡ��cookie�� */
				if (("restaurant_id_" + i).equals(c.getName())) {
					/* c.getValue()ȡ�ö�Ӧcookieֵ */
					foodId = c.getValue();
				}
				if (("restaurant_num_" + i).equals(c.getName())) {
					number = c.getValue();
				}
				if (null != foodId && null != number) {
					FoodBean foodBean = new FoodBean();
					foodBean.setFoodId(foodId);
					/* ����Bo���foodBean */
					foodBean = new FoodBoImpl().selectById(foodBean);
					/* ʹ��Cookie�е���Ϣ���� */
					/* �ָ��ر�ҳ��ǰ�Ĺ��ﳵ״̬ */
					cart.addItem(foodBean, number);
					request.getSession().setAttribute("cart", cart);
					/* �ÿ� */
					foodId = null;
					number = null;
					i++;
				}
			}
		}
		
		/* Cookie���� */
		/* ʹ��Cookie�е��û������� */
		/* �û����ٵ�¼ */
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
				/* ��Cookie�е��û���Ϣ���õ�userBean */
				if (null != userName && null != userPass) {
					userBean = new UserBean();
					userBean.setUserName(userName);
					userBean.setUserPass(userPass);
					
					/* ����Bo���� */
					UserBean loginBean = new UserBoImpl().login(userBean);
					
					if (null != loginBean) {
						/* ʹ��cookie�е���Ϣ��¼�ɹ� */
						/* ���浽session, �ض���Init.do */
						request.getSession().setAttribute("login", loginBean);
						response.sendRedirect("Init.do");
						return;
					}
				}
			}
		}

		/* Cookie������ */
		/* ʹ��form��¼ */
		/* �ж�form��Ϊnull, ��form��ȡ��userBean */
		if (null != form) {
			userBean = new UserBoImpl().login((UserBean)form.getEntity());
		}
		if (null != userBean) {
			/* ����cookie */
			Cookie nameCookie = new Cookie("restaurant_nameCookie", userBean.getUserName());
			Cookie passCookie = new Cookie("restaurant_passCookie", userBean.getUserPass());
			
			int keepTime = 0;
			if (null != request.getParameter("keepTime") && "" != request.getParameter("keepTime")) {
				keepTime = Integer.parseInt(request.getParameter("keepTime"));
			}
			
			/* �趨����ʱ�䲢��ӵ�response�� */
			nameCookie.setMaxAge(keepTime);
			/* �趨cookie·��, ���浽Ĭ��Ѱַ�ĸ�Ŀ¼ */
			nameCookie.setPath("/restaurant");
			response.addCookie(nameCookie);
			
			/* �趨����ʱ�䲢��ӵ�response�� */
			passCookie.setMaxAge(keepTime);
			/* �趨cookie·��, ���浽Ĭ��Ѱַ�ĸ�Ŀ¼ */
			passCookie.setPath("/restaurant");
			response.addCookie(passCookie);
			
			/* ���浽session */
			request.getSession().setAttribute("login", userBean);
			/* ת�� */
			response.sendRedirect("Init.do");
			return;
		}
		
		/* Cookie��session�ж�û����Ϣ */
		/* �ض���loginҳ��Ҫ���¼ */
		response.sendRedirect("/restaurant/jsp/login.jsp");
	}

}
