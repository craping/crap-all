package org.crap.jrain.core.validate.security;

import java.security.Key;
import java.security.PrivateKey;

import org.crap.jrain.core.validate.security.component.AES;
import org.crap.jrain.core.validate.security.component.Coder;


  
/**  
* @ClassName: Cipher  
* @Description: 密文处理类
* 数据传输密文模式分两种
* 加密请求内容格式：BASE64(RSA加密后的AES密钥 + 请求的AES密钥加密后的密文)
* 加密返回格式：BASE64(请求的AES密钥加密后的密文)
* @author Crap  
* @date 2017年11月5日  
*    
*/  
    
public abstract class Cipher{
	
	private byte[] clientAESKey;
	
	public byte[] getClientAESKey() {
		return clientAESKey;
	}

	public void setClientAESKey(byte[] clientAESKey) {
		this.clientAESKey = clientAESKey;
	}

	/**
     * 加密数据
     * 
     * @param plaintext 明文字符串
     * @return 密文字符串
     */
    public String encrypt(String plaintext) {
    	try{
			String ciphertext = Coder.encryptBASE64(encryptAES(this.clientAESKey, plaintext));
			return ciphertext.replaceAll("\r|\n", "");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 加密数据
     * 
     * @param plaintext 明文字符串
     * @return 密文字符串
     */
    public String encryptSafe(String plaintext) {
    	try{
	    	byte[] key = generateAESKey(16);
	    	
	    	String serverAESKey = Coder.encryptBASE64(AES.encrypt(key, this.clientAESKey));
			String ciphertext = Coder.encryptBASE64(encryptAES(key, plaintext));
			
			return (serverAESKey + "?" + ciphertext).replaceAll("\r|\n", "");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 解密数据
     * 
     * @param key 密钥(公钥加密则传私钥饥解密,私钥加密则传公钥解密)
     * @param ciphertext 密文的字符串(base64)
     * @return 明文字符串
     */
    public String decrypt(String base64Ciphertext, PrivateKey privateKey) {
    	try{
	    	String[] ciphertextArr = base64Ciphertext.split("[?]");
			String base64ClientAESKeyStr = ciphertextArr[0];
			String base64DataStr = ciphertextArr[1];
			
			byte[] clientAESKey = decryptRSA(privateKey, Coder.decryptBASE64(base64ClientAESKeyStr));
			this.clientAESKey = clientAESKey;
			
			String ciphertext = decryptAES(clientAESKey, Coder.decryptBASE64(base64DataStr));
			
			return ciphertext;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * 解密数据
     * 
     * @param key 密钥(公钥加密则传私钥饥解密,私钥加密则传公钥解密)
     * @param ciphertext 密文的字符串(base64)
     * @return 明文字符串
     */
    public String decryptJS(String base64Ciphertext, PrivateKey privateKey) {
    	try{
	    	String[] ciphertextArr = base64Ciphertext.split("[?]");
			String base64ClientAESKeyStr = ciphertextArr[0];
			String base64DataStr = ciphertextArr[1];
			
			byte[] clientAESKey = decryptRSA(privateKey, Coder.decryptBASE64(base64ClientAESKeyStr));
			
			clientAESKey = new String(clientAESKey, "UTF-8").getBytes("ISO-8859-1");
			this.clientAESKey = clientAESKey;
			
			String ciphertext = decryptAES_JS(clientAESKey, Coder.decryptBASE64(base64DataStr));
			return ciphertext;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    protected abstract byte[] generateAESKey(int size);
    
    protected abstract byte[] encryptAES(byte[] key, String plaintext);
    
    protected abstract String decryptAES(byte[] key, byte[] cipherblock);
    
    protected abstract String decryptAES_JS(byte[] key, byte[] cipherblock);
    
    protected abstract byte[] decryptRSA(Key key, byte[] cipherblock);
}