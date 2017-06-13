package servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Korisnici;
import javaBean.Korisnik;
import javaBean.Racun;

/**
 * Servlet implementation class Login
 */
@WebServlet(description = "login servlet", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Korisnici korisnici;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		korisnici= new Korisnici();
		Korisnik k = new Korisnik();
		k.setUserName("abc"); // kasnije cu formu za regirstraciju
		k.setPassword("123");
		korisnici.addUser(k);
		Korisnik k1 = new Korisnik();
		k1.setUserName("aaa");
		k1.setPassword("111");
		Racun r1 = new Racun(111,"Dinarski",222,333,true,true);
		k1.addRacun(r1);
		korisnici.addUser(k1);
		config.getServletContext().setAttribute("korisnici", korisnici);
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("login.html");
		return;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//.getServletContext().
		String userName= request.getParameter("userName");
		String password = request.getParameter("password");
		Korisnik k = korisnici.checkForm(userName, password);
		if(k!=null){
			request.getSession().setAttribute("aktivniKorisnik", k);

			response.sendRedirect("banka.jsp");
			return;
		}else{
			response.sendRedirect("login.html");
			return;
			
		}
		
		
	}

}
