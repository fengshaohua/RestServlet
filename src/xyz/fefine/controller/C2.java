package xyz.fefine.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.fefine.annotations.Path;
import xyz.fefine.annotations.RequestParam;

public class C2 {
	
	@Path(value="/main/{id}",requestMethod = "post")
	public void fun(HttpServletRequest req,HttpServletResponse resp,@RequestParam("id")String id) throws IOException{
		
		
		resp.getWriter().append("[{\"id\":"+id+"}]");
		
		
	}

}
