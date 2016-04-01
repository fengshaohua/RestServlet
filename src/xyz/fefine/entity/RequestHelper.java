package xyz.fefine.entity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.objectweb.asm.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xyz.fefine.annotations.Path;
import xyz.fefine.annotations.RequestParam;

/**
 *    请求协助类
 *    传入url，获取requestHandler
    * @ClassName: RequestHelper
    * @Description: TODO
    * @author feng_
    * @date 2016年3月31日
    *
 */
public class RequestHelper {
	
	Logger LOG = Logger.getLogger(this.getClass().getName(), null);
	
	private List<RequestHandler> handlers;
//	private RequestHandler handler;
	
	public RequestHelper() {
		handlers = new ArrayList<RequestHandler>();
	}
	
	/**
	 * 
	    * @Title: findRequestHandler
	    * @Description: TODO 获取到适合的请求处理器
	    * @param @param url
	    * @param @param handlerList
	    * @param @return    
	    * @return RequestHandler    
	    * @throws
	 */
	public RequestHandler findRequestHandler(String url){
		
		for (int i = 0; i < handlers.size(); i++) {
			
			if(handlers.get(i).matcher(url)){
				return handlers.get(i);
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * 
	    * @Title: initRequestHandler
	    * @Description: TODO 初始化requestHandler
	    * @param @param packageName 需要扫描的包名
	    * @param @return    
	    * @return List<RequestHandler>    
	    * @throws
	 */
	public List<RequestHandler> initRequestHandler(String packagePath){
		
		String[] packages = getPackageName(packagePath);
		
		for (int i = 0; i < packages.length; i++) {
			
			scanPackage(packages[i]);
			
		}
		
		return handlers;
	}
	
	public String[] getPackageName(String packagePath){
		
		
		
		try {
			// getpath
			packagePath = this.getClass().getClassLoader().getResource("").toURI().getPath()+"//" + packagePath;
			
			
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder build = null;
			try {
				build = fact.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			Document doc = null;
			try {
				doc = build.parse(new File(packagePath));
			} catch ( IOException | org.xml.sax.SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//获取根
			Element ele = doc.getDocumentElement();
			
			//要扫描的包名
			NodeList nl = ele.getElementsByTagName("package");
			
			String[] scPkName = new String[nl.getLength()];
			
			for (int i = 0; i < nl.getLength(); i++) {
				
				Node no = nl.item(i);
				
				scPkName[i] = no.getTextContent();
				
				//scanPackage(sacnPkName);
				LOG.log(Level.INFO,"scanPackageName:"+no.getTextContent());
				
			}
			
			
			return scPkName;
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//输出调试
//		System.out.println(InfoList.urlInfos);
		
		return null;
		
	}
	
	/**
	 * 
	    * @Title: scanPackage
	    * @Description: TODO 扫描包下面所有类
	    * @param @param pkName    
	    * @return void    
	    * @throws
	 */
	private void scanPackage(String pkName){
		String path = pkName.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        try {
            if(url!=null && url.toString().startsWith("file")){
                String filePath = URLDecoder.decode(url.getFile(),"utf-8");
                File dir = new File(filePath);
                List<File> fileList = new ArrayList<File>();
                fetchFileList(dir,fileList);
                for(File f:fileList){
                    String className =  f.getAbsolutePath();
                    if(className.endsWith(".class")){
                        String nosuffixFileName = className.substring(8+className.lastIndexOf("classes"),className.indexOf(".class"));
                        className = nosuffixFileName.replaceAll("\\\\", ".");
                    }
//                    System.out.println("scan class name ："+className);
                    LOG.log(Level.INFO, "sacnClassName:"+className);
                    //扫描class
                    scanClass(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    private static void  fetchFileList(File dir,List<File> fileList){
        if(dir.isDirectory()){
            for(File f:dir.listFiles()){
                fetchFileList(f,fileList);
            }
        }else{
            fileList.add(dir);
        }
    }
	
	
	/**
	 * 
	    * @Title: scanClass
	    * @Description: TODO 扫描类中所有的方法及其注解
	    * @param @param className
	    * @param @throws InstantiationException
	    * @param @throws IllegalAccessException
	    * @param @throws ClassNotFoundException    
	    * @return void    
	    * @throws
	 */
	private void scanClass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		
		
		Class<?> cla = Class.forName(className);
		
		String classAnnoName = "";
		
		Path path = cla.getAnnotation(Path.class);
		if(path != null){
			
			classAnnoName += path.value();
			
		}
			
		//获取所有方法
		Method[] mes = cla.getMethods();
		
		for(int i=0;i<mes.length;i++){
			
			Method m = mes[i];
			
			
			path = m.getAnnotation(Path.class);
			
			String methodName = m.getName();
			
			if(path != null){
				
				//每个带注解的method就是一个新的handler
				RequestHandler handler = new RequestHandler();
				
				handler.setClassName(className);
				
				handler.setMethod(m);
				//请求方式
				handler.setRequestMethod(path.requestMethod());
				
				handler.setMethodLocation(i);
				
				handler.setParamsType(Type.getArgumentTypes(m));
				
				handler.setReturnType(Type.getReturnType(m));
				
				//注解值
				String methodAnnoName = path.value();
				//方法名
//				String requestMethod = path.requestMethod();
				//参数暂时不需要在这里读取，放进Map里面的需要有：urlPattern，url，className，MethodName
				//在拦截器中进行方法的详细操作
				
				//参数名
				Parameter[] ps = m.getParameters();
				
				Map<String,Integer> map = new HashMap<String,Integer>();
				
				for(int ij = 0;ij<ps.length;ij++){
					
					Parameter p = ps[ij];
					
//					System.out.println(p.getParameterizedType());
					
					RequestParam rp = p.getAnnotation(RequestParam.class);
					
					if(rp != null){
						
						//参数的名称
						String paramName = rp.value();
//						System.out.println("param:"+paramName);
						LOG.log(Level.INFO, "param:"+paramName);
						map.put(paramName, ij);
					}
					
				}
				
				//保存到list中
				save(className, methodName, classAnnoName, methodAnnoName,map,handler);
				
			}
			
		}
		
	}
	
	/**
	 * 
	    * @Title: save
	    * @Description: TODO 将url处理并放入list
	    * @param @param className
	    * @param @param methodName
	    * @param @param classAnnoName
	    * @param @param methodAnnoName    
	    * @return void    
	    * @throws
	 */
	private void save(String className,String methodName,String classAnnoName,String methodAnnoName,Map<String,Integer> map,RequestHandler handler){
		
		String url = classAnnoName+methodAnnoName;
		String urlPattern = url.replaceAll("\\{[a-z0-9]+\\}", "[^/]+")+"$";
		
		LOG.log(Level.INFO, "scanUrl:"+url+"   scanUrlPattern:"+urlPattern);
		
		handler.setUrl(url);
		handler.setUrlPattern(Pattern.compile(urlPattern));
		
		//将map放入字符串中
		String[] params = paramsLocation(url,map);
		
		//参数的部分信息
		handler.setParamsInfo(params);
		
		handlers.add(handler);
		
/*		String[] infos = new String[4+params.length];
		infos[0] = urlPattern;
		infos[1] = url;
		infos[2] = className;
		infos[3] = methodName;
		for(int i=4;i<4+params.length;i++){
			infos[i] = params[i-4];
		}
		//初始化时应该做尽可能多的工作
		//放入list
		InfoList.urlInfos.add(infos);*/
		
	}
	
	/**
	 * 
	    * @Title: paramsLocation
	    * @Description: TODO 将链接分解并拼接成包含信息的字符串
	    * @param @param url
	    * @param @return    
	    * @return String[]    
	    * @throws
	 */
	private String[] paramsLocation(String url ,Map<String,Integer> map){
		
		String[] sta = url.split("/");
	
		//存放参数
		String[] res = new String[sta.length];
		int j = 0;
		for (int i = 0; i < sta.length; i++) {
			if(sta[i].matches("\\{.+\\}")){
				String t = "";
				String k = sta[i].substring(1,sta[i].length()-1);
				//第一个为参数名称，参数在源字符串中的位置，参数在方法的的参数中的位置
				t += k + "}"+i + "}" + map.get(k);
				res[j++] = t;
//				System.out.println("t is "+t);
				LOG.log(Level.INFO, "params:"+t);
			}
		}
		//sta为新字符串
		sta = new String[j];
		for (int i = 0; i < j; i++) 
			//将res中多余的去除
			sta[i] = res[i];
		
		
		return sta;
		
	}
	
	

}
