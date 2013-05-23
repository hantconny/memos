package com.common;

import java.io.File;
import java.net.URLDecoder;

public class FileTools {
	
	public static void main(String[]args){
		new FileTools().getAllHead();
	}
	
	/* 定义为get方法, 方便页面EL调用 */
	/* File也有getName()方法, 方便EL调用 */
	public File[] getAllHead() {
		try {
			String path = "";

			/* '/'代表编译后该文件的绝对路径 */
			path = FileTools.class.getResource("/").getPath();

			/* 转译中文乱码 */
			path = URLDecoder.decode(path, "utf-8");

			/* 类编译在/WEB-INF/classes/目录下 */
			/* 将当前路径替换为/image/head/, 转换到图片目录下 */
			path = path.replaceAll("/WEB-INF/classes/", "/image/head/");

			/* 实例化文件夹 */
			/* File既可以是文件夹, 也可以是文件 */
			File dir = new File(path);

			/* 判断是否为文件夹 */
			if (dir.isDirectory()) {
				/* 返回文件夹下的文件列表 */
				return dir.listFiles();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
