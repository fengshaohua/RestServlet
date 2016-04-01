package xyz.fefine.entity;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.objectweb.asm.Type;

import xyz.fefine.exception.NoHandlerFoundException;
import xyz.fefine.exception.ParamNotFoundException;
import xyz.fefine.util.DynamicMethodInterceptor;

/**
 * 	在servlet初始化是进行存储List<RequestHandler> , 
 * 对请求的url进行适配，查找符合的requesHandler，requestHandler
 * 进行执行适配的方法
    * @ClassName: RequestHandler
    * @Description: TODO
    * @author feng_
    * @date 2016年3月31日
    *
 */
public class RequestHandler {
	
	/**
	 * 包含信息：
	 */
	/**
	 * 原url信息
	 */
	private String url;
	
	/**
	 * url正则
	 */
	private Pattern urlPattern;
	
	/**
	 * 类名
	 */
	private String className;
	
	/**
	 * 参数信息
	 */
	private String paramsInfo[];
	
	/**
	 * 传递的参数
	 */
	private Object[] objs;
	/**
	 * 方法
	 */
	private Method method;
	
	/**
	 * 方法所在的位置
	 */
	private int methodLocation;
	
	/**
	 * 参数类型
	 */
	private Type[] paramsType;
	
	/**
	 * 返回值类型
	 */
	private Type returnType;
	
	/**
	 * 请求方式
	 */
	private String requestMethod;
	
	
	
	
	public String getUrl() {
		return url;
	}




	public void setUrl(String url) {
		this.url = url;
	}




	public Pattern getUrlPattern() {
		return urlPattern;
	}




	public void setUrlPattern(Pattern urlPattern) {
		this.urlPattern = urlPattern;
	}




	public String getClassName() {
		return className;
	}




	public void setClassName(String className) {
		this.className = className;
	}




	public String[] getParamsInfo() {
		return paramsInfo;
	}




	public void setParamsInfo(String[] paramsInfo) {
		this.paramsInfo = paramsInfo;
	}




	public Object[] getObjs() {
		return objs;
	}




	public void setObjs(Object[] objs) {
		this.objs = objs;
	}




	public Method getMethod() {
		return method;
	}




	public void setMethod(Method method) {
		this.method = method;
	}




	public int getMethodLocation() {
		return methodLocation;
	}




	public void setMethodLocation(int methodLocation) {
		this.methodLocation = methodLocation;
	}




	public Type[] getParamsType() {
		return paramsType;
	}




	public void setParamsType(Type[] paramsType) {
		this.paramsType = paramsType;
	}




	public Type getReturnType() {
		return returnType;
	}




	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}




	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}




	/**
	 * 是否符合
	    * @Title: matcher
	    * @Description: TODO
	    * @param @param url
	    * @param @return    
	    * @return boolean    
	    * @throws
	 */
	public boolean matcher(String url){
//		return url.matches(urlPattern);
		
		return urlPattern.matcher(url).find();
		
	}


	/**
	 * 
	    * @Title: invokeMethod
	    * @Description: TODO 执行方法
	    * @param     
	    * @return void    
	    * @throws
	 */
	public void invokeMethod(HttpServletRequest req,HttpServletResponse resp) throws NoHandlerFoundException{
		
		//需要先组织一下参数
		DynamicMethodInterceptor dmi = new DynamicMethodInterceptor();
		
		
		try {
			this.setObjs(createObjs(req,resp));
		} catch (ParamNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			dmi.getInstance(this, null, req, resp);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Object[] createObjs(HttpServletRequest req,HttpServletResponse resp) throws ParamNotFoundException{
		
		String[] infos = this.getParamsInfo();
		
		Object[] objs = new Object[this.paramsType.length];
		
		//参数前两个必须为request,response
		objs[0] = req;
		objs[1] = resp;
		
		//进行变量注入
		String reqPath = req.getServletPath();
		
//		method.getParameters();
//		proxy.
//		Parameter[] ps = method.getParameters();
		
		for (int i = 0; i < infos.length; i++) {
			
			String info = infos[i];
			
			//获取不同的参数
			String[] t = info.split("}");
			
			String[] path = reqPath.split("/");
			
			//获取url中的值,t[1] 为在path中的位置
			String value = path[Integer.parseInt(t[1])];
			
			//将url中的值传递给参数 t[2]为在objs中的位置
			objs[Integer.parseInt(t[2])] = value;
			
		}
		
		return objs;
	}

}
