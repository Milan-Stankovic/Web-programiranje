package model;

import java.util.Observable;
import java.util.Observer;

import files.FileType;
import files.FileUtility;

public class ForumObserver implements Observer{
	
	@Override
	public void update(Observable arg0, Object arg1) {
		FileUtility.SaveObject(arg1, FileType.Forum);
	}


}
