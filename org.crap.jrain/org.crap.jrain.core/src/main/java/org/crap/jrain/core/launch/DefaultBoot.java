package org.crap.jrain.core.launch;

import java.util.Map;

import org.crap.jrain.core.Config;
import org.crap.jrain.core.asm.handler.ASMPump;
import org.crap.jrain.core.validate.exception.NoSuchServiceDefinitionException;

  
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

	@Override
	public ASMPump<Map<?,?>> getHandler(String mapping) throws NoSuchServiceDefinitionException {
		
		ASMPump<Map<?,?>> pump = SERVER_MAP.get(mapping);
		
		if(pump == null)
			throw new NoSuchServiceDefinitionException(mapping);
		
		return pump;
	}
}
