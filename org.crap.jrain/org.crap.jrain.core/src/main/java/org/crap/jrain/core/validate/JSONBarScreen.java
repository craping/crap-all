package org.crap.jrain.core.validate;

import org.crap.jrain.core.util.StringUtil;

import net.sf.json.JSONObject;

  
/**  
* @ClassName: JSONValidation  
* @Description: JSON数据屏障类
* @author Crap  
* @date 2017年11月3日  
*    
*/  
    
public class JSONBarScreen extends DataBarScreen<JSONObject> {
	
	private static JSONBarScreen instance;
	
	private JSONBarScreen() {};
	
	public static JSONBarScreen getInstance() {
		if(instance == null) {
			synchronized(JSONBarScreen.class){  
                if(instance == null){  
                    instance = new JSONBarScreen();  
                }  
            }
		}
		return instance;
	}

	@Override
	protected Object getValue(JSONObject params, String key) {
		if(key == null)
			return null;
		String[] propertys = StringUtil.split(key, ".");
		for (int i = 0; i < propertys.length; i++) {
			String p = propertys[i];
			
			if(!params.has(p))
				break;
			
			if(i != propertys.length-1){
				params = params.getJSONObject(p);
			}else{
				return params.get(key);
			}
		}
		return null;
	}
	
	@Override
	protected void setValue(JSONObject params, String key, Object value) {
		if(key == null)
			return;
		String[] propertys = StringUtil.split(key, ".");
		for (int i = 0; i < propertys.length; i++) {
			String p = propertys[i];
			
			if(!params.has(p))
				break;
			
			if(i != propertys.length-1){
				params = params.getJSONObject(p);
			}else{
				params.put(p, value);
			}
		}
	}

	@Override
	public JSONObject parseParams(String data) {
		return JSONObject.fromObject(data);
	}
}
