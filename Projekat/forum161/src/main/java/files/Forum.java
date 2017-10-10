package files;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import exceptions.ExistingCommentException;
import exceptions.ExistingThemeException;
import exceptions.NoSubForumException;
import exceptions.NoThemeException;
import exceptions.SubForumNameExistsException;
import model.Comment;
import model.ForumObserver;
import model.Review;
import model.RewForType;
import model.RewType;
import model.SubForum;
import model.Theme;
import model.User;

public class Forum extends Observable {

	
	private static Forum instance = null;
	private HashMap<String,SubForum> allSubForums;
	
	private Forum(){
		
		HashMap<String,SubForum> load = (HashMap<String,SubForum>)FileUtility.ReadObject(FileType.Forum);
		if(load == null){
			allSubForums = new HashMap<String,SubForum>();
			}else {
				allSubForums = load;
				//System.out.println("U instantiate je dobio nesto");
			}
		this.addObserver(new ForumObserver());
	}
	
	
	
	
	
	public ArrayList<Theme> getMostLiked(){
		ArrayList<Theme> best = new ArrayList<Theme>();
		ArrayList<Theme> all = new ArrayList<Theme>();
		Date d = new Date();
		
	//	System.out.println("U Get most liked :D");
		
		
		for (SubForum s : allSubForums.values()) {
			for (Theme t : s.getThemes().values()) {
				//System.out.println(t.getDateOfCreation().getTime());
			//	System.out.println(d.getTime());
			//	System.out.println(d.getTime()-t.getDateOfCreation().getTime());
				if (  d.getTime() - t.getDateOfCreation().getTime() <= 24*60*60*1000) { // u zadnja 24h
					all.add(t);
			//		System.out.println("evo dodao sam nesto :D");
				}
			}
		}
		Collections.sort(all, Collections.reverseOrder());
		if(all.size() >=3){
			best.add(all.get(0));
			best.add(all.get(1));
			best.add(all.get(2));
		}else
			best=all;
		
		return best;
	}
	
	public static Forum Instance()
	{
		if(instance == null)
			instance = new Forum();
		return instance;
	}
	
	
	public SubForum getSubForum(String s){
		
		return allSubForums.get(s);
	}
	
	public Collection<SubForum> getAllSubForums() // vracam listu
	{
		
		//System.out.println("U getallSubs je ");
		ArrayList<SubForum> subF= new ArrayList<SubForum>();
		for (SubForum sf : allSubForums.values()) {
			subF.add(sf);
		}
		return subF;
	}
	
	public SubForum findForum(String s){
		
		if(allSubForums.containsKey(s))
			return allSubForums.get(s);
		
		return null;
		
	}
	
	
	public SubForum addNewSubForum(SubForum sf) throws SubForumNameExistsException
	{
		if(!allSubForums.containsKey(sf.getName()))
		{
			allSubForums.put(sf.getName(), sf);
			setChanged();
			notifyObservers(allSubForums);
			return sf;
		}else
			throw new SubForumNameExistsException();
	}
	
	public void editSubForum(SubForum sf) throws NoSubForumException
	{
		if(allSubForums.containsKey(sf.getName()))
		{
			allSubForums.remove(sf.getName());
			allSubForums.put(sf.getName(), sf);
			setChanged();
			notifyObservers(allSubForums);
		}else
			throw new NoSubForumException();
	}
	
	
	
	
	public boolean review(Review r){
		
		if(r.getForType() == RewForType.THEME){
				for (SubForum s : allSubForums.values()) {
				
					if(s.getThemes().containsKey(r.getId())){
						if(r.getType()== RewType.LIKE){
							s.getThemes().get(r.getId()).plusLike();
							
							setChanged();
							notifyObservers(allSubForums);
							return true;
						}else{
							s.getThemes().get(r.getId()).plusDislike();
							setChanged();
							notifyObservers(allSubForums);
							return true;
						}
					}
				}
			}else{

				for (SubForum s : allSubForums.values()) {
					for (Theme t : s.getThemes().values()) {
						
						for (Comment co : t.getComments().values()) {
							if(co.getId().toString().equals(r.getId())){
								
								if(r.getType()==RewType.LIKE){
									t.getComments().get(r.getId()).plusLike();
									setChanged();
									notifyObservers(allSubForums);
									return true;
								}else{
									t.getComments().get(r.getId()).plusDislike();
									setChanged();
									notifyObservers(allSubForums);
									return true;
								}
							}
							
						}
						
						
							//System.out.println("Evo me u ovom elsu");
							ArrayList<Comment> alc = new ArrayList( t.getComments().values());
							
							if(recursionPlus(alc, r))
								return true;
						
					} 
				}
			}
				
			
			return false;
		}
	
	
	boolean recursionPlus(ArrayList<Comment> myList, Review r) {
	//	System.out.println("Usao u ovu rekurziju");
	//	System.out.println(myList.size());
		for (Comment c : myList) {
			
			
			if (c.getId().toString().equals(r.getId())) {
			//	System.out.println("Nasao sam komentar u komentaru gde ide like :D");
				if (r.getType() == RewType.LIKE) {
					c.plusLike();
					setChanged();
					notifyObservers(allSubForums);
					return true;
				} else {
					c.plusDislike();
					setChanged();
					notifyObservers(allSubForums);
					return true;
				}

			}
			
			
			
				for (Comment comment : c.getComments()) {
					//System.out.println(comment.getText());
					if (comment.getId().toString().equals(r.getId())) {
					//	System.out.println("Nasao sam komentar u komentaru gde ide like :D");
						if (r.getType() == RewType.LIKE) {
							comment.plusLike();
							setChanged();
							notifyObservers(allSubForums);
							return true;
						} else {
							comment.plusDislike();
							setChanged();
							notifyObservers(allSubForums);
							return true;
						}

					}
					if (comment.getComments().size() > 0) {
						//System.out.println("Treba ovde poceti opet rekurziju");
						recursionPlus(comment.getComments(), r);
					}
				}
				
			
		}
		return false;
	}
	
	
	
	boolean recursionMinus(ArrayList<Comment> myList, Review r) {
		
		
		for (Comment c : myList) {
			
			if (c.getId().toString().equals(r.getId())) {
				
				if (r.getType() == RewType.LIKE) {
					c.minusLike();
					setChanged();
					notifyObservers(allSubForums);
					return true;
				} else {
					c.minusDislike();
					setChanged();
					notifyObservers(allSubForums);
					return true;
				}
				
			}
			
			
				for (Comment comment : c.getComments()) {

					if (comment.getId().toString().equals(r.getId())) {
					//	System.out.println("Nasao sam komentar u komentaru gde ide like :D");
						if (r.getType() == RewType.LIKE) {
							comment.minusLike();
							setChanged();
							notifyObservers(allSubForums);
							return true;
						} else {
							comment.minusDislike();
							setChanged();
							notifyObservers(allSubForums);
							return true;
						}

					}
					
					if (comment.getComments().size() > 0) {
					//	System.out.println("Treba ovde poceti opet rekurziju");
						recursionMinus(comment.getComments(), r);
					}
				}
			
		}
		return false;
	}
	
	
	boolean recursiveRemove(ArrayList<Comment> myList, Comment com ) {
		for (Comment c : myList) {
			
			if (c.getId().toString().equals(com.getParentID())) {
				for (Comment c1 : c.getComments()) {
					if( c1.getId().toString().equals(com.getId().toString()) ){
				//		System.out.println("Upao sam da bas brisem skrooooooz");
						c.getComments().remove(c1);
						return true;
					}
				}
			}
			
				for (Comment comment : c.getComments()) {

					if (comment.getId().toString().equals(com.getParentID())) {
						for (Comment c1 : comment.getComments()) {
							if( c1.getId().toString().equals(com.getId().toString()) ){
						//		System.out.println("Upao sam da bas brisem skrooooooz");
								comment.getComments().remove(c1);
								return true;
							}
						}
					}
					if (comment.getComments().size() > 0) 
						recursiveRemove(comment.getComments(), com);
					
				}
				
			
		}
		return false;
	}
	
	boolean recursiveAdd(ArrayList<Comment> myList, Comment com ) throws ExistingCommentException {
		//System.out.println("U rekurzivnom addu sam ");
		
		for (Comment c : myList) {
			
				for (Comment comment : c.getComments()) {

					if (comment.getId().toString().equals(com.getParentID())) {
						comment.addComment(com);
						return true;
					}
					if (comment.getComments().size() > 0) 
						recursiveAdd(comment.getComments(), com);
				}
			
		}
		return false;
	}
	
	
	

	
	
	
	
	
public boolean removeReview(Review r){
		//System.out.println("U remove review");
		if(r.getForType() == RewForType.THEME){ // TREBAO SAM NAPRAVITI DA ID OD TEME ZAVISI OD FORUMA A OD KOMENATRA OD TEME ZBOG BRZINE PRISTUPA...
			for (SubForum s : allSubForums.values()) {
			
				if(s.getThemes().containsKey(r.getId())){
					if(r.getType()== RewType.LIKE){
						s.getThemes().get(r.getId()).minusLike();
						
						setChanged();
						notifyObservers(allSubForums);
						return true;
					}else{
						s.getThemes().get(r.getId()).minusDislike();
						setChanged();
						notifyObservers(allSubForums);
						return true;
					}
				}
			}
		}else{
			for (SubForum s : allSubForums.values()) {
				for (Theme t : s.getThemes().values()) {
					
					
					
					for (Comment co : t.getComments().values()) {
						if(co.getId().toString().equals(r.getId())){
							
							if(r.getType()==RewType.LIKE){
								t.getComments().get(r.getId()).minusLike();
								setChanged();
								notifyObservers(allSubForums);
								return true;
							}else{
								t.getComments().get(r.getId()).minusDislike();
								setChanged();
								notifyObservers(allSubForums);
								return true;
							}
						}
						
					}
					
					
					
					
					//	System.out.println("Upadoh u ovaj else :D");
						ArrayList<Comment> alc = new ArrayList( t.getComments().values());
						
						if(	recursionMinus(alc, r))
							return true;
					
				} 
			}
		}
			
		
		return false;
	}
	
	
	
	public void removeTheme(Theme t){
		String st = t.getSubForum();
		
		SubForum s = allSubForums.get(st);
		
		try {
			s.removeTheme(t);
			setChanged();
			notifyObservers(allSubForums);
		} catch (NoThemeException e) {
			// TODO Auto-generated catch block
			System.out.println("Puce kod izbacivanja teme u Forumu");
		}
		
	}
	
	
	public Theme addTheme(Theme t){
		String st = t.getSubForum();
		
		SubForum s = allSubForums.get(st);
		
		try {
			
			if(t!=null && st!=null){
				s.addTheme(t);
				setChanged();
				notifyObservers(allSubForums);
				return t;
			}
		} catch (ExistingThemeException e) {
			// TODO Auto-generated catch block
			System.out.println("Puce kod dodavanja teme u Forumu");
		}
		return t;
		
	}
	
	
	public Theme editTheme(Theme t){
		String st = t.getSubForum();
		
		SubForum s = allSubForums.get(st);
		
		try {
			try {
				s.editTheme(t);
				
			} catch (ExistingThemeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			setChanged();
			notifyObservers(allSubForums);
			return t;
		} catch (NoThemeException e) {
			// TODO Auto-generated catch block
			System.out.println("Puce kod eidta teme u Forumu");
			return null;
		}
		
	}
	
	
	
	public void removeComment(String s, String t, Comment c ){
		
		
		SubForum s1 = allSubForums.get(s);
		
		if(s1 != null){
			for (Theme t1 : s1.getThemes().values()) {
				if(t1.getId().toString().equals(t)){
					if(c.getParentID().equals(t)){
				//		System.out.println("Trazi ga direktno");
						t1.removeComment(c.getId().toString());
					}else{
				//		System.out.println("Trazi ga rekurzivno");
					//	System.out.println(c.getText());
				//		System.out.println(c.getId().toString());
						ArrayList<Comment> co = new ArrayList<Comment>(t1.getComments().values());
						recursiveRemove(co,c);
					}
					setChanged();
					notifyObservers(allSubForums);
				}
			}
		}
	
	}
	
	
	
	public Comment addComment(Theme t, Comment c){
		String st = t.getSubForum();
		
		SubForum s = allSubForums.get(st);
		
		if(s != null){
				Theme t1 = s.findTheme(t.getId().toString());
				
			//	System.out.println("Nasao temu");
					try {
					//	System.out.println("Dodao komentar");
						t1.addComment(c);
						setChanged();
						notifyObservers(allSubForums);
					} catch (ExistingCommentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return c;
				}
			
		
		return null;
		
	}
	
	
	
	public Comment addCommentToComment(Theme t, Comment parentComment, Comment childComment){
		String st = t.getSubForum();
		//System.out.println("u forumu za dodavanje komentara");
		SubForum s = allSubForums.get(st);
		
		if(s != null){
				Theme t1 = s.findTheme(t.getId().toString());
				
			//	System.out.println("Nasao temu");
					try {
					//	System.out.println("Dodao komentar u komentar");
						Comment c1 = t1.findComment(childComment.getParentID());
						if(c1!=null){
						c1.addComment(childComment);
						setChanged();
						notifyObservers(allSubForums);
						}else{
							ArrayList<Comment> list = new ArrayList<>(t1.getComments().values());
							if(recursiveAdd(list,childComment))
							{
								setChanged();
								notifyObservers(allSubForums);
									
							}
						}
						
					} catch (ExistingCommentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return childComment;
				}
			
		
		return null;
		
	}
	

	
	public void editComment(String s1, String to, Comment c, String u){
		if(s1 ==null || to== null || c == null){
			return;
		}
		
		if(!allSubForums.containsKey(s1))
			return;
		SubForum s = allSubForums.get(s1);
		boolean b=true;
		if(allSubForums.containsKey(s.getName())){
			SubForum su= allSubForums.get(s.getName());
			
			if( su.getModerators().containsKey(u))
				b = false;
			
			if(su.getThemes().containsKey(to)){
				su.getThemes().get(to).editComment(c, b);
				setChanged();
				notifyObservers(allSubForums);
			}
			return;
			
			
		}
		
	}
	
	
	public void logicalDeleteComment(String s1, String to, Comment c){
		if(s1 ==null || to== null || c == null){
			return;
		}
		
		if(!allSubForums.containsKey(s1))
			return;
		SubForum s = allSubForums.get(s1);
		boolean b=true;
		if(allSubForums.containsKey(s.getName())){
			SubForum su= allSubForums.get(s.getName());
		
			if(su.getThemes().containsKey(to)){
				su.getThemes().get(to).logicalDeleteComment(c);
				setChanged();
				notifyObservers(allSubForums);
			}
			return;
			
			
		}
		
	}
	
	
	
	
	
	
	public boolean removeSubForum(SubForum sf)  throws NoSubForumException { // dzabe sam pravio drugi -.-
		
		//System.out.println("Uleteh u brisanje");
		//System.out.println(allSubForums.size());
		
		if(allSubForums.containsKey(sf.getName()))
		{
			allSubForums.remove(sf.getName());
		//	System.out.println("Znam sta da brisem");
			
			setChanged();
			notifyObservers(allSubForums);
			return true;
		}else
			throw new NoSubForumException();
	
		
	}
	
}
