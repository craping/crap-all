package org.crap.jrain.core.launch;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crap.jrain.core.Config;
import org.crap.jrain.core.Resource;
import org.crap.jrain.core.asm.adapter.DataPumpAdapter;
import org.crap.jrain.core.asm.adapter.PumpAdapter;
import org.crap.jrain.core.asm.annotation.Pipe;
import org.crap.jrain.core.asm.annotation.Pump;
import org.crap.jrain.core.asm.handler.ASMPump;
import org.crap.jrain.core.util.PackageUtil;
import org.crap.jrain.core.util.StringUtil;
import org.crap.jrain.core.validate.DataBarScreen;
import org.crap.jrain.core.validate.annotation.BarScreen;
import org.crap.jrain.core.validate.exception.NoSuchServiceDefinitionException;
import org.crap.jrain.core.validate.security.param.EncryptDataParam;
import org.crap.jrain.core.validate.security.param.EncryptFlagParam;
import org.crap.jrain.core.validate.security.param.EncryptSourceParam;


/** 
 * ClassName: Boot 
 * 
 * @author Crap 
 * 
 * 2016年9月6日 上午11:31:05
 * 
 * @since JDK 1.8 
 * 
 * @descr 启动类
 */ 
public abstract class Boot {
	
	private static Logger log = LogManager.getLogger(Boot.class);
	
	public final static Map<String, ASMPump<Map<?,?>>> SERVER_MAP = new HashMap<String, ASMPump<Map<?,?>>>();
	
	protected Config config;
	
	protected Boot() {}
	
	public Boot(Config config) {
		this.config = config;
		init();
	}
	
	  
	/**  
	* @Title: init  
	* @Description: 初始化系统
	* @param @param config    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	protected void init() {
		Config config = getConfig();
		if(!StringUtil.isBlank(config.getSysConfig()))
			Resource.setSysConfig(config.getSysConfig());
		
		if(config.getResourcePath() != null && config.getResourcePath().length > 0)
			Resource.setPackagesToScan(config.getResourcePath());
		
		if(!StringUtil.isBlank(config.getResourceCharset()))
			Resource.setCharset(config.getResourceCharset());
		Resource.init();
		
		if(config.getKeyPairCollection() != null)
			DataBarScreen.setKPCOLLECTION(config.getKeyPairCollection());
		
		DataBarScreen.KPCOLLECTION.generate(config.getKeyPairNum());
		
		if(!StringUtil.isBlank(config.getEncryptDataKey()))
			EncryptDataParam.setDefaultKey(config.getEncryptDataKey());
		
		if(!StringUtil.isBlank(config.getEncryptFlagKey()))
			EncryptFlagParam.setDefaultKey(config.getEncryptFlagKey());
		
		if(!StringUtil.isBlank(config.getEncryptSourceKey()))
			EncryptSourceParam.setDefaultKey(config.getEncryptSourceKey());
		
		launch();
	}
	
	  
	/**  
	* @Title: launch  
	* @Description: Boot启动方法
	* @param @param packge 
	* @return void    返回类型  
	* @throws  
	*/  
	    
	protected void launch(String packge) {
		try {
			scanPump(packge);
//			scanBarScreen();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			log.error("scan error [@ServerMethod] from path:[{}]", packge);
		}
	}
	
	  
	/**  
	* @Title: launch  
	* @Description: Boot启动方法
	* @param     参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	public void launch() {
		String[] packges = this.config.getScanPackge();
		for (String packge : packges) {
			launch(packge);
		}
	}
	
	/** 
	 * scanServer:扫描Pump分发注解服务. 
	 * 用于main方法启动子系统.
	 * 通过ASM自动编译接口类并注册到spring.
	 * 
	 * @author Crap  
	 * @throws URISyntaxException 
	 * @throws ClassNotFoundException 
	 * @since JDK 1.8 
	 */  
	protected void scanPump(String packge) throws URISyntaxException {
		log.info("scan [@Pump] from path:[{}]", packge);
		
		List<String> classNames = PackageUtil.getClassName(packge);
		
		PumpAdapter adapter = new DataPumpAdapter();
		
		for (String className : classNames) {
			Class<?> serverClass = null;
			try {
				serverClass = Class.forName(className);
			} catch (ClassNotFoundException | NoClassDefFoundError e) {
				e.printStackTrace();
				continue;
			}
			if(!ASMPump.class.isAssignableFrom(serverClass) || !serverClass.isAnnotationPresent(Pump.class)){
				continue;
			}
			
			SERVER_MAP.putAll(adapter.resolve(serverClass));
			scanBarScreen(serverClass);
		}
		log.info("scan [@Pump] complete.");
	}
	
	/**  
	* @Title: scanBarScreen  
	* @Description: 扫描BarScreen校验注解服务  
	* @param     参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	protected void scanBarScreen(Class<?> serverClass) {
		Pump pump = serverClass.getAnnotation(Pump.class);
		
		if(pump == null)
			return;
		
		Method[] methods = serverClass.getDeclaredMethods();
		String module = pump.value().equals("")?serverClass.getSimpleName():pump.value();
		for (Method met : methods) {
			Pipe pipe = met.getAnnotation(Pipe.class);
			if(pipe == null)
				continue;
			
			String method = pipe.value().equals("")?met.getName():pipe.value();

			String mapping = (module+"$"+method).replace("/", "$").replace("\\", "$");
			
			BarScreen barScreen = met.getAnnotation(BarScreen.class);
			if(barScreen != null) {
				DataBarScreen.registBarScreen(mapping, barScreen);
				log.info("scan [@BarScreen] from path:[{}]", mapping);
			}
		}
	}
	
	  
	/**  
	* @Title: scanBarScreen  
	* @Description: 扫描BarScreen校验注解服务  
	* @param     参数  
	* @return void    返回类型  
	* @throws  
	*/  
	@Deprecated
	protected void scanBarScreen() {
		for (Map.Entry<String, ASMPump<Map<?,?>>> entry : SERVER_MAP.entrySet()) {
			try {
				Class<?> pump = entry.getValue().getClass();
				Method method = pump.getMethod("execute", Map.class);
				BarScreen barScreen = method.getAnnotation(BarScreen.class);
				if(barScreen != null) {
					DataBarScreen.registBarScreen(entry.getKey(), barScreen);
					log.info("scan [@BarScreen] from path:[{}]", entry.getKey());
				}
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param mapping
	 * @return ServerHandler.class
	 * 
	 * 通过mapping 获取对应的可执行Handler
	 * 
	 */
	public abstract ASMPump<Map<?,?>> getHandler(String mapping) throws NoSuchServiceDefinitionException;


	public Config getConfig() {
		return config;
	}


	public void setConfig(Config config) {
		this.config = config;
	}
	
	
}
