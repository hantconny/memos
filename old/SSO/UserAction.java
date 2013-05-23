package com.web.action;

import java.util.ArrayList;
import java.util.List;

import com.bo.UserBo;
import com.entity.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	private static final long serialVersionUID = -4803888460315722968L;

	/* Bo */
	private UserBo bo = null;

	/* Property */
	private UserInfo user = new UserInfo();
	private ActionContext ctx = null;

	public UserBo getBo() {
		return bo;
	}

	public void setBo(UserBo bo) {
		this.bo = bo;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	@SuppressWarnings("unchecked")
	public String login() {
		ctx = ActionContext.getContext();
		// 当前登录对象
		UserInfo curLogin = bo.login(user);
		// 没有查到，传回到登录页
		if (null == curLogin) {
			return "login_deny";
		}

		// 先从application中拿到onlines集合，保存了所有在线User
		List<UserInfo> onlines = (List<UserInfo>)ctx.getApplication().get("onlines");
		// 遍历在线User
		if (null != onlines && 0 < onlines.size()) {
			for (UserInfo logged : onlines) {
				// 如果onlines中的logged的usrName和当前即将登录的人的名字一样，说明即将登录的用户已经在线
				// 要拒绝登录
				if (logged.getUsrName().equals(curLogin.getUsrName())) {
					return "index";
				}
			}
		}
		// 否则
		// 将即将登录的用户保存到session中
		ctx.getSession().put("login", curLogin);
		if (null == onlines) {
			onlines = new ArrayList<UserInfo>();
		}
		// 将新登录的session放到List中
		// 因为List不会替换相同元素
		onlines.add(curLogin);
		// 将onlines放到application中
		ctx.getApplication().put("onlines", onlines);
		return "login_success";

	}
	
	@SuppressWarnings("unchecked")
	public String logout(){
		ctx = ActionContext.getContext();
		/* 从session中取出login属性, 保存为一个临时变量expireUser */
		UserInfo expireUser = (UserInfo)ctx.getSession().get("login");
		/* 从application中取出onlines集合 */
		List<UserInfo> onlines = (List<UserInfo>)ctx.getApplication().get("onlines");
		/* 从onlines集合中移除expireUser */
		onlines.remove(expireUser);
		/* 从session中移除login属性 */
		ctx.getSession().remove("login");
		return "index";
	}
}
