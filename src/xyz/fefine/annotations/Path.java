package xyz.fefine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
    * @ClassName: Path
    * @Description: TODO 请求地址的注解
    * @author feng_
    * @date 2016年3月28日
    *
 */
@Target(value={ElementType.METHOD,ElementType.TYPE})				//方法和类均支持的注解声明
@Retention(RetentionPolicy.RUNTIME)		//运行期间保留
public @interface Path {
	
	public String value() default "/";				//默认为/
	public String requestMethod() default "GET";	//默认为get请求//暂时不实现


}
