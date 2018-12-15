package org.crap.jrain.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/** 
 * @project Crap
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 * @email 422655094@qq.com
 * 
 */
public class ClassUtil {
	
	
	public static final Logger log = LogManager.getLogger(ClassUtil.class);
	/**
     * 
     * <p>
     * 比较参数类型是否一致
     * </p>
     * 
     * @param types
     *            asm的类型({@link Type})
     * @param clazzes
     *            java 类型({@link Class})
     * @return
     */
    private static boolean sameType(Type[] types, Class<?>[] clazzes) {
        // 个数不同
        if (types.length != clazzes.length) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(clazzes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * <p>
     * 获取方法的参数名
     * </p>
     * 
     * @param m
     * @return String[]
     */
    public static String[] getMethodParamNames(final Method m) {
        final String[] paramNames = new String[m.getParameterTypes().length];
        //final String n = m.getDeclaringClass().getName();
        ClassReader cr = null;
        try {
            cr = new ClassReader(loadClass(m.getDeclaringClass()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cr.accept(new ClassVisitor(Opcodes.ASM7) {
            @Override
            public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
                final Type[] args = Type.getArgumentTypes(desc);
                // 方法名相同并且参数个数以及类型相同
				if (name.equals(m.getName()) && sameType(args, m.getParameterTypes())) {
					MethodVisitor v = super.visitMethod(access, name, desc, signature, exceptions);
	                return new MethodVisitor(Opcodes.ASM7, v) {
	                    @Override
	                    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
	                        int i = index - 1;
	                        // 如果是静态方法，则第一就是参数
	                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
	                        if (Modifier.isStatic(m.getModifiers())) {
	                            i = index;
	                        }
	                        if (i >= 0 && i < paramNames.length) {
	                            paramNames[i] = name;
	                        }
	                        super.visitLocalVariable(name, desc, signature, start, end, index);
	                    }
	                };
                }
                
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 0);
        return paramNames;
    }
    
    
    /**
     * 
     * <p>
     * 获取方法的参数名
     * </p>
     * 
     * @param m
     * @return String[]
     */
    public static Map<String, String> getInParams(final Method m) {
        final Map<String, String> inParams = new HashMap<String, String>();
        
       //final String n = m.getDeclaringClass().getName();

        ClassReader cr = null;
        try {
            cr = new ClassReader(loadClass(m.getDeclaringClass()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cr.accept(new ClassVisitor(Opcodes.ASM7) {
	        @Override
	        public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
	            final Type[] args = Type.getArgumentTypes(desc);
	            // 方法名相同并且参数个数以及类型相同
	            if (name.equals(m.getName()) && sameType(args, m.getParameterTypes())) {
					MethodVisitor v = super.visitMethod(access, name, desc, signature, exceptions);
	                return new MethodVisitor(Opcodes.ASM7, v) {
	                    @Override
	                    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
	                        int i = index - 1;
	                        // 如果是静态方法，则第一就是参数
	                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
	                        if (Modifier.isStatic(m.getModifiers())) {
	                            i = index;
	                        }
	                        if (i >= 0 && i < m.getParameterTypes().length) {
	                        	inParams.put(name, desc);
	                        }
	                        super.visitLocalVariable(name, desc, signature, start, end, index);
	                    }
	                };
                }
                
                return super.visitMethod(access, name, desc, signature, exceptions);
	        }
        }, 0);
        return inParams;
    }
    
    public static String getOutParam(Method m) {
    	String outParam = m.getReturnType().getName();
    	outParam = outParam.equals("void")?"V":"L" + outParam.replace(".", "/") + ";";
    	return outParam;
    }
    
    
    public static InputStream loadClass(Class<?> clazz) {
    	URL url = clazz.getResource("/");
    	log.info("ClassUtil.loadClass classpath0: {}", clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
    	log.info("ClassUtil.loadClass classpath1: {}", url);
    	InputStream in = null;
//    	if(url != null){
//			try {
//				File file = new File(getClasspath(clazz));
//				in = new FileInputStream(file);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//    	}else{
    		StringBuffer sb = new StringBuffer();
        	sb.append(clazz.getPackage().getName().replace(".", "/"))
        	.append("/")
        	.append(clazz.getSimpleName())
        	.append(".class");
    		in = ClassUtil.class.getClassLoader().getResourceAsStream(sb.toString());
//    	}
		return in;
    }
    
    public static String getClasspath(Class<?> clazz) {
    	return getClasspath(clazz, null);
    }
    
    public static String getClasspath(Class<?> clazz, String classSimpleName) {
    	URL url = clazz.getResource("/");
    	log.info("ClassUtil.getClasspath classpath0: {}", clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
    	log.info("ClassUtil.getClasspath classpath1: {}", url);
    	try {
	    	if(url != null){
    			StringBuffer classpath = new StringBuffer(url.toURI().getPath());
    			classpath.append(clazz.getPackage().getName().replace(".", "/"))
    	    	.append("/")
    	    	.append(classSimpleName==null?clazz.getSimpleName():classSimpleName)
    	    	.append(".class");
    			
    	    	return classpath.toString();
	    	}else{
	    		StringBuffer classpath = new StringBuffer();
				classpath.append(clazz.getPackage().getName().replace(".", "/"))
		    	.append("/")
		    	.append(clazz.getSimpleName())
		    	.append(".class");
				url = ClassUtil.class.getClassLoader().getResource(classpath.toString());
				String path = url.getFile().replace(".jar!", "");
				
		    	return classSimpleName==null?path:path.replace(clazz.getSimpleName(), classSimpleName);
	    	}
    	} catch(Exception e){
    		e.printStackTrace();
			return "";
		}
    }
    
    
	public static Field[] getDeclaredFields(Class<?> cls){
		Field[] fields = cls.getDeclaredFields();
		if(!cls.getSuperclass().equals(Object.class)){
			Field[] temp = fields;
			Field[] superFields = getDeclaredFields(cls.getSuperclass());
			fields = new Field[superFields.length + fields.length];
			
			System.arraycopy(superFields, 0, fields, 0, superFields.length);
			System.arraycopy(temp, 0, fields, superFields.length, temp.length);
		}
		return fields;
	}
	
	public static Field getDeclaredField(Class<?> cls, String name) throws NoSuchFieldException, SecurityException {
		Field field = null;
		try {
			field = cls.getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {
			if(!cls.getSuperclass().equals(Object.class)){
				field = getDeclaredField(cls.getSuperclass(), name);
			} else {
				throw e;
			}
		}
		return field;
	}
	
	public static Method[] getDeclaredMethods(Class<?> cls){
		Method[] methods = cls.getDeclaredMethods();
		if(!cls.getSuperclass().equals(Object.class)){
			Method[] temp = methods;
			Method[] superMethods = getDeclaredMethods(cls.getSuperclass());
			methods = new Method[superMethods.length + methods.length];
			
			System.arraycopy(superMethods, 0, methods, 0, superMethods.length);
			System.arraycopy(temp, 0, methods, superMethods.length, temp.length);
		}
		return methods;
	}
	
	public static Method getDeclaredMethod(Class<?> cls, String name) throws NoSuchMethodException {
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if(method.getName().equals(name))
				return method;
		}
		if(!cls.getSuperclass().equals(Object.class))
			return getDeclaredMethod(cls.getSuperclass(), name);
		throw new NoSuchMethodException(String.format("%s.%s()", cls.getName(), name));
	}
	
	public static Class<?>[] getDeclaredException(Class<?> cls, String name) throws NoSuchMethodException {
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if(method.getName().equals(name))
				return method.getExceptionTypes();
		}
		if(!cls.getSuperclass().equals(Object.class))
			return getDeclaredException(cls.getSuperclass(), name);
		throw new NoSuchMethodException(String.format("%s.%s()", cls.getName(), name));
	}
    
    public static void main(String[] args) {
    	/*Annotation[] as = ServerService.class.getAnnotations();
    	for (Annotation annotation : as) {
    		System.out.println(annotation);
		}
    	System.out.println(ServerService.class.getAnnotation(Service.class));*/
		/*Method[] md = ServerService.class.getDeclaredMethods();
		for (Method method : md) {
			System.out.println(method.getReturnType().getName());
			//System.out.println(Arrays.toString(getMethodParamNames(method)));
		}*/
	}
}
