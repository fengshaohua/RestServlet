package xyz.fefine.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.fefine.annotations.Path;
import xyz.fefine.annotations.RequestParam;

/**
 * 
    * @ClassName: C1
    * @Description: TODO controller测试
    * @author feng_
    * @date 2016年3月28日
    *
 */
@Path("/Main")
public class C1 {
	
	@Path("/Index/{hehe}")
	public void exe(HttpServletRequest request,HttpServletResponse response,@RequestParam("hehe")String name) throws IOException{
		System.out.println("java " + name);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}
	@Path("/Index/{hehe}/{ni}")
	public void exe2(HttpServletRequest request,HttpServletResponse response,@RequestParam("hehe")String name,@RequestParam("ni")String ni) throws IOException{
		
		System.out.println("java2 0:"+name+" 1:"+ni);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}
	
	@Path("/Hehe/{rr}")
	public void exe3(HttpServletRequest request,HttpServletResponse response,@RequestParam("rr")String name) throws IOException{
		
		
		
		response.getWriter().append("{'name':'"+name+"'}");
		
	}
	
	@Path("/Hehe")
	public void exe4(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		response.getWriter().append("{'name':'mei'}");
		
	}

}
