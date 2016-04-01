package xyz.fefine.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SelfDispatcher implements RequestDispatcher{

	@Override
	public void forward(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		
		
		System.out.println("disp");
		
	}

	@Override
	public void include(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
