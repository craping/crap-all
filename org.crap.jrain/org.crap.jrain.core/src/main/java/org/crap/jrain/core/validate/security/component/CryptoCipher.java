package org.crap.jrain.core.validate.security.component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;

import org.crap.jrain.core.validate.security.Cipher;


  
/**  
* @ClassName: CryptoCipher  
* @Description: 默认密文处理类 
* @author Crap  
* @date 2017年11月5日  
*    
*/  
    
public class CryptoCipher extends Cipher {

	@Override
	protected byte[] generateAESKey(int size) {
		return AES.generateKey(size);
	}

	@Override
	protected byte[] encryptAES(byte[] key, String plaintext) {
		try {
			return AES.encrypt(URLEncoder.encode(plaintext, "UTF-8").getBytes("UTF-8"), key);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected String decryptAES(byte[] key, byte[] cipherblock) {
		return AES.decrypt(cipherblock, key);
	}

	@Override
	protected String decryptAES_JS(byte[] key, byte[] cipherblock) {
		return AES.decryptJS(cipherblock, key);
	}

	@Override
	protected byte[] decryptRSA(Key key, byte[] cipherblock) {
		try {
			return RSA.decrypt(key, cipherblock);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
}