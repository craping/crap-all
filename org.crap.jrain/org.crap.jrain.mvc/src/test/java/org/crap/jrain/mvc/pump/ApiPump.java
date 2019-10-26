package org.crap.jrain.mvc.pump;

import java.net.InetSocketAddress;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.crap.jrain.core.asm.annotation.Pipe;
import org.crap.jrain.core.asm.annotation.Pump;
import org.crap.jrain.core.asm.handler.DataPump;
import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;
import org.crap.jrain.core.bean.result.criteria.Data;
import org.crap.jrain.core.bean.result.criteria.DataResult;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.util.PackageUtil;
import org.crap.jrain.core.validate.DataBarScreen;
import org.crap.jrain.core.validate.annotation.BarScreen;
import org.crap.jrain.core.validate.security.component.Coder;
import org.crap.jrain.mvc.HttpServer;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;

@Pump("api")
public class ApiPump extends DataPump<FullHttpRequest, Channel> {
	
	@Pipe("apiDocument")
	@BarScreen(desc="API文档")
	public Errcode api (JSONObject params) {
		try {
			String info = PackageUtil.apiResolve("plan.server.pump", "http://127.0.0.1:"+HttpServer.PORT);
			return new DataResult(Errors.OK, new Data(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(Errors.OK);
	}
	
	@Pipe("getPublicKey")
	@BarScreen(desc="随机获取公钥")
	public Errcode publicKey (JSONObject params) {
		InetSocketAddress insocket = (InetSocketAddress) getResponse().remoteAddress();
		System.out.println("IP:"+insocket.getAddress().getHostAddress());
		int index = (int)(Math.random()*DataBarScreen.KPCOLLECTION.getTotal());
		RSAPublicKey publicKey = (RSAPublicKey)DataBarScreen.KPCOLLECTION.get(index).getPublic();

		Map<String, Object> key = new HashMap<>();
		key.put("id", index);
		key.put("n", publicKey.getModulus().toString());
		key.put("e", publicKey.getPublicExponent().toString());
		try {
			key.put("publicKey", Coder.encryptBASE64(publicKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DataResult(Errors.OK, new Data(key));
	}
}
