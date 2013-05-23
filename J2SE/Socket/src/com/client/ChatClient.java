package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
	
	public static void main(String[] args) {
		new ChatClient().go();
	}
	
	public void go(){
		
		while (true) {
			try {
				// ����һ��sock
				Socket client = new Socket("127.0.0.1", 4242);

				// ʹ��client�Ķ˿��õ�һ���ײ�������
				// ��ʹ�����������õ�һ��writer��������server������Ϣ
				PrintWriter writer = new PrintWriter(client.getOutputStream());

				Scanner scanner = new Scanner(System.in);
				System.out.println("please type: ");
				String userInput = scanner.nextLine();
				writer.println(userInput);
				writer.flush();

				// ����һ���ײ�������������server������
				// �ٽ���һ���߲����������Ч��ȡ
				InputStreamReader inputStreamReader = new InputStreamReader(
						client.getInputStream());
				BufferedReader reader = new BufferedReader(inputStreamReader);

				// ����һ����ʱ������ÿ�ζ�һ������
				String val = reader.readLine();

				// �������������
				System.out.println(val);

				writer.close();
				reader.close();

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
