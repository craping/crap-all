package org.crap.jrain.core.validate;

import java.util.Map;

import org.crap.jrain.core.util.StringUtil;


  
/**  
* @ClassName: MapValidation  
* @Description: Map数据屏障类
* @author Crap  
* @date 2017年11月3日  
*    
*/  
    
public class MapBarScreen extends DataBarScreen<Map<String, Object>> {
	
	private static MapBarScreen instance;
	
	private MapBarScreen() {}
	
	public static MapBarScreen getInstance() {
		if(instance == null) {
			synchronized(MapBarScreen.class){  
                if(instance == null){  
                    instance = new MapBarScreen();  
                }  
            }
		}
		return instance;
	}

	@Override
	protected Object getValue(Map<String, Object> params, String key) {
		return params.get(key);
	}

	@Override
	protected void setValue(Map<String, Object> params, String key, Object value) {
		params.put(key, value);
	}

	@Override
	public Map<String, Object> parseParams(String data) {
		return StringUtil.decodeParams(data);
	}

}
