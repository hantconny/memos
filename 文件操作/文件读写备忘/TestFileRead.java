package com.han;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestFileRead {

	public static void main(String[] args) throws IOException {
		// File�����ļ���Ϊnew.txt���ļ�
		File file = new File("new.txt");
		// �ж��ļ��Ƿ����
		if (file.exists()) {
			// ʹ��FileReader���ļ�
			// ��Ҫһ��Fileʵ��
			FileReader fileReader = new FileReader(file);
			// ʹ��BufferedReader��ȡЧ�ʸ���
			// ��Ҫһ��FileReaderʵ��
			BufferedReader bufferReader = new BufferedReader(fileReader);
			String temp = null;
			// ��һ�в�Ϊ�յ�ʱ��������
			while (null != (temp = bufferReader.readLine())) {
				System.out.println(temp);
			}
			// �ر�bufferReader
			bufferReader.close();
		}
	}

}
