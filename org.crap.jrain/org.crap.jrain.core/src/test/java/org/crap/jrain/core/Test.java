package org.crap.jrain.core;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import org.crap.jrain.core.asm.handler.ASMPump;
import org.crap.jrain.core.launch.Boot;
import org.crap.jrain.core.launch.DefaultBoot;
import org.crap.jrain.core.validate.exception.NoSuchServiceDefinitionException;
import org.crap.jrain.core.validate.security.component.AES;
import org.crap.jrain.core.validate.security.component.Coder;
import org.crap.jrain.core.validate.security.component.CryptoCipher;
import org.crap.jrain.core.validate.security.component.RSA;

import net.sf.json.JSONObject;

public class Test {
	
	public static void main(String[] args) throws Exception {
		Boot boot = new DefaultBoot(new Config("org.crap.jrain.core"));
		ASMPump<Map<?, ?>> pump = boot.getHandler("map$test");
		Map<?, ?> params = new HashMap<>();
		pump.execute(params);
		
		pump = boot.getHandler("json$test");
		params = JSONObject.fromObject("{}");
		pump.execute(params);
	}
	
	public static void main1(String[] args) throws Exception {
		KeyPair keyPair = RSA.generateKeyPair();
		byte[] clientAESKey = AES.generateKey(16);
		CryptoCipher c = new CryptoCipher();
		c.setClientAESKey(clientAESKey);
		
		String base64DataStr = Coder.encryptBASE64(AES.encrypt("中文测试123ASD".getBytes(), clientAESKey));
		
		clientAESKey = RSA.encrypt(keyPair.getPublic(), clientAESKey);
		String base64ClientAESKeyStr = Coder.encryptBASE64(clientAESKey);
		
		
		
		String base64Ciphertext = base64ClientAESKeyStr+"?"+base64DataStr;
		
		System.out.println(c.decrypt(base64Ciphertext, keyPair.getPrivate()));
		
	}
}
