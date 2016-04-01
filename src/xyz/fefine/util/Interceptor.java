package xyz.fefine.util;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
    * @ClassName: Interceptor
    * @Description: TODO 拦截器
    * @author feng_
    * @date 2016年3月28日
    *
 */
public interface Interceptor {
	
	/**
	 * 
	    * @Title: before
	    * @Description: TODO 方法执行前
	    * @param @param request
	    * @param @param response
	    * @param @param method
	    * @param @param args    
	    * @return void    
	    * @throws
	 */
	public void before(ServletRequest request,ServletResponse response,Method method, Object[] args);  
	  
    public void after(ServletRequest request,ServletResponse response,Method method, Object[] args);  
  
    public void afterThrowing(ServletRequest request,ServletResponse response,Method method, Object[] args, Throwable throwable);  
  
    public void afterFinally(ServletRequest request,ServletResponse response,Method method, Object[] args);  

}
