package com.han;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileWrite {

	public static void main(String[] args) throws IOException {
		// File�������old.txt����ļ�
		// �����ڻᴴ��, ���ڻḲ��
		File file = new File("old.txt");
		// ʹ��file���󴴽�һ��FileWriter����
		FileWriter fileWriter = new FileWriter(file);
		// BufferReaderЧ�ʸ���
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		// д�뻺����
		bufferWriter.write("��������12");
		// ����Ҫflush
		// ǿ�ƻ���������д��
		bufferWriter.flush();
		// �ر�bufferWriter
		bufferWriter.close();
	}

}
