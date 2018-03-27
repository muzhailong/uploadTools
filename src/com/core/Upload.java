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
import com.log.Log;

public class Upload {
	private Env env;
	private List<File> fs;
	private String passwd;
	private String pre;

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
			env.waitJS4();
			HtmlForm hf = page.getFormByName("loginForm");
			hf.getInputByName("stid").setAttribute("value", stid);
			hf.getInputByName("pwd").setAttribute("value", passwd);
			HtmlElement btn = hf.getElementsByTagName("button").get(0);
			HtmlPage p = btn.click();
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
			Log.erWriter(stid+"   登录失败"+"\r\n");
		}
	}

	public void execute(String uid, File f) {
		login(uid);
		HtmlPage page = null;
		try {
			page = env.getClient().getPage(env.getProp().getProperty("uploadUrl"));
			env.waitJS2();
			HtmlForm hf = page.getFormByName("upload");
			hf.getInputByName("FILE1").setAttribute("value", f.getAbsolutePath());
			hf.getInputByName("Submit").click();
			env.waitJS4();
			Log.writer(uid+"\r\n");
		} catch (FailingHttpStatusCodeException | ClassCastException | IOException e) {
			Log.erWriter(uid+"\r\n");
			e.printStackTrace();
		}
	}

	public void batchExecute() {
		for (File f : fs) {
			env.clearCookie();
			String uid = pre + f.getName().split("\\.")[0];
			execute(uid, f);
		}
	}

}
