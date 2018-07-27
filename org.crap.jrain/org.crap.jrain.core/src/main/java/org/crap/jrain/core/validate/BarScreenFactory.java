package org.crap.jrain.core.validate;

  
/**  
* @ClassName: BarScreenFactory  
* @Description: 数据屏障工厂接口
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public interface BarScreenFactory {
	
	@SuppressWarnings("rawtypes")
	public DataBarScreen createDataBarScreen(DataType type);
	
}
