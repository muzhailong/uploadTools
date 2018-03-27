package com;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.core.Env;
import com.core.Upload;
import com.log.Log;

public class StartUp {
	public static void start() {
		Env env = Env.newInstance();
		File dir = new File(env.getProp().getProperty("dataDir"));
		File[] fs = dir.listFiles();
		List<File> flt = new LinkedList<File>();
		String uname = (fs[0].getName()).split("\\.")[0];
		String s = new String("U2015170");
		// U201517056 --->>10
		String pre = s.substring(0, uname.length());

		for (File f : fs) {
			flt.add(f);
		}
		Upload ud = new Upload(env, flt, pre);
		ud.batchExecute();
	}

	public static void main(String[] args) {
		start();
		Log.all();
		new Scanner(System.in).nextLine();
	}
}
