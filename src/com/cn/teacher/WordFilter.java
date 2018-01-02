package com.cn.teacher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class WordFilter {
	String dataFile;
	ArrayList<String> docs;
	double docNum;
	public static String[] wordsSet ={";",
			".",
			",",
			"£»",
			"£¡",
			"£¿",
			"£¬",
			"¡£"};
	
	public  static String[] wordsSet2 = new String[1210];
	
	public WordFilter(){
		dataFile = "D:/android/workspace/teacherTe/stop.txt";	
//		dataFile = "stop.txt";
		docs = new ArrayList<String>();
		String docLine;
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File( dataFile))));
			while((docLine = br.readLine())!=null)
			{
				docs.add(docLine);
//				System.out.println(docLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		docNum = docs.size();
		for(int i = 0; i < docNum; i++){
			wordsSet2[i] = docs.get(i);
		};
//		for(int i = 0; i < wordsSet2.length; i ++){
//			System.out.println("wordsSet2[" + i + "]µÄÄÚÈÝÎª£º" + wordsSet2[i]);
//		}
	
	}
	
	Boolean isFiltered(String word)
	{
		int i=0;
		while(i<WordFilter.wordsSet2.length)
		{
			if(word.equals(WordFilter.wordsSet2[i]))
				return true;
			i++;
		}
		return false;
	}

}
