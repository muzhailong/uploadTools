package com.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {
	private static BufferedWriter writer;
	private static BufferedWriter erWriter;
	private static int sucessNum;
	private static int errNum;
	
	static {
		try {
			writer=new BufferedWriter(new FileWriter("do.txt",true));
			erWriter=new BufferedWriter(new FileWriter("err.txt",true));
			
			writer.write("\r\n"+new Date().toString()+"\r\n");
			erWriter.write("\r\n"+new Date().toString()+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void writer(String str) {
		try {
			writer.write(str);
			writer.flush();
			++sucessNum;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void erWriter(String str) {
		try {
			erWriter.write(str);
			erWriter.flush();
			++errNum;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void all() {
		System.out.println("成功："+sucessNum);
		System.out.println("失败："+errNum);
	}
	
}
