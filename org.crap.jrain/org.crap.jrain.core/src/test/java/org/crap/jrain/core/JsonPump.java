package org.crap.jrain.core;

import org.crap.jrain.core.asm.annotation.Pipe;
import org.crap.jrain.core.asm.annotation.Pump;
import org.crap.jrain.core.asm.handler.DataPump;
import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.validate.annotation.BarScreen;

import com.alibaba.fastjson.JSONObject;

@Pump("json")
public class JsonPump extends DataPump<Object, Object> {
	
	@Pipe("test")
	@BarScreen(desc="API文档")
	public Errcode api (JSONObject params) {
		System.out.println("jsonPump");
		return new Result(Errors.OK);
	}
}
