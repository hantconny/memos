package com.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	
	public static void main(String[] args) {
		new ChatServer().go();
	}

	public void go() {
		// ��server����4242�˿ڣ���Ϊclient������˿ڷ�����Ϣ
		try {
			ServerSocket serverSock = new ServerSocket(4242);
			
			while (true) {
				// accept�Ժ󣬻��ٷ������Ϸ���һ�������˿�(��4242)�����Ժ�clientͨ��
				// ��4242�˿���Ȼ������������client����
				Socket sock = serverSock.accept();
				
				// �ӷ�����������¶˿��õ�һ���ײ�������
				// ʹ������ײ�������õ�һ���߲��writer
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				
				// �ӷ�����������¶˿��õ�һ���ײ��������
				// ʹ������ײ���������õ�һ���߲��reader
				InputStreamReader inputStreamReader = new InputStreamReader(sock.getInputStream());
				BufferedReader reader = new BufferedReader(inputStreamReader);
				
				// ʹ��reader��ȡclient���͵���Ϣ
				// ʹ��writer����Ϣ��ӡ���ͻ���
				writer.println("server�����" + reader.readLine());
				writer.flush();
				
				// �رն�д��
				writer.close();
				reader.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
