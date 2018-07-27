package org.crap.jrain.core;

import org.crap.jrain.core.validate.security.DefaultKeyPairCollection;
import org.crap.jrain.core.validate.security.KeyPairCollection;

  
/**  
* @ClassName: Config  
* @Description: Jrain主配置类
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public class Config {
	
	  
	/**  
	* @Fields {@Pump} 扫描路径
	*/  
	private String[] scanPackge;
	
	  
	/**  
	* @Fields 系统配置文件名
	*/  
	    
	private String sysConfig = "config";
	
	  
	/**  
	* @Fields 错误集资源文件语言包路径
	*/  
	    
	private String[] resourcePath = {"config.language"};
	
	  
	/**  
	* @Fields 资源文件编码
	*/  
	    
	private String resourceCharset = "utf-8";
	
	  
	/**  
	* @Fields RSA密钥对容器
	*/  
	    
	private KeyPairCollection keyPairCollection = new DefaultKeyPairCollection();

	  
	/**  
	* @Fields 生成RSA密钥对数量
	*/  
	    
	private int keyPairNum = 100;
	
	  
	/**  
	* @Fields 当消息为安全模式 数据的参数名
	*/  
	    
	private String encryptDataKey = "encrypt_data";
	
	  
	/**  
	* @Fields 当消息为安全模式 RSA密钥标记的参数名
	*/  
	    
	private String encryptFlagKey = "encrypt_flag";
	
	  
	/**  
	* @Fields 当消息为安全模式 RSA加密源的参数名
	*/  
	    
	private String encryptSourceKey = "encrypt_source";
	
	public Config(String... scanPackge) {
		this.scanPackge = scanPackge;
	}
	
	public String[] getScanPackge() {
		return scanPackge;
	}

	public void setScanPackge(String[] scanPackge) {
		this.scanPackge = scanPackge;
	}

	public String[] getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String[] resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getResourceCharset() {
		return resourceCharset;
	}

	public void setResourceCharset(String resourceCharset) {
		this.resourceCharset = resourceCharset;
	}

	public KeyPairCollection getKeyPairCollection() {
		return keyPairCollection;
	}

	public void setKeyPairCollection(KeyPairCollection keyPairCollection) {
		this.keyPairCollection = keyPairCollection;
	}

	public int getKeyPairNum() {
		return keyPairNum;
	}

	public void setKeyPairNum(int keyPairNum) {
		this.keyPairNum = keyPairNum;
	}

	public String getSysConfig() {
		return sysConfig;
	}

	public void setSysConfig(String sysConfig) {
		this.sysConfig = sysConfig;
	}

	public String getEncryptDataKey() {
		return encryptDataKey;
	}

	public void setEncryptDataKey(String encryptDataKey) {
		this.encryptDataKey = encryptDataKey;
	}

	public String getEncryptFlagKey() {
		return encryptFlagKey;
	}

	public void setEncryptFlagKey(String encryptFlagKey) {
		this.encryptFlagKey = encryptFlagKey;
	}

	public String getEncryptSourceKey() {
		return encryptSourceKey;
	}

	public void setEncryptSourceKey(String encryptSourceKey) {
		this.encryptSourceKey = encryptSourceKey;
	}
	
	
}
