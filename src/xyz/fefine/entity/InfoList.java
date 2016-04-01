package xyz.fefine.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
    * @ClassName: ResMapFactory
    * @Description: TODO 用于存放url相关信息,设置为静态是为了共享一个list,已经淘汰，更换成了reqeustHandler
    * @author feng_
    * @date 2016年3月30日
    *		//固定长度为4
		//1，urlPattern
		//2，url
		//3，className
		//4，methodName
 */
@Deprecated
public class InfoList {
	
	public static List<String[]> urlInfos = new ArrayList<String[]>(){
		
	    /**
	    * @Fields serialVersionUID : TODO
	    */
	    
	private static final long serialVersionUID = -1136253406934254608L;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String rs = "";
		for(String[] s: this){
			for(String ss : s){
				rs += ss +",";
			}
			rs += "\n";
		}
		return rs;
	}
};;
	
	
	public static String getUrlPattern(String url){
		return getUrlAndCNAndMN(url)[0];
	}
	
	public static String getUrl(String url){
		return getUrlAndCNAndMN(url)[1];
		
	}
	public static String getClassName(String url){
		
		return getUrlAndCNAndMN(url)[2];
	}
	public static String getMethodName(String url){
		
		return getUrlAndCNAndMN(url)[3];
	}
	
	
	/**
	 * 
	    * @Title: getUrlAndCNAndMN
	    * @Description: TODO 获取跟符合url的相关信息
	    * @param @param url
	    * @param @return    
	    * @return String[]  长度为4  
	    * @throws
	 */
	public static String[] getUrlAndCNAndMN(String url){
		
		if(url == null) return null;
		
		for(String[] urls : urlInfos){
			
			if(url.matches(urls[0]))
				return urls;
			
		}
		
		return null;
		
	}

}
