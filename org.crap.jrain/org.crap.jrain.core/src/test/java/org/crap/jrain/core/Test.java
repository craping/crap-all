package org.crap.jrain.core;

import java.util.HashMap;
import java.util.Map;

import org.crap.jrain.core.asm.handler.ASMPump;
import org.crap.jrain.core.launch.Boot;
import org.crap.jrain.core.launch.DefaultBoot;
import org.crap.jrain.core.validate.exception.NoSuchServiceDefinitionException;

import net.sf.json.JSONObject;

public class Test {
	
	public static void main(String[] args) throws NoSuchServiceDefinitionException {
		Boot boot = new DefaultBoot(new Config("org.crap.jrain.core"));
		ASMPump<Map<?, ?>> pump = boot.getHandler("map$test");
		Map<?, ?> params = new HashMap<>();
		pump.execute(params);
		
		pump = boot.getHandler("json$test");
		params = JSONObject.fromObject("{}");
		pump.execute(params);
	}
}
