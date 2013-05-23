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
		System.out.println("���Դ�session�н����");
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
	}
	
	public void sessionCreated(HttpSessionEvent arg0) {
	}

	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent event) {
		/* ��event������ȡ��session, ��ʱ��session��û��expire
		 * Ȼ���session��ȡ��login, ÿ��session�ж���һ��login��ʾ��¼����
		 * ����һ��expireUser��ʾ������ʱ��user */
		UserInfo expireUser = (UserInfo)event.getSession().getAttribute("login");
		/* �����userΪ��, ˵������������
		 * ��ô��user.action���Ѿ���������
		 * ֱ��return */
		if (null == expireUser) {
			return;
		}
		/* ����, ˵�����쳣���� */
		/* ��expireUser��ȡ��usrName��Ϊһ����ʱֵ */
		String tempName = expireUser.getUsrName();
		/* ����event�����õ�session
		 * ����session�õ�ServletContext, ��application
		 * ��application��ȡ��onlines����
		 * onlines��һ��������������User��List */
		List<UserInfo> onlines = (List<UserInfo>)event.getSession().getServletContext().getAttribute("onlines");
		/* ��onlines��Ϊ��&&size����0ʱѭ�� */
		if (null != onlines && 0 < onlines.size()) {
			/* ����onlines�������е�session */
			for (UserInfo logged : onlines) {
				/* ���onlines�е��û����ͼ������ڵ��û���һ�� */
				if (logged.getUsrName().equals(tempName)) {
					/* ��onlines���Ƴ��û� */
					onlines.remove(logged);
					break;
				}
			}
		}
	}

}