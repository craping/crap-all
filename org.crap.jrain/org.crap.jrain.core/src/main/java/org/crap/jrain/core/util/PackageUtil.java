package org.crap.jrain.core.util;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crap.jrain.core.asm.annotation.Pipe;
import org.crap.jrain.core.asm.annotation.Pump;
import org.crap.jrain.core.validate.annotation.BarScreen;
import org.crap.jrain.core.validate.annotation.Parameter;
import org.crap.jrain.core.validate.support.Param;
import org.crap.jrain.core.validate.support.param.EnumParam;
import org.crap.jrain.core.validate.support.param.MultiParam;

public class PackageUtil {
	
	private static Logger log = LogManager.getLogger(PackageUtil.class);
	
	/**
	 * 获取某包下（包括该包的所有子包）所有类
	 * @param packageName 包名
	 * @return 类的完整名称
	 * @throws Exception 
	 */
	public static String apiResolve(String packageName, String host) throws Exception {
		StringBuffer document = new StringBuffer("</br></br>");
		List<String> classNames = getClassName(packageName, true);
		if (classNames != null) {
			for (String className : classNames) {
				if(!className.contains("$")){
					log.debug("classname:"+className);
					Class<?> cls = Class.forName(className);
					if(cls.isAnnotationPresent(Pump.class)){
						Pump pump = cls.getAnnotation(Pump.class);
						document.append("<p><strong style='color:red'>模块：").append(pump.value()).append("</strong></p>");
						for (Method method : cls.getMethods()) {
							if(method.isAnnotationPresent(Pipe.class)){
								Pipe pipe = method.getAnnotation(Pipe.class);
								String url = host+"/"+pump.value()+"/"+pipe.value();
								document.append("请求地址：<span style='color:blue'>").append(url).append("</span> ")
								.append("格式：[")
								.append("<a target='_blank' href='").append(url).append("'>html</a>").append(" ")
								.append("<a target='_blank' href='").append(url).append("?format=json").append("'>json</a>").append(" ")
								.append("<a target='_blank' href='").append(url).append("?format=xml").append("'>xml</a>").append("]").append("</br>");
								BarScreen barScreen = method.getAnnotation(BarScreen.class);
								if(barScreen != null) {
									document.append("请求方法：").append(method.getParameterTypes()[0].equals(Map.class)?"GET":"POST").append("</br>");
									document.append("接口说明：").append(barScreen.desc()).append(" (加密传输：").append(barScreen.security()).append(")</br>");
									document.append("参数：</br>");
									for (Parameter param : barScreen.params()){
										if(MultiParam.class.isAssignableFrom(param.type())){
											MultiParam<?> multiParam = (MultiParam<?>) param.type().getDeclaredConstructor().newInstance();
											for (Param<?> aParam : multiParam.getParams()) {
												String enums = aParam.getEnums() != null && aParam.getEnums().length >0?
														(EnumParam.class.isAssignableFrom(aParam.getClass())?"取值范围：["+aParam.toString()+"]":"取值范围："+Arrays.toString(aParam.getEnums())):"";
															
												document.append("<span style='color:#FF0000'>").append(aParam.getValue()).append("</span>:<span style='color:#0000FF'>").append(aParam.getDesc())
												.append("[是否必要：").append(aParam.isRequired()).append("] ")
												.append(enums)
												.append("</span></br>");
											}
										}else{
											Param<?> aParam = (Param<?>) param.type().getDeclaredConstructor().newInstance();
											String enums = aParam.getEnums() != null && aParam.getEnums().length >0?
													(EnumParam.class.isAssignableFrom(aParam.getClass())?"取值范围：["+aParam.toString()+"]":"取值范围："+Arrays.toString(aParam.getEnums())):"";
											
											if(!param.value().equals("") || aParam.getValue() != null)
												document.append("<span style='color:#FF0000'>").append(param.value().equals("")?aParam.getValue():param.value()).append("</span>:<span style='color:#0000FF'>")
												.append(param.desc().equals("")?aParam.getDesc():param.desc()).append(" [是否必要：")
												.append(aParam.isRequired() == null?param.required():aParam.isRequired()).append("] ")
												.append(enums).append("</span></br>");
											else if(!param.desc().equals("") || aParam.getDesc() != null)
												document.append((param.desc().equals("")?aParam.getDesc():param.desc())).append("</br>");
										}
									}
								}
								document.append("</br></br></br></br>");
							}
						}
						
					}
				}
			}
		}
		
		return document.toString();
	}
	
	
	/**
	 * 获取某包下（包括该包的所有子包）所有类
	 * @param packageName 包名
	 * @return 类的完整名称
	 * @throws URISyntaxException 
	 */
	public static List<String> getClassName(String packageName) throws URISyntaxException {
		return getClassName(packageName, true);
	}

	/**
	 * 获取某包下所有类
	 * @param packageName 包名
	 * @param childPackage 是否遍历子包
	 * @return 类的完整名称
	 * @throws URISyntaxException 
	 */
	@SuppressWarnings("unused")
	public static List<String> getClassName(String packageName, boolean childPackage) throws URISyntaxException {
		List<String> fileNames = null;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");
		URL url = loader.getResource(packagePath);
		//String path = PackageUtil.class.getResource("/").toURI().getPath();
		log.info("解析路径："+url);
		log.info("解析路径："+url.getPath());
		if (url != null) {
			String type = url.getProtocol();
			log.info("uriPath:"+url.toURI().getPath());
			String path = url.toURI().getPath() == null?url.getPath():url.toURI().getPath();
			if (type.equals("file")) {
				fileNames = getClassNameByFile(path, null, childPackage);
			} else if (type.equals("jar")) {
				fileNames = getClassNameByJar(path, childPackage);
			}
		} else {
			fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
		}
		return fileNames;
	}

	/**W
	 * 从项目文件获取某包下所有类
	 * @param filePath 文件路径
	 * @param className 类名集合
	 * @param childPackage 是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				if (childPackage) {
					myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
				}
			} else {
				String childFilePath = childFile.getPath();
				log.debug("childFilePath:"+childFilePath);
				if (childFilePath.endsWith(".class")) {
					childFilePath = childFilePath.substring(childFilePath.indexOf("classes") + 8, childFilePath.lastIndexOf("."));
					childFilePath = childFilePath.replace("\\", ".").replace("/", ".");
					myClassName.add(childFilePath);
				}
			}
		}

		return myClassName;
	}

	/**
	 * 从jar获取某包下所有类
	 * @param jarPath jar文件路径
	 * @param childPackage 是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
		log.info("jar path:"+jarPath);
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		String packagePath = jarInfo[1].substring(1);
		try {
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if (childPackage) {
						if (entryName.startsWith(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					} else {
						int index = entryName.lastIndexOf("/");
						String myPackagePath;
						if (index != -1) {
							myPackagePath = entryName.substring(0, index);
						} else {
							myPackagePath = entryName;
						}
						if (myPackagePath.equals(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					}
				}
			}
			jarFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myClassName;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 * @param urls URL集合
	 * @param packagePath 包路径
	 * @param childPackage 是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				URL url = urls[i];
				String urlPath = url.getPath();
				// 不必搜索classes文件夹
				if (urlPath.endsWith("classes/")) {
					continue;
				}
				String jarPath = urlPath + "!/" + packagePath;
				myClassName.addAll(getClassNameByJar(jarPath, childPackage));
			}
		}
		return myClassName;
	}
}