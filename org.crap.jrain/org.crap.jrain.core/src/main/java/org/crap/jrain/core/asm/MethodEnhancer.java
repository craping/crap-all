package org.crap.jrain.core.asm;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crap.jrain.core.asm.annotation.Separate;
import org.crap.jrain.core.util.ClassUtil;
import org.crap.jrain.core.util.FileUtil;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MethodEnhancer implements Opcodes {
	
	private final Logger log = LogManager.getLogger(MethodEnhancer.class);
	
	/**
	 * @param cls
	 * @param executeMethod
	 * @return
	 * @throws ReflectMethodNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public Object generateSecure(Class<?> cls, Method executeMethod) throws ReflectMethodNotFoundException, NoSuchMethodException, SecurityException {
		Map<String, String> inParams = ClassUtil.getInParams(executeMethod);
		String outParam =  ClassUtil.getOutParam(executeMethod);
		List<String> exceptionArr = new ArrayList<String>();
		String[] exceptions = null;
		
		Class<?>[] exClass = executeMethod.getExceptionTypes();
		if(exClass != null && exClass.length > 0){
			for (Class<?> exCls : exClass) {
				exceptionArr.add(exCls.getName().replace(".", "/"));
			}
			exceptions = new String[exceptionArr.size()];
			exceptionArr.toArray(exceptions);
		}
		
		return generateSecure(cls, executeMethod.getName(), inParams, outParam, null, exceptions);
	}
	
	public Object generateSecure(Class<?> cls, String executeMethodName, Map<String, Class<?>> inParams, Class<?> outParam, String signature, String[] exceptions) throws ReflectMethodNotFoundException, NoSuchMethodException, SecurityException {
		Map<String, String> strMap = new HashMap<String, String>();
		for (Map.Entry<String, Class<?>> entry : inParams.entrySet()) {
			strMap.put(entry.getKey(), "L" + entry.getValue().getName().replace(".", "/") + ";");
		}
		
		String strOut = outParam == null ? "V" : "L" + outParam.getName().replace(".", "/") + ";";
		return generateSecure(cls, executeMethodName, strMap, strOut, signature, exceptions);
	}
	
	public Object generateSecure(Class<?> cls, String executeMethodName, Map<String, String> inParams, String outParam, String signature, String[] exceptions) throws ReflectMethodNotFoundException, NoSuchMethodException {
		// 新类的全称
		String secureClassName = cls.getName() + "$" + executeMethodName;
		String secureSimpleClassName = cls.getSimpleName() + "$" + executeMethodName;
		
		Separate separate = cls.getAnnotation(Separate.class);
		if(separate == null)
			throw new ReflectMethodNotFoundException(cls);
		
		if(ClassUtil.getDeclaredMethod(cls, separate.value()) == null)
			return null;
		
		try {
			byte[] classData = dump(cls.getName(), separate.value(), executeMethodName, secureClassName, inParams, outParam, signature, exceptions);
			
			StringBuffer oldClasspath = new StringBuffer();
			oldClasspath.append(cls.getPackage().getName().replace(".", "/"))
	    	.append("/")
	    	.append(cls.getSimpleName())
	    	.append(".class");
			ClassUtil.class.getClassLoader().getResource(oldClasspath.toString()).getPath();
			String classpath = URLDecoder.decode(ClassUtil.class.getClassLoader().getResource(oldClasspath.toString()).getFile(), "utf-8");
			String url = classpath.split(".jar!")[0].replace("file:/", "").split(cls.getName()+".class")[0];
			String path = (url+"/"+oldClasspath.toString().replace(cls.getSimpleName(), secureSimpleClassName)).replaceAll("//", "/");
			String dir = path.substring(0, path.lastIndexOf("/"));
			
			log.info("classpath: {}", classpath);
			log.info("url: {}", url);
			log.info("path: {}", path);
			log.info("dir: {}", dir);
			
			FileUtil.createDirs(dir, true);
			FileUtil.createFile(classData, path);
			
			
			URL cp = new File(url).toURI().toURL();
			URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			URL[] urls = loader.getURLs();
			
			boolean hasClassPath = false;
			
			for (URL u : urls) {
				if(u.equals(cp)) {
					hasClassPath = true;
					break;
				}
			}
			if(!hasClassPath) {
				Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
				add.setAccessible(true);
				add.invoke(loader, new Object[] {cp});
			}
			return Class.forName(secureClassName).getDeclaredConstructor().newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("load [" + secureClassName + "] error", ex);
			return null;
		}
	}

	private byte[] dump(String cls, String superMethodName, String executeMethodName, String newcls, Map<String, String> inParams, String outParam, String signature, String[] exceptions) throws Exception {

		String inParamStr = "";

		for (Map.Entry<String, String> entry : inParams.entrySet()) {
			inParamStr += entry.getValue();
		}
		Method[] methods = Class.forName(cls).getMethods();
		Method method = null;
		for (Method m : methods) {
			if(m.getName().equals(executeMethodName)){
				method = m;
				break;
			}
		}
		
		// 父类的名称
		cls = cls.replace(".", "/");
		// 子类的名称
		newcls = newcls.replace(".", "/");
		// 构建 ASM 工具类对象
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		MethodVisitor mv;
		AnnotationVisitor av;
		
		cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, newcls, null, cls, null);
		cw.visitSource(newcls.substring(newcls.lastIndexOf("/")+1, newcls.length()) + ".java", null);
		{ // 构造方法，每个类必须
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", signature, exceptions);// 方法名称
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(7, l0);
			mv.visitVarInsn(ALOAD, 0);// 加载 this 对象
			mv.visitMethodInsn(INVOKESPECIAL, cls, "<init>", "()V", false);// 调用父类的
																	// init 方法
			mv.visitInsn(RETURN);// 返回
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "L" + newcls + ";", null, l0, l1, 0);// 本地方法变量
			mv.visitMaxs(1, 1);// 设置本地堆栈
			mv.visitEnd();
		}

		{
			// superMethod方法，实现子类的相关方法
			mv = cw.visitMethod(ACC_PUBLIC, superMethodName, "(" + inParamStr + ")" + outParam, signature, exceptions);// 方法名称
			//构造事务注解
			{
				Annotation[] as = method.getAnnotations();
				for (Annotation a : as) {
					Method[] aMethods = a.getClass().getDeclaredMethods();
					
					String annotationClsStr = a.toString();
					av = mv.visitAnnotation("L"+annotationClsStr.substring(1, annotationClsStr.indexOf("(")).replace(".", "/")+";", true);
					for (Method m : aMethods) {
						writeAnnotation(av, m, a);
					}
					av.visitEnd();
				}
			}
			
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(11, l0);
			mv.visitVarInsn(ALOAD, 0);// 加载 this 对象
			for (int i = 1; i <= inParams.size(); i++) {
				mv.visitVarInsn(ALOAD, i);// 加载 num 参数
			}

			mv.visitMethodInsn(INVOKEVIRTUAL, cls, executeMethodName, "(" + inParamStr + ")" + outParam, false);// 调用父类的方法
			mv.visitInsn(ARETURN); // 返回对象的引用

			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "L" + newcls + ";", null, l0, l1, 0);// 本地方法变量

			int i = 1;
			for (Map.Entry<String, String> entry : inParams.entrySet()) {
				mv.visitLocalVariable(entry.getKey(), entry.getValue(), null, l0, l1, i);// 本地方法变量
				i++;
			}

			mv.visitMaxs(2, 2);// 设置本地堆栈
			mv.visitEnd();
		}
		if(!"Ljava/util/Map;".equals(inParamStr)){
			mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, superMethodName, "(Ljava/util/Map;)"+outParam, null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(1, l0);
			mv.visitVarInsn(ALOAD, 0);
			for (int i = 1; i <= inParams.size(); i++) {
				mv.visitVarInsn(ALOAD, i);// 加载 num 参数
			}
			mv.visitTypeInsn(CHECKCAST, inParamStr);
			mv.visitMethodInsn(INVOKEVIRTUAL, newcls, superMethodName, "(" + inParamStr + ")" + outParam, false);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		cw.visitEnd();
		return cw.toByteArray();
	}
	
	public void writeAnnotation(AnnotationVisitor av, Method m, Object instanse) throws SecurityException, Exception {
		String mName = m.getName();
		
		if(m.getParameterCount() > 0 || mName.equals("toString") || mName.equals("hashCode") || mName.equals("annotationType"))
			return;
		
		Class<?> mReturnType = m.getReturnType();
		Object val = m.invoke(instanse);
		if(val != null) {
			writeAnnotationType(av, mReturnType, mName, val);
		}
	}
	
	public void writeAnnotationType(AnnotationVisitor av, Class<?> mReturnType, String name, Object val) throws Exception {
		if(mReturnType.isArray()) {
			Object[] array = (Object[]) val;

			AnnotationVisitor av0 = av.visitArray(name);
			for (Object object : array) {
				if(object == null)
					continue;
				Class<?> typeClass = object.getClass();
				if(Proxy.isProxyClass(typeClass)) {
					Field h = object.getClass().getSuperclass().getDeclaredField("h");
					h.setAccessible(true);
					InvocationHandler handler = (InvocationHandler)h.get(object);
					Field type = handler.getClass().getDeclaredField("type");
					type.setAccessible(true);
					typeClass = (Class<?>) type.get(handler);
				}
				writeAnnotationType(av0, typeClass, null, object);
			}
			av0.visitEnd();
		} else if(mReturnType.isEnum()) {
			av.visitEnum(name, "L"+mReturnType.getName().replace(".", "/")+";", val.toString());
		} else if(mReturnType.isAnnotation()){
			AnnotationVisitor av0 = av.visitAnnotation(null, "L"+mReturnType.getName().replace(".", "/")+";");
			Method[] aMethods = mReturnType.getDeclaredMethods();
			for (Method method : aMethods) {
				writeAnnotation(av0, method, val);
			}
			av0.visitEnd();
		} else if(mReturnType.isAssignableFrom(Class.class)){
			av.visit(name, Type.getType("L"+((Class<?>)val).getName().replace(".", "/")+";"));
		} else {
			av.visit(name, val);
		}
	}
}