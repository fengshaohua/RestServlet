package xyz.fefine.util;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.objectweb.asm.Type;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import xyz.fefine.entity.RequestHandler;

/**
 * 
    * @ClassName: DynamicMethodInterceptor
    * @Description: TODO 代理类，进行改造
    * @author feng_
    * @date 2016年4月1日
    *
 */
public class DynamicMethodInterceptor implements MethodInterceptor{
	
	
	private Object target;
	private Interceptor interceptor;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestHandler handler;
	
	/**
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	    * @Title: getInstance
	    * @Description: TODO 创建代理对象
	    * @param @param target
	    * @param interceptor 可选，自定义的拦截器
	    * @param @return    
	    * @return Object    
	    * @throws
	 */
	public void getInstance(RequestHandler handler , Interceptor interceptor,HttpServletRequest request,HttpServletResponse response) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		this.interceptor = interceptor;
		this.request = request;
		this.response = response;
		this.target = Class.forName(handler.getClassName()).newInstance();
		this.handler = handler;
		
		Enhancer enhance = new Enhancer();
		
		enhance.setSuperclass(this.target.getClass());
		//回调方法
		enhance.setCallback(this);
		//创建代理对象，不执行此会出错
		Object o = enhance.create();
		
		try {
			intercept(o,handler.getMethod(),handler.getObjs(),null);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.err.println("执行方法出错");
			e.printStackTrace();
		}
		
	}

	@Override
	/**
	 * 回调方法
	 */
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		//可选
		if(interceptor == null){
			interceptor = new DefaultInterceptor();
		}
		
		try{
			interceptor.before(request, response, method, args);
			

			
			//进行处理
//			info
			
//			Type rt = Type.getReturnType(method);
//			Type[] pst = Type.getArgumentTypes(method);
//			
			
			Signature sin = new Signature(method.getName(),handler.getReturnType(),handler.getParamsType());
			
			MethodProxy proxys = MethodProxy.find(obj.getClass(), sin);
				
			proxys.invokeSuper(obj, args);
			
			interceptor.after(request, response, method, args);
			
			
		}catch(Throwable throwable){
			interceptor.afterThrowing(request, response, method, args, throwable);
		}finally{
			interceptor.afterFinally(request, response, method, args);
		}
		
		return null;
	}
	

}