package com.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class Env {
	private Properties prop;
	private WebClient client;
	private static Env env = null;
	private Env(String f) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = new WebClient(BrowserVersion.CHROME);
		client.getOptions().setCssEnabled(true);
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setTimeout(40000);
		client.getOptions().setThrowExceptionOnFailingStatusCode(false);
		client.setJavaScriptTimeout(50000);
		client.getOptions().setThrowExceptionOnScriptError(false);
		client.getOptions().setRedirectEnabled(true);
		client.setAjaxController(new NicelyResynchronizingAjaxController());
		client.getOptions().setRedirectEnabled(true);
		client.getCookieManager().setCookiesEnabled(true);
	}
	public Env() {
		this(null);
		
	}
	
	
	public static Env newInstance(Object o) {
		if (env != null)
			return env;
		if (o == null) {
			env = new Env("config.properties");
		} else if (o instanceof String) {
			env = new Env((String) o);
		}
		return env;
	}
	public static Env newInstance() {
		return newInstance(null);
	}
	public WebClient getClient() {
		return client;
	}
	public Properties getProp() {
		return prop;
	}
	
	
	public void waitJS() {
		client.waitForBackgroundJavaScript(1000*3);
	}
	
	public void waitJS(int t) {
		client.waitForBackgroundJavaScript(t);
	}
	
	public void waitJS2() {
		client.waitForBackgroundJavaScript(1000*6);
	}
	
	public void waitJS4() {
		client.waitForBackgroundJavaScript(1000*12);
	}
	
}
