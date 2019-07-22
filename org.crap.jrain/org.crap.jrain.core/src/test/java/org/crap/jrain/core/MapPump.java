package org.crap.jrain.core;

import java.util.Map;

import org.crap.jrain.core.asm.annotation.Pipe;
import org.crap.jrain.core.asm.annotation.Pump;
import org.crap.jrain.core.asm.handler.DataPump;
import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.validate.annotation.BarScreen;

@Pump("map")
public class MapPump extends DataPump<Object, Object> {
	
	@Pipe("test")
	@BarScreen(desc="API文档")
	public Errcode api (Map<?, ?> params) {
		System.out.println("mapPump");
		return new Result(Errors.OK);
	}
}
