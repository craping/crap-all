
package org.crap.jrain.core.asm.adapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crap.jrain.core.asm.ReflectMethodNotFoundException;
import org.crap.jrain.core.asm.annotation.Pipe;
import org.crap.jrain.core.asm.annotation.Pump;
import org.crap.jrain.core.asm.handler.ASMPump;

  
/**  
* @ClassName: DataPumpAdapter  
* @Description: 
* @author Crap  
* @date 2017年11月2日  
*    
*/  
    
public class DataPumpAdapter extends PumpAdapter {
	
	private final Logger log = LogManager.getLogger(DataPumpAdapter.class);
	
	@SuppressWarnings("unchecked")
	public Map<String, ASMPump<Map<?,?>>> resolve(Class<?> clazz) {
		
		Map<String, ASMPump<Map<?,?>>> pumpMap = new HashMap<String, ASMPump<Map<?,?>>>();
		
		Pump pump = clazz.getAnnotation(Pump.class);
		
		if(pump == null)
			return pumpMap;
		
		String module = pump.value().equals("")?clazz.getSimpleName():pump.value();
		
		Method[] methods = clazz.getDeclaredMethods();

		for (Method met : methods) {
			Pipe pipe = met.getAnnotation(Pipe.class);
			if(pipe == null)
				continue;
			
			String method = pipe.value().equals("")?met.getName():pipe.value();

			String mapping = (module+"$"+method).replace("/", "$").replace("\\", "$");
			
			log.info("regist [@Pipe] to Pump mapping[{}]", mapping);
			
			ASMPump<Map<?,?>> asmObj = null;
			try {
				asmObj = (ASMPump<Map<?,?>>) mete.generateSecure(clazz, met);
			} catch (ReflectMethodNotFoundException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
			log.info("generate secure [@Pipe] to [{}] done", asmObj.getClass().getName());
			
			pumpMap.put(mapping, asmObj);
		}
		
		return pumpMap;
	}
}
