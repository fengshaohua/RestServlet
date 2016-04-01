package xyz.fefine.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.fefine.entity.RequestHandler;
import xyz.fefine.entity.RequestHelper;
import xyz.fefine.exception.NoHandlerFoundException;

/**
 * Servlet implementation class MainServlet
 */
// @WebServlet("/")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static RequestHelper reqHelper = null;
	// 存放handler
	// private static List<RequestHandler> handlerList;

	@Override
	/**
	 * 重写Receives standard HTTP requests from the public service method and
	 * dispatches them to the doXXX methods defined in this class.
	 */
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String url = request.getServletPath();

		// handler处理
		RequestHandler handler = reqHelper.findRequestHandler(url);
		
		if(handler == null){
			
			response.getWriter().append("no this handler,please inout a correct url");
			
			return ;
		}
		
		if(!handler.getRequestMethod().toLowerCase().equals(request.getMethod().toLowerCase()) ){
			
			
			response.getWriter().append("requestMathod is not support");
			
			return ;
			
		}
		
			// 执行
			try {
				handler.invokeMethod(request, response);
			} catch (NoHandlerFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

	public void init(ServletConfig config) throws ServletException {

		reqHelper = new RequestHelper();

		String packagePath = config.getInitParameter("contextConfigLocation");

		// 初始化配置

		reqHelper.initRequestHandler(packagePath);

		super.init(config);
	}

}
