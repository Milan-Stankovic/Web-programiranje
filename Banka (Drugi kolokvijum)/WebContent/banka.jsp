<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Banka</title>
</head>
<body>

	<c:set var ="aktivniKorisnik" value="${sessionScope.aktivniKorisnik}"/>
	<c:set var ="Korisnici" value="${applicationScope.korisnici}"/>
		
	<c:if test="${empty aktivniKorisnik}">
		  <c:redirect url = "/login.html"/>
	</c:if>

	<c:if test="${empty Korisnici}">
		  <c:redirect url = "/login.html"/>
	</c:if>

		<h1> Racuni </h1>
		
		<form action="Dodaj" method="post" >
		
			<table>
				<tr>
					<td>Broj racuna </td> <td> <input type="text" name="brojRacuna"> </td>
				</tr>	
				<tr> 
					<td>Tip racuna </td> <td> <select name="tipRacuna"> <option label="Dinarski"  value="Dinarski" /> <option label="Devizni" value="Devizni" /></select> </td>
				</tr>
				<tr>
					<td>Raspolozivo stanje </td> <td> <input type="text" name="raspolozivoStanje"> </td>
				</tr>
				<tr>
					<td>Rezervisano stanje </td> <td> <input type="text" name="rezervisanoStanje"> </td>
				</tr>
				<tr>
					<td> Online </td> <td> <input type="checkbox" name="online" value="online" /> </td>
				</tr>
				<tr> 
					<td> <input type="submit" value="Dodaj" /> </td>
				</tr>
			
			
			</table>
		</form>
		

		
	<c:if test="${not empty aktivniKorisnik.getRacuni()}">
		<table border="1">
			<tr>
			<td>Broj racuna</td>
			<td>Tip racuna</td>
			<td>Rezervisano</td>
			<td>Raspolozivo</td>
			<td>Ukupno</td>
			<td>Online</td>
			<td>Aktivan</td>
			<td> </td>
			<td> </td>
			</tr>
		
		
			<c:forEach var="racun" items="${aktivniKorisnik.getRacuni()}">
				<tr>
					<td><c:out value="${racun.getBrojRacuna().toString()}"/>  </td>	
					<td><c:out value="${racun.getTipRacuna().toString()}"/>  </td>		
					<td><c:out value="${racun.getRezervisanoStanje().toString()}"/>  </td>		
					<td><c:out value="${racun.getRaspolozivoStanje().toString()}"/>  </td>
					<c:set var ="raspolozivo" value="${racun.getRaspolozivoStanje()}"/>
					<c:set var ="rezervisano" value="${racun.getRezervisanoStanje()}"/>		
					<td><c:out value="${( raspolozivo + rezervisano).toString()}"/>  </td>	
					<td><c:if test="${racun.isOnline() eq 'false'}">Ne</c:if>
    				<c:if test="${racun.isOnline() eq 'true'}">Da</c:if>
    				</td>
    				<td>
    				<c:if test="${racun.isAktivan() eq 'false'}">Ne</c:if>
    				<c:if test="${racun.isAktivan() eq 'true'}">Da</c:if>
    				</td>
    				<td><a href="/Banka/RacuniServlet?param2=obrisi&param1=${racun.getBrojRacuna()}">Obriši</a></td>
    				<td>
	    			<c:if test="${racun.isAktivan() eq 'false'}">
	    				<a href="/Banka/RacuniServlet?param2=aktiviraj&param1=${racun.getBrojRacuna()}" >Aktiviraj</a>
	    			</c:if>
	    			<c:if test="${racun.isAktivan() eq 'true'}">
	    				<a href="/Banka/RacuniServlet?param2=deaktiviraj&param1=${racun.getBrojRacuna()}"  >Deaktiviraj</a>
	    			</c:if>
    			</td>
									
			
				</tr>
		
			</c:forEach>
		</table>
		
		<br>
		<br>
		
		<form action="RacuniServlet" method="post">
		<table>
			<tr>
				<td>Racun</td>
				<td>
				<select name="racunizaUplatu">
				<c:forEach var="kori" items="${applicationScope.korisnici.getKorisnici()}">
					<c:forEach var="entry" items="${kori.getRacuni()}">
							<c:if test="${entry.isAktivan() eq 'true'}">
    							<option ><c:out value="${entry.getBrojRacuna().toString()}" /><option>
    						</c:if>
    					</c:forEach>
    			</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
			<td>Iznos</td>
			<td><input type="text" name="iznos"></td>
			</tr>

			<tr>
			<td></td>
			<td><input type="submit"  value="Uplati" ></td>
			</tr>
		</table>
		
		</form>
		
	</c:if>
	<a href="/Banka/Login?param1=invalidate">LogOut</a>
		
</body>
</html>