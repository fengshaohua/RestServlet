package xyz.fefine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)				//参数声明
@Retention(RetentionPolicy.RUNTIME)		//运行期间保留
public @interface RequestParam {
	public String value() default "";
}
