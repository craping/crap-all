package org.crap.jrain.core.validate;

  
/**  
* @ClassName: DefaultBarScreenFactory  
* @Description: 默认的屏障工厂
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public class DefaultBarScreenFactory implements BarScreenFactory {
	
	@SuppressWarnings("rawtypes")
	public DataBarScreen createDataBarScreen(DataType type) {
		switch (type) {
		case JSON:
			return JSONBarScreen.getInstance();
		default :
			return MapBarScreen.getInstance();
		}
	}
}
