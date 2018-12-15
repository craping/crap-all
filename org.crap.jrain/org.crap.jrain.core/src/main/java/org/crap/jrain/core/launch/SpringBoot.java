package org.crap.jrain.core.launch;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crap.jrain.core.Config;
import org.crap.jrain.core.asm.annotation.Pump;
import org.crap.jrain.core.asm.handler.ASMPump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

  
/**  
* @ClassName: SpringBoot
* @Description: 集成Spring启动的引导类  
* @author Crap  
* @date 2017年10月29日  
*    
*/  
    
public class SpringBoot extends Boot {
	
	private static Logger log = LogManager.getLogger(Boot.class);
	
	public static ApplicationContext context;

	public SpringBoot(ApplicationContext context, Config config) {
		this.config = config;
		SpringBoot.context = context;
		init();
	}
	
	@Override
	public void launch() {
		super.launch();
		registerServer();
	}

	/**  
	* @Title: registerServer  
	* @Description: 将ServerHandler托管给Spring管理 
	* @param     参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	protected void registerServer() {
		log.info("regist [@ServerMethod] to Spring beanFactory");
		
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
		
		Map<String, Object> beanMap = context.getBeansWithAnnotation(Pump.class);
		
		for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
			Class<?> resolveClass = entry.getValue().getClass().toString().contains("$$")? entry.getValue().getClass().getSuperclass():entry.getValue().getClass();
			
			if(!ASMPump.class.isAssignableFrom(resolveClass)){
				continue;
				//throw new ClassNotAssignableException(String.format("class [%s] can not assignable from %s", clazz.getName(), ServerHandler.class.getName()));
			}
			
			Map<String, String> property = new HashMap<String, String>();
			//获取父类中需要Spring注入的属性
			Field[] fields = resolveClass.getFields();
			for (Field field : fields) {
				Autowired autowired = field.getAnnotation(Autowired.class);
				if(autowired == null){
					Resource resource = field.getAnnotation(Resource.class);
					if(resource == null)
						continue;
					property.put(field.getName(), resource.name());
				}else{
					property.put(field.getName(), field.getName());
				}
			}
			

			for (Map.Entry<String, ? extends ASMPump<?>> entry1: SERVER_MAP.entrySet()) {
				
				log.info("regist bean info:[{}={}]", entry1.getKey(), entry1.getValue().getClass().getName());
				
				BeanDefinitionBuilder bean = BeanDefinitionBuilder.genericBeanDefinition(entry1.getValue().getClass());
				for (Map.Entry<String, String> p : property.entrySet()){
					bean.setScope("prototype");
					bean.addPropertyReference(p.getKey(), p.getValue());
				}
				beanFactory.registerBeanDefinition(entry1.getKey(), bean.getRawBeanDefinition());
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ASMPump<Map<?,?>> getHandler(String mapping) {
		return context.getBean(mapping, ASMPump.class);
	}
}
