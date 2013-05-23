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
		// 让server监听4242端口，因为client向这个端口发送信息
		try {
			ServerSocket serverSock = new ServerSocket(4242);
			
			while (true) {
				// accept以后，会再服务器上分配一个其他端口(非4242)，用以和client通信
				// 而4242端口仍然继续监听其他client请求
				Socket sock = serverSock.accept();
				
				// 从服务器分配的新端口拿到一个底层的输出流
				// 使用这个底层输出流拿到一个高层的writer
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				
				// 从服务器分配的新端口拿到一个底层的输入流
				// 使用这个底层的输入流拿到一个高层的reader
				InputStreamReader inputStreamReader = new InputStreamReader(sock.getInputStream());
				BufferedReader reader = new BufferedReader(inputStreamReader);
				
				// 使用reader读取client发送的信息
				// 使用writer将信息打印给客户端
				writer.println("server输出：" + reader.readLine());
				writer.flush();
				
				// 关闭读写器
				writer.close();
				reader.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
