package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
			int len=new File(prop.getProperty("dataDir")).listFiles().length;
			for(int i=0;i<len;++i) {
				Runtime.getRuntime().exec("java -jar tt.jar");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
