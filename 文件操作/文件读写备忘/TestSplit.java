package com.han;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestSplit {

	public static void main(String[] args) throws IOException {
		File file = new File("quiz.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write("what's blue and yellow?=green\n");
		writer.write("what's blue and red?=purple");
		writer.flush();
		writer.close();
		
		if (file.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String temp = null;
			while (null != (temp = reader.readLine())) {
				String[] quiz = temp.split("=");
				for (String token : quiz) {
					System.out.println(token);
				}
			}
		}
	}

}
