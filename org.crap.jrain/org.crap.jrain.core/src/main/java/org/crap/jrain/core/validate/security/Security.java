package org.crap.jrain.core.validate.security;

  
/**  
* @ClassName: Security  
* @Description: 数据安全接口
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public interface Security {
	
	ThreadLocal<Cipher> LOCAL_CIPHER = new ThreadLocal<Cipher>();
	
	Cipher createCipher();
	
	public static Cipher getCipher() {
		return LOCAL_CIPHER.get();
	}
	
	public static void setCipher(Cipher cipher) {
		LOCAL_CIPHER.set(cipher);
	}
}
