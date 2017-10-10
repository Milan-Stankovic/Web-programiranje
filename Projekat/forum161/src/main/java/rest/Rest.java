package rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import exceptions.NoSubForumException;
import exceptions.NoSuchUsernameException;
import exceptions.SubForumNameExistsException;
import exceptions.UsernameExistsException;
import files.Forum;
import files.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import model.Comment;
import model.CommentType;
import model.Flag;
import model.Message;
import model.Review;
import model.RewType;
import model.Search;
import model.Status;
import model.SubForum;
import model.Theme;
import model.User;
import model.UserType;

@Path("/services")
public class Rest {
	Users users;
	Forum forum;
	
	/*@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test(){	
		
	
	//	System.out.println("REST TEST !");
		
		users= Users.Instance();
		forum = Forum.Instance();
		return "Radi";
	}
	
	*/
	
	
	@GET
	@Path ("/SubForum/{subForumID1}")
	@Produces (MediaType.APPLICATION_JSON)
	public Response hiperlikSubForum(@QueryParam("subForumID") String subForumID){
		
	//	System.out.println("U restu za redirect na subforum");
		
		System.out.println("Name : " +subForumID);
		
		
		SubForum sf = Forum.Instance().findForum(subForumID);
		
		if(sf == null)
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for name: " + subForumID).build();
		
		URI uri = null;
		try {
			uri = new URI("/forum161/start/subForum.html");
			//?SubForumName="+subForumID
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//	System.out.println("Greska kod logouta u restu s");
		}

	 return Response.temporaryRedirect(uri).build();
	}
	
	
	@GET
	@Path ("/Theme/{themeID1}")
	@Produces (MediaType.APPLICATION_JSON)
	public Response hiperlikTheme(@QueryParam("themeID") String themeID, @QueryParam("subForumID") String subForumID ){
		
	//	System.out.println("U restu za redirect na temu");
	//	System.out.println(themeID);
	//	System.out.println(" sada malo sub");
	//	System.out.println(subForumID); // DOBIJAM OBA ALI GA REDIRECTUJE TAKO DA SKACE OPET NA OVO :D
	//	System.out.println("Sub ID : " + subForumID);
	//	System.out.println("Theme ID : " + themeID);
		
		SubForum sf = Forum.Instance().findForum(subForumID);
	
		sf.findTheme(subForumID);
		if(sf == null)
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for name: " + themeID).build();
		
		URI uri = null;
		try {
		//	uri = new URI("/forum161/start/subForum.html?subForumID="+subForumID+"&themeID="+themeID);
			uri = new URI("/forum161/start/theme.html");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//	System.out.println("Greska kod logouta u restu s");
		}
	//	System.out.println("Idem na ovaj uri ");
	//	System.out.println(uri);
//		System.out.println("rest dobro prodje");
	 return Response.temporaryRedirect(uri).build();
	}
	
	
	@GET
	@Path("/getOneSubForum")
	@Produces(MediaType.APPLICATION_JSON)
	public SubForum getOneSubForum(@QueryParam("subForumID") String subForumID)
	{

		subForumID = URLDecoder.decode(subForumID);
		System.out.println("subName :" +subForumID);
		SubForum sf= Forum.Instance().getSubForum(subForumID);
		//System.out.println(sf.getName());
		return sf;
		
	}
	
	@GET
	@Path("/getOneTheme/{themeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Theme getOneTheme(@PathParam("themeID") String themeID, @QueryParam("subForumID") String subForumID)
	{
	
		subForumID = URLDecoder.decode(subForumID);
		themeID = URLDecoder.decode(themeID);
		
	//	System.out.println("Sub ID : " + subForumID);
		
		SubForum sf= Forum.Instance().getSubForum(subForumID);
		//System.out.println(sf.getName());
		if(sf != null){
			for (Theme t1 : sf.getThemes().values()) {
				if(t1.getId().toString().equals(themeID))
					return t1;
				
			}
			
		}
		return null;
	}
	
	
	
	@POST
	@Path("/getThemeList")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Theme> getThemeList(ArrayList<String> ids){
	
		
	//	System.out.println("U get theme list");
		ArrayList<Theme> themes = new ArrayList<Theme>();
		
		if(ids!=null)
			//System.out.println("PA NISAM NULL");
		
		if(ids !=null)
		for (String themeID : ids) { // EKSTREMNO LOSE :D Trebao mi se u reviews cuvati objekat :D ili makar parent id :D 
			for (SubForum sf : Forum.Instance().getAllSubForums() ){
				for (Theme t1 : sf.getThemes().values()) {
					if(t1.getId().toString().equals(themeID)){
					//	System.out.println(t1.getId().toString());
					//	System.out.println(themeID);
					//	System.out.println("U dodaje temu u  listu");
						themes.add(t1);
					}
				}
			}
		}
		
		return themes;
		
	}
	
	
	

	@POST
	@Path("/getCommentList")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getCommentList(ArrayList<String> ids){
		
		System.out.println("U get comment list");
		boolean b = false;
		
		if(ids != null)
			System.out.println(ids.size());
		
		ArrayList<Comment> comments = new ArrayList<Comment>();
		if(ids !=null)
		for (String commentID : ids) { // EKSTREMNO LOSE PA NA KVADRAT :D Trebao mi se u reviews cuvati objekat ali json ne moze jer bi u autoru bio autor :D ili makar parent id :D 
			b=false;
			for (SubForum sf : Forum.Instance().getAllSubForums() ){
				for (Theme t1 : sf.getThemes().values()) {
					Comment c = t1.findComment(commentID);
					if(c!=null){
						System.out.println("Dodaje comm u listu ");
						if(c.getId().toString().equals(commentID)){
							comments.add(c);
							b=true;
						}
					}
					if(b)
						break;
				}
				if(b)
					break;
			}
		}
		
		return comments;
		
	}
	
	
	
	
	
	@PUT
	@Path ("/addFavSubForum")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public User addFavSubtoUser(@QueryParam("subForumID") String subForumID, User u){
		
		//System.out.println("Dodaje se omiljeni");
		//System.out.println(u.getUsername());
		//	System.out.println(subForumID);
		//boolean b=true;
		for (SubForum sub : Forum.Instance().getAllSubForums()) {
			if(sub.getName().equals(subForumID))
				Users.Instance().followForum(u, sub);
			
		}
		
		
		return null;
	}
	
	
	
	@PUT
	@Path ("/sendMessage")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Message sendMessage(@QueryParam("reciverID") String reciverID, @QueryParam("senderID") String senderID, String text){
		
	System.out.println("U restu za send message");
	

		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(reciverID))
			return Users.Instance().sendMessage(reciverID, senderID, text);
		}
		
		
		return null;
	
	}
	
	
	public Message respondToFlag(String reciverID, String senderID, String text){
		
//		System.out.println("U restu za send message");
		

			for (User us : Users.Instance().getAllUsers()) {
				if(us.getUsername().equals(reciverID))
				return Users.Instance().respondToFlag(reciverID, senderID, text);
			}
			
			
			return null;
		
		}
	
	

	
	
	
	@POST
	@Path("/solveFlag")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Message solveFlag(@QueryParam("option") String option, Message m){
		
	//System.out.println("U restu za solve flag");
	//System.out.println(option);
//	System.out.println(m.getContent());
		 String array[] = m.getContent().split("\n");
		 String subid = array[0].split("SubForum ID : ")[1];
		 subid=subid.trim();
		 String themeid="";
		 String commentid= "";
		 String parentid = "";
		 String userid = ""; // Poslao je flag
		 
		 
		// System.out.println(m.getContent());
		 
		 if(array[1].indexOf("Theme ID : ") != -1){
				 themeid = array[1].split("Theme ID : ")[1];
				 themeid= themeid.trim();
		}else{
			userid= array[1].split("User : ")[1];
			userid= userid.trim();
		}
		 
		 
		 if(userid ==""){
			 if(array[2].indexOf("Comment ID : ") != -1){
				 commentid = array[2].split("Comment ID : ")[1];
				 commentid= commentid.trim();
			 }else{
				 userid= array[2].split("User : ")[1];
					userid= userid.trim();
		 	}
		 }
		 
		 
		 if(userid==""){
			 userid= array[5].split("User : ")[1];
				userid= userid.trim();
			 parentid= array[4].split("Parent ID : ")[1];
			 parentid= parentid.trim();
		 }
		 
	//	 System.out.println(subid);
	//	 System.out.println(themeid);
	//	 System.out.println(commentid);
	//	 System.out.println(userid);
	//	 System.out.println(option);
		
		boolean b = true;
	if(option.equals("Warn")){
	//	System.out.println("U WARNU");
		
		
		if(commentid != ""){
			
	//		System.out.println("U warnu za komentare");
			
			for (SubForum sub : Forum.Instance().getAllSubForums()) {
				if(sub.getName().equals(subid)){
					if(sub.getThemes().containsKey(themeid)){
					 String username=	sub.getThemes().get(themeid).findComment(commentid).getAuthor().getUsername();
					 String text = "We have been informed that you have left a comment that isn't fully appropriate with our standards so we are sending you a WARNING, please be wary of the things you post on our forum  ";
					 respondToFlag(username, m.getReceiverID() , text );
					 respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a comment has been solved and the author has been warned");
					 b=false;

					}
						
				}
			}
			if(b)
			respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a comment has been solved and the author has been warned");
			
			return null;
		}
		
		if(themeid != ""){
			
		//	System.out.println("U warnu za teme");
				for (SubForum sub : Forum.Instance().getAllSubForums()) {
					//System.out.println(sub.getName());
					//System.out.println(subid);
					if(sub.getName().equals(subid)){
					//	System.out.println("Kako to da ovde ne dolazim ????");
						if(sub.getThemes().containsKey(themeid)){
						 String username=	sub.getThemes().get(themeid).getAuthor().getUsername();
						 String text = "We have been informed that you have made a theme that isn't fully appropriate with our standards so we are sending you a WARNING, please be wary of the things you post on our forum  ";
						 respondToFlag(username, m.getReceiverID() , text );
						 respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a theme has been solved and the author has been warned");
						 b=false;

						}
							
					}
				}
			//	System.out.println("NISI VALJDA TU :( ");
				if(b)
				respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a theme has been solved and the author has been warned");
			
			
			
			return null;
		}
			
		//System.out.println("U warnu za subForume");
				for (SubForum sub : Forum.Instance().getAllSubForums()) {
					if(sub.getName().equals(subid)){
						// String username= sub.getMainModerator().getUsername();
						 String text = "We have been informed that you have made a subForum that isn't fully appropriate with our standards so we are sending you a WARNING, please be wary of the things you post on our forum  ";
						 respondToFlag("admin", m.getReceiverID() , text );
						 respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a subForum has been solved and the author has been warned");
						 b=false;
					}
				}
				if(b)
				respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a subForum has been solved and the author has been warned");
			
			
			
		
		return null;
		
	}else if(option.equals("Delete")){
		
		if(commentid != ""){
			for (SubForum sub : Forum.Instance().getAllSubForums()) {
				if(sub.getName().equals(subid)){
					if(sub.getThemes().containsKey(themeid)){
						Comment c = new Comment();
						c.setId(UUID.fromString(commentid));
						c.setParentID(parentid);
						Forum.Instance().removeComment(subid, themeid, c);
						
					 String username=	sub.getThemes().get(themeid).findComment(commentid).getAuthor().getUsername();
					 String text = "We have been informed that you have left a comment that isn't fully appropriate with our standards so we are sending you a WARNING and DELETING the comment, please be wary of the things you post on our forum  ";
					 respondToFlag(username, m.getReceiverID() , text );
					 respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a comment has been solved and the comment has been deleted while the author has been warned");
					 b=false;

					}
						
				}
			}
			if(b)
			respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a comment has been solved and the comment has been deleted while the author has been warned");
			
			return null;
		}
		
		if(themeid != ""){
			
	
				for (SubForum sub : Forum.Instance().getAllSubForums()) {
					if(sub.getName().equals(subid)){
						if(sub.getThemes().containsKey(themeid)){
							 String username=	sub.getThemes().get(themeid).getAuthor().getUsername();
							Forum.Instance().removeTheme(sub.getThemes().get(themeid));
						
						 String text = "We have been informed that you have made a theme that isn't fully appropriate with our standards so we are sending you a WARNING and DELETING the comment, please be wary of the things you post on our forum  ";
						 respondToFlag(username, m.getReceiverID() , text );
						 respondToFlag(userid, m.getReceiverID() , "We are glad to inform you that your recent flag of a theme has been solved and the theme has been deleted while the author has been warned");
						 b=false;

						}
							
					}
				}
				if(b)
				respondToFlag(userid, m.getReceiverID() ,  "We are glad to inform you that your recent flag of a theme has been solved and the theme has been deleted while the author has been warned");
			
			
			
			return null;
		}
			
			
				for (SubForum sub : Forum.Instance().getAllSubForums()) {
					if(sub.getName().equals(subid)){
						 String username= sub.getMainModerator().getUsername();
							try {
								Forum.Instance().removeSubForum(sub);
							} catch (NoSubForumException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 String text = "We have been informed that you have made a subForum that isn't fully appropriate with our standards so we are sending you a WARNING and DELETING the comment, please be wary of the things you post on our forum  ";
						 respondToFlag(username, m.getReceiverID() , text );
						 respondToFlag(userid, m.getReceiverID() ,  "We are glad to inform you that your recent flag of a subForum has been solved and the theme has been deleted while the author has been warned");
						 b=false;
					}
				}
				if(b)
				respondToFlag(userid, m.getReceiverID() ,  "We are glad to inform you that your recent flag of a subForum has been solved and the theme has been deleted while the author has been warned");
			
			
			
		
		return null;
		
		
		
		
		
	}else{
		
		respondToFlag(userid, m.getReceiverID() , "We are sorry to inform you that your recent flag has been rejected");
		return null;
	}
		
		
	
	
	}
	
	
	
	
	
	
	
	
	@PUT
	@Path ("/addFavThemeForum")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public User addFavThembtoUser(@PathParam("subForumID") String subForumID, @QueryParam("themeID") String themeID, User u){
		
		for (SubForum sub : Forum.Instance().getAllSubForums()) {
			if(sub.getName().equals(subForumID))
				 for (Theme t : sub.getThemes().values()) {
					 if(t.getId().toString().equals(themeID)){
						 Users.Instance().followTheme(u, t);
					 }
				}
		}
	
		
		return null;
	}
	
	
	
	@PUT
	@Path ("/addFavComment")
	@Produces (MediaType.APPLICATION_JSON)
	public User addFavComment(@QueryParam("subForumID") String subForumID, @QueryParam("themeID") String themeID, @QueryParam("userID") String userID , @QueryParam("commentID") String commentID){

		System.out.println("U restu za add fav comm");
		for (SubForum sub : Forum.Instance().getAllSubForums()) {
			if(sub.getName().equals(subForumID))
				 for (Theme t : sub.getThemes().values()) {
					 if(t.getId().toString().equals(themeID)){
						 System.out.println("zapoceo dodavanje sada ce u usere");
						 Users.Instance().followComment(userID, subForumID, themeID, commentID);
					 }
				}
			
		}
		return null;
	}
	
	
	
	@PUT
	@Path ("/addAdmin")
	@Produces (MediaType.APPLICATION_JSON)
	public User addAdmin(@QueryParam("userID") String userID){
	
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(userID)){
				// System.out.println("tu sammm 222 !");
				return Users.Instance().addAdmin(us);
			}
		}
		
		return null;
	}
	
	@PUT
	@Path ("/addMod")
	@Produces (MediaType.APPLICATION_JSON)
	public User addMod(@QueryParam("userID") String userID){
	
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(userID)){
				// System.out.println("tu sammm 222 !");
				return Users.Instance().addMod(us);
			}
		}
		
		return null;
	}
	
	
	@PUT
	@Path ("/removeUser")
	@Produces (MediaType.APPLICATION_JSON)
	public User removeUser(@QueryParam("userID") String userID){
	
		
		if(userID.equals("admin"))
			return null;
		
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(userID)){
				// System.out.println("tu sammm 222 !");
				 try {
					Users.Instance().removeUser(us);
				} catch (NoSuchUsernameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	
	@PUT
	@Path ("/removeTitle")
	@Produces (MediaType.APPLICATION_JSON)
	public User removeTitle(@QueryParam("userID") String userID){
	
		if(userID.equals("admin"))
			return null;
		
		
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(userID)){
				// System.out.println("tu sammm 222 !");
				return Users.Instance().removeTitle(us);
			}
		}
		
		return null;
	}
	
	
	
	@POST
	@Path("/editTheme")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editTheme(Theme t){
		
		t.setEdit(true);
		Forum.Instance().editTheme(t);
		
	}
	
	
	@POST
	@Path("/editUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editUser(User u){
	//	System.out.println("Rest edit user :D");
		Users.Instance().editUser(u);
		
	}
	
	
	@POST
	@Path("/editComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editComment(@QueryParam ("subForumID") String s, @QueryParam ("themeID") String t, Comment c, @QueryParam ("userID") String u){
		c.setEdit(true);
		c.setChanged(true);
		Forum.Instance().editComment(s, t, c, u);
		
	}
	
	
	@POST
	@Path("/logicalDeleteComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logicalDeleteComment(@QueryParam ("subForumID") String s, @QueryParam ("themeID") String t, Comment c){
		c.setEdit(true);
		c.setChanged(true);
		Forum.Instance().logicalDeleteComment(s, t, c);
		
	}
	
	
	@POST
	@Path("/editSubForum")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editSubForum(SubForum s){
		try {
			s.setEdit(true);
			Forum.Instance().editSubForum(s);
		} catch (NoSubForumException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Nema tog foruma, problem kod rest editSubForum");
		}
		
	}
	
	
	
	
	@DELETE
	@Path ("/RemoveSubForum/{subForumID}")
	@Produces (MediaType.APPLICATION_JSON)
	public void deleteSubForum(@PathParam("subForumID") String subForumID){
		
		SubForum sf = Forum.Instance().findForum(subForumID);
		if(sf != null){
			
				try {
					Forum.Instance().removeSubForum(sf);
				
				} catch (NoSubForumException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("Greska kod brisanja SubForuma");
					
				}
			
			}
	}
	
	
	
	
	@DELETE
	@Path ("/RemoveTheme/{subForumID1}")
	@Produces (MediaType.APPLICATION_JSON)
	public void deleteTheme(@QueryParam("subForumID") String subForumID, @QueryParam("themeID") String themeID ){
		
		System.out.println("Usao u brisanje");
		System.out.println(subForumID);
		SubForum sf = Forum.Instance().findForum(subForumID);
		if(sf != null){
			System.out.println("Brisem");
				for (Theme t : sf.getThemes().values()) {
					//System.out.println(t.getId().toString());
				//	System.out.println("A sad moj :");
					//System.out.println(themeID);
					
					if(t.getId().toString().equals(themeID)){
						Forum.Instance().removeTheme(t);
						System.out.println("Usao da ga obrise");
						return;
					}
				}
			
			}
			
	}
	
	
	

	@POST
	@Path ("/RemoveComment")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public void deleteComment(@QueryParam("subForumID") String subForumID, @QueryParam("themeID") String themeID, Comment c ){
		
		//System.out.println("Usao u brisanje");
		SubForum sf = Forum.Instance().findForum(subForumID);
		if(sf != null){
		//	System.out.println("Brisem");
				for (Theme t : sf.getThemes().values()) {
					//System.out.println(t.getId().toString());
				//	System.out.println("A sad moj :");
					//System.out.println(themeID);
					
					if(t.getId().toString().equals(themeID)){
						Forum.Instance().removeComment(subForumID, themeID, c);
					//	System.out.println("Usao da ga obrise");
						return;
					}
				}
			
			}
			
	}
	
	
	
	@POST
	@Path("/reviewLike")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public String review(@QueryParam ("userID") String  userID, Review r ){
		
		//System.out.println("Rev : "+ r.getId() );
		
		
		if(userID == null || r == null)
			return "{\"value\":\"NEMA PODATAKA\"} ";
		
		
		User u=null;
		r.setType(RewType.LIKE);
		
		
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(userID))
				u=us;
		}
		
		if(u==null)
			return "{\"value\":\"NEMA USERA\"}";
		
		
		
		//System.out.println("Prosao usera :D");
		//System.out.println(r.getId());
	
			 if(Users.Instance().addReview(u, r)){
			//	System.out.println("treba add");
				 boolean b= Forum.Instance().review(r);
				 if(b)
					return "{\"value\":\"DODAT\"}";
					
				 return "{\"value\":\"DODAT\"}";
			}
			 else{
				// System.out.println("treba remove");
				 boolean b =  Forum.Instance().removeReview(r);
				 
				 if(b)
					return "{\"value\":\"IZBACEN\"}";
					 
				 return "{\"value\":\"IZBACEN\"}";
			 }
	
	}
	
	@POST
	@Path("/reviewDislike")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public String reviewDislike(@QueryParam ("userID") String  userID, Review r ){
		
		
		if(userID == null || r == null)
			return "{\"value\":\"NEMA PODATAKA\"}";
		
		r.setType(RewType.DISLIKE);
		User u=null;
		
		
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(userID))
				u=us;
		}
		
		if(u==null)
			return "{\"value\":\"NEMA USERA\"}";
		

		if(Users.Instance().addReview(u, r)){
			if(Forum.Instance().review(r))
				return "{\"value\":\"DODAT\"}";
			return "{\"value\":\"DODAT\"}";
			
			}
		 else{
			 if(Forum.Instance().removeReview(r))
				 return "{\"value\":\"IZBACEN\"}";
			 return "{\"value\":\"IZBACEN\"}";
		 }		
					
		

	}
	
	@GET
	@Path("/recommended")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Theme> recommended(){
		
		return Forum.Instance().getMostLiked();
	}
	

	
	
	@GET
	@Path("/getFollowedSubForums")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SubForum> getFollowedSubForums(@QueryParam("userID") String userID){
		
		return Users.Instance().getFollowedSubForums(userID);
	}
	
	
	@GET
	@Path("/getReviews")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Review> getReviews(@QueryParam("userID") String userID){
		
		return Users.Instance().getReviews(userID);
	}
	
	
	
	@GET
	@Path("/getFollowedThemes")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Theme> getFollowedThemes(@QueryParam("userID") String userID){
		
		return Users.Instance().getFollowedThemes(userID);
	}
	
	@GET
	@Path("/getFollowedComments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getFollowedComments(@QueryParam("userID") String userID){
		
		return Users.Instance().getFollowedComments(userID);
	}
	
	
	@GET
	@Path("/getMessages")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Message> getMessages(@QueryParam("userID") String userID){
	//	System.out.println("u getmessages");
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getUsername().equals(userID)){
			//	System.out.println("Nasao mi je usera");
				return us.getMessages();
			}
		}
		return null;
		
	}
	
	
	
	@POST
	@Path("/readMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message readMessage(Message m){
	//	System.out.println("u getmessages");
		
		m.setRead(true);
		return Users.Instance().readMessage(m);
		
	}
	
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Search search(String s){
		
		Search sr = new Search();
		
		return sr.doSearch(s);
	}
	
	
	@GET
	@Path("/korisnik")
	@Produces(MediaType.APPLICATION_JSON)
	public User korisnik(){	
		
		
		//System.out.println("Korisnik TEST !");
		
		User u = new User(); 
		u.setEmail("milan@test.com");
		u.setFirstName("milan");
		u.setLastName("stankovic");
		u.setPassword("pass");
		u.setPhone("1233");
		u.setRole(UserType.REGULAR);
		u.setUsername("user1");
		Date d = new Date();
		u.setRegistrationDate(d);
	//	System.out.println("REST USER !");
		
		//System.out.println("Korisnika treba poslati !");
		
		return u;
	}
	
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SubForum pocetak(){	
		
		SubForum s = new SubForum();
		s.setDescription("desc");
		s.setName("hii");
		User mod = new User();
		mod.setUsername("Modmod");
		s.setMainModerator(mod);
		s.setIcon(null);
		
		//System.out.println("REST TEST !");
		return s;
	}
	
	*/
	
	@GET
	@Path("/oneUser")
	public User oneUser(@QueryParam("userID") String userID){
		
		for (User u : Users.Instance().getAllUsers()) {
			if(u.getUsername().equals(userID))
				return u;
		}
		
		return null;
	}
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response  login(User u)
	{
		
		//System.out.println("Testiranje");
		HashMap<String, Object> response = new HashMap<String, Object>();	
		ArrayList<User> korisnici = (ArrayList<User>) Users.Instance().getAllUsers();
		
		String korisnickoIme = u.getUsername();
		String lozinka = u.getPassword();
		
		for (User k : korisnici) {
			if (k.getUsername().equals(korisnickoIme) && k.getPassword().equals(lozinka)) {
				response.put("user", k);
				response.put("token", createJWT(korisnickoIme, lozinka, k.getRole().toString()));
				
				//System.out.println("U petlji");
				return Response.ok(response, MediaType.APPLICATION_JSON).build();
			}
		}
		
	//	System.out.println("Vracam NULL");
		return null;
	}
	
	
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public  Response  logout(User u)
	{
		
		//System.out.println("Logout like a boss");
		
		ArrayList<User> korisnici = null;
	//	System.out.println("My role is :");
	//	System.out.println(u.getRole());
		
		switch (u.getRole()) {
		case ADMIN:
			korisnici = (ArrayList<User>) Users.Instance().getActiveAdmins();
			break;
		case MODERATOR:
			korisnici = (ArrayList<User>) Users.Instance().getActiveModerators();
			break;
		case REGULAR:
			korisnici = (ArrayList<User>) Users.Instance().getNormalActiveUsers();
		break;
		
		default:
			korisnici = null;
			System.out.println("Nije ni jedan tip o.O");
			break;
		}
		
		String korisnickoIme = u.getUsername();
		
		
		for (User k : korisnici) {
			if (k.getUsername().equals(korisnickoIme)) {
				
				try {
					Users.Instance().removeActiveUser(k);
				} catch (NoSuchUsernameException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					
					System.out.println("nema korisnika ipak o.O, u RESTU/LOGOUT");
				}
				
				URI uri = null;
				try {
					uri = new URI("index.jsp");
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("Greska kod logouta u restu s");
				}
		
			 return Response.temporaryRedirect(uri).build();
			}
		}
		
	//	System.out.println("Vracam NULL");
		return null;
	}
	
	
	
	
	private static String createJWT(String korisnickoIme, String lozinka, String uloga) {
		 
		//The JWT signature algorithm we will be using to sign the token
		Key key = MacProvider.generateKey();
		 
		 Claims claims = Jwts.claims().setSubject(korisnickoIme);
	        claims.put("korisnickoIme", korisnickoIme);
	        claims.put("role", uloga);
	        
		  //Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setClaims(claims)
		                                .signWith(SignatureAlgorithm.HS256, key);
		  
		 //Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
		}
	private boolean prazno(User u ){
		
		if(u.getFirstName().isEmpty() || u.getLastName().isEmpty() || u.getEmail().isEmpty() || u.getPassword().isEmpty() || u.getUsername().isEmpty() )
				return true;
		
		return false;
	}
	
	
	private boolean praznoSub(SubForum s ){
		
		if(s.getName().isEmpty() || s.getDescription().isEmpty() || s.getName().isEmpty() || s.getRules().isEmpty() || s.getMainModerator()==null){
			for (SubForum sf : Forum.Instance().getAllSubForums()) {
				if(sf.getName().equals(s.getName()))
					return false;
			}
			return true;
			
		}else
			return false;
	}
	
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User register(User u)
	{
		Date d = new Date();
		u.setFollowedForums(new ArrayList<SubForum>());
		u.setFollowedThemes(new ArrayList<Theme>());
		u.setMessages(new ArrayList<Message>());
		u.setRegistrationDate(d);
		u.setRole(UserType.REGULAR);
		if(prazno(u))
			return null;
		try {
			Users.Instance().addNewUser(u);
		} catch (UsernameExistsException e) {
			return null;
		}
		return u;
	}
	
	
	
	
	@POST
	@Path("/addSubForum")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SubForum addSubForum(SubForum s)
	{
		Date d = new Date();
		HashMap<String, User> us = new HashMap<String, User>();
		//s.set
		us.put(s.getMainModerator().getUsername(), s.getMainModerator());
		s.setModerators(us);
		s.setThemes(new HashMap<String,Theme>());
		s.setEdit(true);
		if(praznoSub(s))
			return null;
		
		try {
			return	Forum.Instance().addNewSubForum(s);
		//	System.out.println("Uselo je dodavanje subforuma");
		} catch (SubForumNameExistsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Failovao je dodavanje subforuma");
			return null;
		}
		
	
	}
	
	
	@POST
	@Path("/addTheme")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Theme addTheme(Theme t)
	{
		//System.out.println("Dodajem temu u restu");
		//System.out.println(t.getTitle());
		Date d = new Date();
		
		t.setDateOfCreation(d);
		t.setComments(new HashMap<String, Comment>());
		t.setLikes(0);
		t.setDislikes(0);
		t.setId(UUID.randomUUID());
		t.setEdit(true);
		//SubForum sf = t.getForum();
		
		return  Forum.Instance().addTheme(t);
		
	}
	
	
	
	@POST
	@Path("/flagSubForum")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Flag flagSubForum(@QueryParam("userID") String userID ,Flag f)
	{
		//System.out.println("Dodajem temu u restu");
		//System.out.println(t.getTitle());
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d = new Date();
		String date = df.format(d);
	//	System.out.println(date);
		String s = "SubForum ID : " + f.getSubForum().getName() + System.lineSeparator()  + "User : " + 	userID +  "\n" + "Text : " + f.getMessage() +  System.lineSeparator() + "Date : " + date;
	//	System.out.println(s);
		
		
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getRole() == UserType.ADMIN){
				us.sendFlag(s);
			}
		}
		
	
		
		return null;
		//return  Forum.Instance().addTheme(t);
		
	}
	
	
	
	@POST
	@Path("/flagTheme")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Flag flagTheme(@QueryParam("userID") String userID ,Flag f)
	{
		//System.out.println("Dodajem temu u restu");
		//System.out.println(t.getTitle());
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d = new Date();
		String date = df.format(d);
		//System.out.println(date);
		String s = "SubForum ID : " + f.getSubForum().getName() + System.lineSeparator()  + "Theme ID : " +f.getTheme().getId().toString() + System.lineSeparator() +  "User : " + 	userID +  "\n" + "Text : " + f.getMessage() +  System.lineSeparator() + "Date : " + date;
		//System.out.println(s);
		
		f.getSubForum().getMainModerator().sendFlag(s);
		
		for (User us : Users.Instance().getAllUsers()) {
			
			if(us.getRole() == UserType.ADMIN){
				us.sendFlag(s);
			}
		}
		
		
		
	
		
		return null;
		//return  Forum.Instance().addTheme(t);
		
	}
	
	

	@POST
	@Path("/flagComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Flag flagComment(@QueryParam("userID") String userID ,Flag f)
	{
		//System.out.println("Dodajem temu u restu");
		//System.out.println(t.getTitle());
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d = new Date();
		String date = df.format(d);
	//	System.out.println(date);
		String s = "SubForum ID : " + f.getSubForum().getName() + System.lineSeparator()  + "Theme ID : " +f.getTheme().getId().toString() + System.lineSeparator() + "Comment ID : " +f.getComment().getId().toString() +System.lineSeparator() + "Comment content : "+ f.getComment().getText() + System.lineSeparator() + "Parent ID : " + f.getComment().getParentID() +System.lineSeparator() +  "User : " + 	userID +  "\n" + "Text : " + f.getMessage() +  System.lineSeparator() + "Date : " + date;
	//	System.out.println(s);
		
		
		for (User us : Users.Instance().getAllUsers()) {
			if(us.getRole() == UserType.ADMIN){
				us.sendFlag(s);
			}
		}
		
		f.getSubForum().getMainModerator().sendFlag(s);
	
		
		return null;
		//return  Forum.Instance().addTheme(t);
		
	}
	
	
	
	
	@POST
	@Path("/addComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment addComment(@QueryParam ("themeID") String themeID, @QueryParam ("subForumID") String subForumID, Comment c)
	{
	//	System.out.println("Dodajem temu u restu");
		Date d = new Date();
		c.setDateOfCreation(d);
		c.setLastEdited(d);
		c.setDislikes(0);
		c.setLikes(0);
		c.setUUID(UUID.randomUUID());
		c.setStatus(Status.AKTIVAN);
		c.setComments(new ArrayList<Comment>());
		c.setCommentType(CommentType.THEME);
		c.setEdit(true);
		c.setChanged(false);
		//SubForum sf = t.getForum();
		
		//System.out.println("U dodavanju komentara u restu ");
		
		SubForum sf=  Forum.Instance().getSubForum(subForumID);
		if(sf ==null)
			return null;
		
		
	
		Theme t= null;
		
		t= sf.findTheme(themeID);
		
		if(t==null)
			return null;
		
		
		
		return  Forum.Instance().addComment(t, c);
		
	}
	
	
	@POST
	@Path("/addCommentToComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment addCommentToComment(@QueryParam ("themeID") String themeID, @QueryParam ("subForumID") String subForumID, Comment c)
	{
	//	System.out.println("Dodajem temu u restu");
		Date d = new Date();
		c.setDateOfCreation(d);
		c.setLastEdited(d);
		c.setDislikes(0);
		c.setLikes(0);
		c.setUUID(UUID.randomUUID());
		c.setStatus(Status.AKTIVAN);
		c.setComments(new ArrayList<Comment>());
		c.setCommentType(CommentType.COMMENT);
		c.setEdit(true);
		c.setChanged(false);
		//SubForum sf = t.getForum();
		
	//	System.out.println("U dodavanju komentara na komentar u restu ");
	//	System.out.println(subForumID);
	//	System.out.println(themeID);
		
		SubForum sf=  Forum.Instance().getSubForum(subForumID);
		if(sf ==null)
			return null;
	//	System.out.println("Nasao sub u restu");
		
		Theme t= null;
		
		t= sf.findTheme(themeID);
		
		if(t==null)
			return null;
		
		//System.out.println("Nasao temu u restu");
		
		Comment c1 = t.findComment(c.getParentID());
		
		//System.out.println("Evo me pronalaska komentara i teks mu je : " +c1.getText());
		
		if(c1==null)
			return null;
		//System.out.println("Puce kod komentara");
		
		return  Forum.Instance().addCommentToComment(t,c1, c);
		
	}
	
	
	
	
	
	
	
	
	@GET
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRegister()
	{
		
			URI uri = null;
			try {
				uri = new URI("/forum161/register.html");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		 return Response.temporaryRedirect(uri).build();
		
	}
	
	
	
	@GET
	@Path("/allSubForums")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SubForum> allSubForums()
	{
		//System.out.println("Trazi subove");
		for (SubForum s : Forum.Instance().getAllSubForums()) {
		//	System.out.println(s.getName());
		}
		return Forum.Instance().getAllSubForums();
	}	
	
	@GET
	@Path("/allActiveUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listActive()
	{
		return Users.Instance().getActive();
	}
	
	@GET
	@Path("/allExistingUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listAll()
	{
		return Users.Instance().getAllUsers();
	}
	
	@GET
	@Path("/activeNormalUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Object activeNormal()
	{
		return Users.Instance().getNormalActiveUsers();
	}
	
	@GET
	@Path("/activeModeators")
	@Produces(MediaType.APPLICATION_JSON)
	public Object activeModerators()
	{
		return Users.Instance().getActiveModerators();
	}
	
	@GET
	@Path("/activeAdmins")
	@Produces(MediaType.APPLICATION_JSON)
	public Object activeAdmins()
	{
		return Users.Instance().getActiveAdmins();
	}
	
	@GET
	@Path("/exsitingNormal")
	@Produces(MediaType.APPLICATION_JSON)
	public Object exsitingNormal()
	{
		return Users.Instance().getNormalExistingUsers();
	}
	
	@GET
	@Path("/existingModerators")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listActiveNormal()
	{
		return Users.Instance().getExistingModerators();
	}
	
	@GET
	@Path("/existingAdmins")
	@Produces(MediaType.APPLICATION_JSON)
	public Object existingAdmins()
	{
		return Users.Instance().getExistingAdmins();
	}
	

}
