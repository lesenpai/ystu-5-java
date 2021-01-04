import javax.servlet.*;

public class Model2Servlet extends GenericServlet {
	public void service(ServletRequest request, ServletResponse response) 
	throws ServletException, java.io.IOException { 
		String userName = request.getParameter("userName"); 
		String password = request.getParameter("password");

		if (userName==null) {
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/Login.jsp"); 
			rd.forward(request, response);
		}
		else {
			// успешная регистрация
			if (password!=null && userName.equals("lev") && password.equals("root")) { 
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/Welcome.jsp"); 
				rd.forward(request, response);
			}
			// отказ регистрации
			else {
				request.setAttribute("error", "yes");
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/Login.jsp"); 
				rd.forward(request, response);
			}
		}
	} 
}
