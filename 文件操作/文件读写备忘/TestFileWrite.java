package com.han;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileWrite {

	public static void main(String[] args) throws IOException {
		// File对象代表old.txt这个文件
		// 不存在会创建, 存在会覆盖
		File file = new File("old.txt");
		// 使用file对象创建一个FileWriter对象
		FileWriter fileWriter = new FileWriter(file);
		// BufferReader效率更高
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		// 写入缓冲区
		bufferWriter.write("测试中文12");
		// 必须要flush
		// 强制缓冲区立即写入
		bufferWriter.flush();
		// 关闭bufferWriter
		bufferWriter.close();
	}

}
