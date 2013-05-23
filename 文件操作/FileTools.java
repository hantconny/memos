package com.common;

import java.io.File;
import java.net.URLDecoder;

public class FileTools {
	
	public static void main(String[]args){
		new FileTools().getAllHead();
	}
	
	/* ����Ϊget����, ����ҳ��EL���� */
	/* FileҲ��getName()����, ����EL���� */
	public File[] getAllHead() {
		try {
			String path = "";

			/* '/'����������ļ��ľ���·�� */
			path = FileTools.class.getResource("/").getPath();

			/* ת���������� */
			path = URLDecoder.decode(path, "utf-8");

			/* �������/WEB-INF/classes/Ŀ¼�� */
			/* ����ǰ·���滻Ϊ/image/head/, ת����ͼƬĿ¼�� */
			path = path.replaceAll("/WEB-INF/classes/", "/image/head/");

			/* ʵ�����ļ��� */
			/* File�ȿ������ļ���, Ҳ�������ļ� */
			File dir = new File(path);

			/* �ж��Ƿ�Ϊ�ļ��� */
			if (dir.isDirectory()) {
				/* �����ļ����µ��ļ��б� */
				return dir.listFiles();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
