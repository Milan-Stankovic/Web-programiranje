package files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import model.SubForum;
import model.User;

public class FileUtility {
	
	public static String usersFileName = "/Users.tmp";
	public static String forumFileName = "/Forum.tmp";
	
	public static String GetServerPath()
	{
		return new File("").getAbsolutePath();
	}
	
	public static boolean SaveObject(Object o, FileType ft)
	{
		String fileName = GetServerPath();
		switch(ft)
		{
			case User: fileName += usersFileName; break;
			case Forum: fileName += forumFileName; break;
		}
		return SaveToFile(o,fileName);
	}
	
	private static boolean SaveToFile(Object o, String filePath)
	{
	    FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			new File(filePath);
			return false;
		}
	    ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(fos);
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			return false;
		}
	    return true;
	}
	
	
	public static Object ReadObject(FileType ft)
	{
		String fileName = GetServerPath();
		if(ft !=null)
		switch(ft)
		{
			case User: 
				fileName += usersFileName;
				return (HashMap<String,User>) ReadFromFile(fileName);
			case Forum: 
				fileName += forumFileName; 
				return (HashMap<String,SubForum>) ReadFromFile(fileName);
			
		}
		return null;
	}
	
	private static Object ReadFromFile(String filePath)
	{
		
		FileInputStream fi = null;
		ObjectInputStream oi = null;
		Object retVal = null;
		try {
			fi = new FileInputStream(filePath);
			oi = new ObjectInputStream(fi);
			retVal = oi.readObject();
			oi.close();
			fi.close();
		} catch (FileNotFoundException e) {
			new File(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
