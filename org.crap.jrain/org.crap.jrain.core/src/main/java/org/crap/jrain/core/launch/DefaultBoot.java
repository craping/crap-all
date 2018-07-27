package org.crap.jrain.core.launch;

import java.util.Map;

import org.crap.jrain.core.Config;
import org.crap.jrain.core.asm.handler.ASMPump;

  
/**  
* @ClassName: DefaultBoot  
* @Description: 默认引导类  
* @author Crap  
* @date 2017年10月29日  
*    
*/  
    
public class DefaultBoot extends Boot {
	
	public DefaultBoot(Config config) {
		super(config);
	}

	public ASMPump<Map<?,?>> getHandler(String mapping) {
		return SERVER_MAP.get(mapping);
	}
}
