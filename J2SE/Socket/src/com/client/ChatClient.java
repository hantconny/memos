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
				// 建立一个sock
				Socket client = new Socket("127.0.0.1", 4242);

				// 使用client的端口拿到一个底层的输出流
				// 并使用这个输出流得到一个writer，用以向server发送信息
				PrintWriter writer = new PrintWriter(client.getOutputStream());

				Scanner scanner = new Scanner(System.in);
				System.out.println("please type: ");
				String userInput = scanner.nextLine();
				writer.println(userInput);
				writer.flush();

				// 建立一个底层输入流，接受server的数据
				// 再建立一个高层的流，来高效读取
				InputStreamReader inputStreamReader = new InputStreamReader(
						client.getInputStream());
				BufferedReader reader = new BufferedReader(inputStreamReader);

				// 声明一个临时变量，每次读一行数据
				String val = reader.readLine();

				// 输出服务器数据
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
