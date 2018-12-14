package org.crap.wxbot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class KeyWord {
	
	private static File KEYWORD_FILE = new File(System.getProperty("user.dir")+"/keyword.properties");
	
	private static File KEYWORD_GROUP_FILE = new File(System.getProperty("user.dir")+"/keyword_group.properties");
	
	public static Properties KEYWORD = new Properties();
	
	public static Properties KEYWORD_GROUP = new Properties();
	
	static {
		load();
	}
	
	public static void load() {
		
		if(!KEYWORD_FILE.exists()) {
			try {
				KEYWORD.store(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(KEYWORD_FILE), "utf-8")), "keyword");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(KEYWORD_FILE);
				KEYWORD.clear();
				KEYWORD.load(new InputStreamReader(fis, "utf-8"));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fis != null)
						fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!KEYWORD_GROUP_FILE.exists()) {
			try {
				KEYWORD_GROUP.store(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(KEYWORD_GROUP_FILE), "utf-8")), "keyword_group");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(KEYWORD_GROUP_FILE);
				KEYWORD_GROUP.clear();
				KEYWORD_GROUP.load(new InputStreamReader(fis, "utf-8"));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(fis != null)
						fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
