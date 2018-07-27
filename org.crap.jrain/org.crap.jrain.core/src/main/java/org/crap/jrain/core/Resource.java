package org.crap.jrain.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.crap.jrain.core.util.FileUtil;

/** 
 * 系统配置文件类 
 * @author Crap 
 * 
 */
public class Resource extends TimerTask {
	
	private static Logger log = LogManager.getLogger(Resource.class);
	
	private static Resource RESOURCE;
	
	public final static Map<String, Properties> POP_MAP = new HashMap<String, Properties>();
	
	private static String sysConfig;
	
	private boolean autoUpdate;
	
	private int updateTime = 60000;
	
	private Timer timer;
	
	private static String charset = "utf-8";
	
	private static String[] packagesToScan;

    private Resource(){}
    
	public static void init() {
		if(RESOURCE == null)
			RESOURCE = new Resource();
		RESOURCE.loadConfig();
	}
	
	public static Resource getInstance(){
    	return RESOURCE;
    }
	
	public void loadConfig() {
		load();
		if(autoUpdate){
			log.info("Configation start auto load. info:[updateTime={}ms]", updateTime);
			timer = new Timer(true);
			timer.schedule(RESOURCE, updateTime, updateTime);
		}
	}
	
    public void run(){
    	load();
    }
    
    public void load(){
    	log.debug("Configation load, info:[charset={},packagesToScan={}]", charset, Arrays.toString(packagesToScan));
    	URL url = Resource.class.getResource("/");
    	log.debug("Configation url:{}", url);
    	if(url != null && !"null".equals(url.toString())){
    		loadProp();
    	}else{
    		loadJarProp();
    	}
    }
    
    public void loadProp(){
    	//获取config路径下配置文件
		List<File> files = new ArrayList<File>();
		for (String path : packagesToScan) {
			String realPath = Resource.class.getProtectionDomain().getCodeSource().getLocation().getPath()+path.replace(".", "/");
			try {
				path = Resource.class.getResource("/").toURI().getPath()+path.replace(".", "/");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			log.info("load path:{}", path);
			List<File> loadFiles = FileUtil.getDirFiles(new File(path));
			
			if(loadFiles == null || loadFiles.size() ==0) {
				log.info("load realPath:{}", path);
				loadFiles = FileUtil.getDirFiles(new File(realPath));
			}
			files.addAll(loadFiles);
		}
    	log.info("load files num:{}", files.size());
    	for (File file : files) {
    		log.info("load file {}", file);
		}
    	InputStream in = null;
    	Properties p = null;
    	String key = null;
    	try{
			for (File file : files) {
				key = FileUtil.getFileName(file.getName());
				in = new FileInputStream(file);
				InputStreamReader inR = new InputStreamReader(in, charset);
				if(POP_MAP.containsKey(key))
					POP_MAP.get(key).load(inR);
				else{
					p = new Properties();
					p.load(inR);
					POP_MAP.put(key, p);
				}
			}
    	}catch(Exception e){  
            e.printStackTrace(); 
            if(autoUpdate) {
            	timer.cancel();
            	log.error("Configation load error atuto update cancel. info:[filename={}]", key);
            } else{
            	log.error("Configation load error. info:[filename={}]", key);
            }
        }finally{
			try {
				if(in != null)
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public void loadJarProp(){
    	//从相对于 jar中获取config路径下配置文件
		for (String path : packagesToScan) {
			//正式环境使用
			path = path.replace(".", "/");
			URL url = Resource.class.getClassLoader().getResource(path);
			if(url == null)
				continue;
			String[] jarInfo = url.getFile().split("!");
			String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
			log.info("load jarFilePath:{}", jarFilePath);
			JarFile jarFile = null;
			try {
				jarFile = new JarFile(jarFilePath);
				log.info("Configation jarFile:{}", jarFile);
				Enumeration<JarEntry> entrys = jarFile.entries();
				while (entrys.hasMoreElements()) {
					JarEntry jarEntry = entrys.nextElement();
					String entryName = jarEntry.getName();
					if (entryName.endsWith(".properties") && entryName.startsWith(path)) {
						InputStream in = Resource.class.getClassLoader().getResourceAsStream(entryName);
						InputStreamReader inR = new InputStreamReader(in, charset);
						String[] pathArr = entryName.split("[.]")[0].split("[/]");
						String key = pathArr[pathArr.length - 1];
						
						if(POP_MAP.containsKey(key))
							POP_MAP.get(key).load(inR);
						else{
							Properties p = new Properties();
							p.load(inR);
							POP_MAP.put(key, p);
						}
						log.info("load jar file path:{}", key);
					}
				}
				log.info("Configation POP_MAP:{}", POP_MAP);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
    }

    public static String getSysProp(String key) {
    	Properties pop = POP_MAP.get(sysConfig);
    	if(pop == null)
    		return null;
		return pop.getProperty(key);
	}
    
    
    
    
    
    
    
    public boolean isAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	public static String[] getPackagesToScan() {
		return packagesToScan;
	}

	public static void setPackagesToScan(String[] packagesToScan) {
		Resource.packagesToScan = packagesToScan;
	}

	public int getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	public static String getCharset() {
		return charset;
	}

	public static void setCharset(String charset) {
		Resource.charset = charset;
	}
	
	public static Properties getProp(String propName) {
		return POP_MAP.get(propName);
	}
	
	public static String getSysConfig() {
		return sysConfig;
	}

	public static void setSysConfig(String sysConfig) {
		Resource.sysConfig = sysConfig;
	}
	
	

    public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}  