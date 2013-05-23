package com.han;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestFileRead {

	public static void main(String[] args) throws IOException {
		// File代表文件名为new.txt的文件
		File file = new File("new.txt");
		// 判断文件是否存在
		if (file.exists()) {
			// 使用FileReader读文件
			// 需要一个File实例
			FileReader fileReader = new FileReader(file);
			// 使用BufferedReader读取效率更高
			// 需要一个FileReader实例
			BufferedReader bufferReader = new BufferedReader(fileReader);
			String temp = null;
			// 读一行不为空的时候进行输出
			while (null != (temp = bufferReader.readLine())) {
				System.out.println(temp);
			}
			// 关闭bufferReader
			bufferReader.close();
		}
	}

}
