package com.entity;

import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserInfo implements java.io.Serializable,
		HttpSessionBindingListener, HttpSessionListener {

	private static final long serialVersionUID = -3060163309799788935L;
	private Integer usrId;
	private String usrName;
	private String usrPwd;
	private Integer usrStatus;

	public UserInfo() {
	}

	public Integer getUsrId() {
		return this.usrId;
	}

	public void setUsrId(Integer usrId) {
		this.usrId = usrId;
	}

	public String getUsrName() {
		return this.usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrPwd() {
		return this.usrPwd;
	}

	public void setUsrPwd(String usrPwd) {
		this.usrPwd = usrPwd;
	}

	public Integer getUsrStatus() {
		return this.usrStatus;
	}

	public void setUsrStatus(Integer usrStatus) {
		this.usrStatus = usrStatus;
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("属性从session中解除绑定");
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
	}
	
	public void sessionCreated(HttpSessionEvent arg0) {
	}

	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent event) {
		/* 从event对象中取得session, 此时的session还没有expire
		 * 然后从session中取出login, 每个session中都有一个login表示登录对象
		 * 定义一个expireUser表示即将超时的user */
		UserInfo expireUser = (UserInfo)event.getSession().getAttribute("login");
		/* 如果该user为空, 说明是正常下线
		 * 那么在user.action中已经做过处理
		 * 直接return */
		if (null == expireUser) {
			return;
		}
		/* 否则, 说明是异常下线 */
		/* 从expireUser中取出usrName作为一个临时值 */
		String tempName = expireUser.getUsrName();
		/* 利用event对象拿到session
		 * 利用session拿到ServletContext, 即application
		 * 从application中取出onlines集合
		 * onlines是一个包含所有在线User的List */
		List<UserInfo> onlines = (List<UserInfo>)event.getSession().getServletContext().getAttribute("onlines");
		/* 当onlines不为空&&size大于0时循环 */
		if (null != onlines && 0 < onlines.size()) {
			/* 遍历onlines里面所有的session */
			for (UserInfo logged : onlines) {
				/* 如果onlines中的用户名和即将过期的用户名一致 */
				if (logged.getUsrName().equals(tempName)) {
					/* 从onlines中移除用户 */
					onlines.remove(logged);
					break;
				}
			}
		}
	}

}