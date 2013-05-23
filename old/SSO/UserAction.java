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
		// ��ǰ��¼����
		UserInfo curLogin = bo.login(user);
		// û�в鵽�����ص���¼ҳ
		if (null == curLogin) {
			return "login_deny";
		}

		// �ȴ�application���õ�onlines���ϣ���������������User
		List<UserInfo> onlines = (List<UserInfo>)ctx.getApplication().get("onlines");
		// ��������User
		if (null != onlines && 0 < onlines.size()) {
			for (UserInfo logged : onlines) {
				// ���onlines�е�logged��usrName�͵�ǰ������¼���˵�����һ����˵��������¼���û��Ѿ�����
				// Ҫ�ܾ���¼
				if (logged.getUsrName().equals(curLogin.getUsrName())) {
					return "index";
				}
			}
		}
		// ����
		// ��������¼���û����浽session��
		ctx.getSession().put("login", curLogin);
		if (null == onlines) {
			onlines = new ArrayList<UserInfo>();
		}
		// ���µ�¼��session�ŵ�List��
		// ��ΪList�����滻��ͬԪ��
		onlines.add(curLogin);
		// ��onlines�ŵ�application��
		ctx.getApplication().put("onlines", onlines);
		return "login_success";

	}
	
	@SuppressWarnings("unchecked")
	public String logout(){
		ctx = ActionContext.getContext();
		/* ��session��ȡ��login����, ����Ϊһ����ʱ����expireUser */
		UserInfo expireUser = (UserInfo)ctx.getSession().get("login");
		/* ��application��ȡ��onlines���� */
		List<UserInfo> onlines = (List<UserInfo>)ctx.getApplication().get("onlines");
		/* ��onlines�������Ƴ�expireUser */
		onlines.remove(expireUser);
		/* ��session���Ƴ�login���� */
		ctx.getSession().remove("login");
		return "index";
	}
}
