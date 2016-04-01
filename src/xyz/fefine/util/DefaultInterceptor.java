package xyz.fefine.util;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DefaultInterceptor implements Interceptor {

	@Override
	public void before(ServletRequest request, ServletResponse response, Method method, Object[] args) {
		// TODO Auto-generated method stub
		
//		System.out.println("before:"+method.getName());
	}

	@Override
	public void after(ServletRequest request, ServletResponse response, Method method, Object[] args) {
		// TODO Auto-generated method stub
//		System.out.println("after");

	}

	@Override
	public void afterThrowing(ServletRequest request, ServletResponse response, Method method, Object[] args,
			Throwable throwable) {
		throwable.printStackTrace();
//		System.out.println("afterThrowing");
		// TODO Auto-generated method stub

	}

	@Override
	public void afterFinally(ServletRequest request, ServletResponse response, Method method, Object[] args) {
//		System.out.println("afterFinally");
		// TODO Auto-generated method stub

	}

}
