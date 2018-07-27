package org.crap.jrain.mvc;

import java.util.HashMap;
import java.util.Map;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.launch.Boot;
import org.crap.jrain.core.validate.BarScreenFactory;
import org.crap.jrain.core.validate.DataBarScreen;
import org.crap.jrain.core.validate.DataType;
import org.crap.jrain.core.validate.DefaultBarScreenFactory;
import org.crap.jrain.core.validate.exception.ValidationException;

  
/**  
* @ClassName: Treatment  
* @Description: MVC的中央消息处理类，整个流程为 validate 和 execute
* @author Crap  
* @date 2017年11月10日  
*  
* @param <TRequest>
* @param <TResponse>  
*/  
    
public abstract class Treatment<TRequest, TResponse> {
	
	protected Boot boot;
	
	protected Map<String, Render<TRequest, TResponse>> renders = new HashMap<>();
	
	protected BarScreenFactory bsFactory = new DefaultBarScreenFactory();
	
	public Treatment(Boot boot) {
		this.boot = boot;
	}
	
	  
	/**  
	* @Title: process  
	* @Description: 请求消息主程序处理方法
	* @param @param mapping
	* @param @param request
	* @param @param response    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	public void process(String mapping, TRequest request, TResponse response) {
		
		DataType dataType = getDataType(request);
		
		String format = getFormat(request);
		
		@SuppressWarnings("unchecked")
		DataBarScreen<Map<?, ?>> dBS = bsFactory.createDataBarScreen(dataType);
		
		Map<?, ?> params = getRawParams(request, dataType);
		
		Render<TRequest, TResponse> render = renders.get(format);
		if(render == null)
			render = getDefaultRender();
		
		try {
			Errcode errcode = dBS.validate(params, mapping);
			if(!errcode.equals(Errors.OK)){
				render.render(errcode, request, response);
				return;
			}
			
			Errcode errcodeResult = boot.getHandler(mapping).execute(params);
			if(render != null)
				render.render(errcodeResult, request, response);
		} catch (ValidationException e) {
			e.printStackTrace();
			render.render(e.toResult(), request, response);
			return;
		}
	}
	
	  
	/**  
	* @Title: getFormat  
	* @Description: 从request中获取 url中的format 参数
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws  
	*/  
	    
	protected abstract String getFormat(TRequest request);
	
	  
	/**  
	* @Title: getRawParams  
	* @Description: 获取请求中的原始参数
	* @param @param request
	* @param @param dataType
	* @param @return    参数  
	* @return Map<?,?>    返回类型  
	* @throws  
	*/  
	    
	protected abstract Map<?, ?> getRawParams(TRequest request, DataType dataType);
	
	  
	/**  
	* @Title: getDataType  
	* @Description: 通过获取请求content-type 来判断请求的数据类型
	* 				{@BarScreenFactory}工厂通过判断 DataType 获取对应{@DataBarScreen}来校验
	* @param @param request
	* @param @return    参数  
	* @return DataType    返回类型  
	* @throws  
	*/  
	    
	protected abstract DataType getDataType(TRequest request);
	
	  
	/**  
	* @Title: getDefaultRender  
	* @Description: 获取默认的返回渲染
	* @param @return    参数  
	* @return Render<TRequest,TResponse>    返回类型  
	* @throws  
	*/  
	    
	protected abstract Render<TRequest, TResponse> getDefaultRender();
	
	public void addRender(Render<TRequest, TResponse> render) {
		renders.put(render.getFormat(), render);
	}

	public Boot getBoot() {
		return boot;
	}

	public void setBoot(Boot boot) {
		this.boot = boot;
	}

	public Map<String, Render<TRequest, TResponse>> getRenders() {
		return renders;
	}

	public void setRenders(Map<String, Render<TRequest, TResponse>> renders) {
		this.renders = renders;
	}

	public BarScreenFactory getBsFactory() {
		return bsFactory;
	}

	public void setBsFactory(BarScreenFactory bsFactory) {
		this.bsFactory = bsFactory;
	}
}
