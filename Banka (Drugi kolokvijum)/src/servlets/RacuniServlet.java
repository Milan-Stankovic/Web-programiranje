package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaBean.Korisnici;
import javaBean.Korisnik;
import javaBean.Racun;

/**
 * Servlet implementation class RacuniServlet
 */
@WebServlet("/RacuniServlet")
public class RacuniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RacuniServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String tip = request.getParameter("param2");
		
		int brojRacuna = Integer.parseInt(request.getParameter("param1"));
		
		Korisnik k = (Korisnik) request.getSession().getAttribute("aktivniKorisnik");
		
		switch (tip) {
		case "obrisi":
			int i=-1;
			for(Racun r : k.getRacuni()){
				i++;
				if(r.getBrojRacuna()==brojRacuna){
					k.getRacuni().remove(i);
					break;
				}
			}
			break;
		case "aktiviraj":
			for(Racun r : k.getRacuni()){
				if(r.getBrojRacuna()==brojRacuna){
					r.setAktivan(true);
				}
			}
			
			break;

		case "deaktiviraj":
			for(Racun r : k.getRacuni()){
				if(r.getBrojRacuna()==brojRacuna){
					r.setAktivan(false);
				}
			}
	
			break;
		default:
			System.out.println("greska");
			break;
		}
		
		 response.sendRedirect("banka.jsp");
		 return;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int iznos = Integer.parseInt(request.getParameter("iznos"));
		int brojRacuna = Integer.parseInt(request.getParameter("racunizaUplatu"));
		
		Korisnici kor = (Korisnici) request.getServletContext().getAttribute("korisnici");
		
		Korisnik k = kor.findRacun(brojRacuna);
		if(k != null){
			for(Racun r : k.getRacuni()){
				if(r.getBrojRacuna()==brojRacuna){
					r.addIznos(iznos);
				}
			}
			response.sendRedirect("banka.jsp");
			return;
		}
		return;
		
	}

}
