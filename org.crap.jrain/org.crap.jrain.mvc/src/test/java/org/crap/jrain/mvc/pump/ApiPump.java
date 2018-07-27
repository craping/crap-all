package org.crap.jrain.mvc.pump;

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
import org.crap.jrain.core.validate.annotation.BarScreen;
import org.crap.jrain.core.validate.annotation.Parameter;
import org.crap.jrain.core.validate.support.param.DateTimeParam;
import org.crap.jrain.core.validate.support.param.NumberParam;
import org.crap.jrain.mvc.param.Constant;
import org.crap.jrain.mvc.param.DataStatusEParam;

@Pump("api")
public class ApiPump extends DataPump<Map<String, Object>> {
	
	@Pipe("apiDocument")
	@BarScreen(desc="API文档")
	public Errcode api (Map<String, Object> params) {
		try {
			String info = PackageUtil.apiResolve("org.crap.jrain.mvc.pump", "http://127.0.0.1:8080");
			return new DataResult(Errors.OK, new Data(info));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Result(Errors.OK);
	}
	
	@Pipe("test")
	@BarScreen(
		desc="测试接口", 
		params={
			@Parameter(value="beginTime", required = true, type = DateTimeParam.class, desc="开始时间"),
			@Parameter(value="endTime", required = true, type = DateTimeParam.class, desc="结束时间"),
			@Parameter(value="accountName", required=false, desc="下级用户名"),
			@Parameter(value="rate", type=NumberParam.class, required=false, min="5", desc="返点率"),
			@Parameter(value="includeSub", type=DataStatusEParam.class, defaultValue=Constant.System.DataStatus.NR, desc="包含下级")
		}
	)
	public Errcode method (Map<String, Object> params) {
		return new Result(Errors.OK);
	}
}
