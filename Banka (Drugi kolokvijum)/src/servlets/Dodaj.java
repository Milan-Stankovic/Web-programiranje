package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Korisnik;
import javaBean.Racun;

/**
 * Servlet implementation class Dodaj
 */
@WebServlet(description = "dodaj racun", urlPatterns = { "/Dodaj" })
public class Dodaj extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dodaj() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 int brojRacuna = Integer.parseInt(request.getParameter("brojRacuna"));
		 String tipRacuna = request.getParameter("tipRacuna");
		 int raspolozivoStanje = Integer.parseInt(request.getParameter("raspolozivoStanje"));
		 int rezervisanoStanje = Integer.parseInt(request.getParameter("rezervisanoStanje"));
		 boolean online=false;
		 if(request.getParameter("online")!=null){
			 if(request.getParameter("online").equals("online"))
			 online=true;
		 }
		 boolean aktivan = true;
		 if(brojRacuna>0){
			 Racun r = new Racun(brojRacuna, tipRacuna, raspolozivoStanje, rezervisanoStanje, online, aktivan);
			 Korisnik k = (Korisnik) request.getSession().getAttribute("aktivniKorisnik");
			 k.addRacun(r);
			 
		 }
		 response.sendRedirect("banka.jsp");
		 return;
		 
		 
	}

}
