package com.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Upload {
	private Env env;
	private List<File> fs;
	private String passwd;
	private String pre;

	private static BufferedReader reader;
	private static BufferedReader reader2;
	private static BufferedWriter writer;
	private static BufferedWriter writer2;
	private static Set<String> doset;
	private static Set<String> undoset;

	static {
		try {
			reader = new BufferedReader(new FileReader("do.txt"));
			reader2 = new BufferedReader(new FileReader("undo.txt"));
			writer = new BufferedWriter(new FileWriter("do.txt"));
			writer2 = new BufferedWriter(new FileWriter("undo.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		doset = new HashSet<String>();
		undoset = new HashSet<String>();
		String s = null;
		try {
			while ((s = reader.readLine()) != null) {
				doset.add(s);
			}

			while ((s = reader2.readLine()) != null) {
				undoset.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Upload(Env env, List<File> fs) {
		this.env = env;
		this.fs = fs;
		this.passwd = env.getProp().getProperty("pwd");
	}

	public Upload(Env env, List<File> fs, String pre) {
		this.env = env;
		this.fs = fs;
		this.passwd = env.getProp().getProperty("pwd");
		this.pre = pre;
	}

	private void login(String stid) {

		String url = env.getProp().getProperty("homeUrl");
		HtmlPage page = null;
		try {
			page = env.getClient().getPage(url);
			env.waitJS2();
			HtmlForm hf = page.getFormByName("loginForm");
			hf.getInputByName("stid").setAttribute("value", stid);
			hf.getInputByName("pwd").setAttribute("value", passwd);

			HtmlElement btn = hf.getElementsByTagName("button").get(0);
			HtmlPage p = btn.click();
			// System.out.println(p.asText());
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
	}

	public void execute(String uid, File f) {
		if (doset.contains(uid)||undoset.contains(uid))
			return;
		try {
			login(uid);
			HtmlPage page = null;
			try {
				page = env.getClient().getPage(env.getProp().getProperty("uploadUrl"));
				env.waitJS2();
				HtmlForm hf = page.getFormByName("upload");
				hf.getInputByName("FILE1").setAttribute("value", f.getAbsolutePath());
				hf.getInputByName("Submit").click();
				env.waitJS4();
			} catch (FailingHttpStatusCodeException | IOException e) {
				e.printStackTrace();
			}

			doset.add(uid);
		} catch (Exception e) {
			undoset.add(uid);
		} finally {
			try {
				for (String s : doset) {
					writer.write(s);
				}
				writer.flush();
				for (String s : undoset) {
					writer2.write(s);
				}
				writer2.flush();
			} catch (Exception e) {

			}
		}
	}

	public void batchExecute() {
		for (File f : fs) {
			String uid = pre + f.getName().split("\\.")[0];
			execute(uid, f);
		}
	}

}
