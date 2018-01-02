package com.cn.teacher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;

public class IDFCal {
	String dataFile;
	ArrayList<String> docs;
	ArrayList<Term> termList;//存放的是整个文档的关键字的对象 
	WordFilter filter;
	double docNum;
	
	public IDFCal()
	{
		dataFile = "data.txt";	
		docs = new ArrayList<String>();
		termList = new ArrayList<Term>();
		filter = new WordFilter(); 
	}
	
	/**
	 * 把标准答案放入list里面
	 */
	public void getDocs() 
	{
		SegTag st = new SegTag(1);  
		//标准答案
		String docLine = "客观存在并可以相互区分的客观事物或抽象事件。";
		SegResult srans = st.split(docLine);  //标准答案
//		System.out.println(srans.getFinalResult());//标准答案
		docs.add(srans.getFinalResult());
	}
    
	
	/**
	 * 去掉没用的东西
	 *
	 */
	public void getTermList()
	{
		String docInstance;
		for(int i=0; i<docs.size();i++)
		{
			ArrayList<Term> termInDoc = new ArrayList<Term>();//存放文档里面的所有关键字
			docInstance = docs.get(i);
			int start  = 0;
			int end  = 0;
			for(end=0; end<docInstance.length();end++)
			{
				if(docInstance.substring(end, end+1).equals(" "))
				{
					start = end;
				}
				if(docInstance.substring(end, end+1).equals("/"))
				{
					if(!filter.isFiltered(docInstance.substring(start+1, end)))
					{
						//把关键字放入数组中，重复的只放一次（如果重复则把这个词的termFreq值增加1）
						addItemInDoc(docInstance.substring(start+1, end),termInDoc);	
					}
					
					//System.out.println("add item: "+docInstance.substring(start+1, end));
				}
			}
			docNum = termInDoc.size();
			refreshTermList(termInDoc);//把所有的关键字放入Termlist里面
		}
		setIDF();//设置idf属性
//		printTermList();//打印termList里面的具体信息
		
	}
	
	
	/**
	 * 把所有的关键字放入termList里面
	 * @param termInDoc
	 */
	private void refreshTermList(ArrayList<Term> termInDoc) {
		for(int i=0;i<termInDoc.size();i++)
		{
			Boolean isMatchedFound = false;
			//顺序查找，写起来简单，但是效率低
			for(int j=0;j<termList.size();j++)
			{
				if(termList.get(j).getTerm().equals(termInDoc.get(i).getTerm()))
				{
					isMatchedFound = true;
					termList.get(j).addTermFreq(1);
				}
			}
			if(!isMatchedFound)
			{
				termList.add(new Term(termInDoc.get(i).getTerm()));
			}
		}
		
	}
	
	
	
	/**
	 * 打印termList里面的具体信息
	 */
	private void printTermList() {
		System.out.println("total terms :"+termList.size());
		for(int i=0;i<termList.size();i++)
		{
			System.out.println(i+"th term"+termList.get(i).getTerm()+" "
					+termList.get(i).getTermFreq()+" "
					+termList.get(i).getIDF());
		}
		
	}
	
	
	/**
	 * 把关键字放入数组中，重复的只放一次（如果重复则把这个词的termFreq值增加1）
	 * @param stringTerm
	 * @param termInDoc
	 */
	private void addItemInDoc(String stringTerm,ArrayList<Term> termInDoc) {
		if(termInDoc.size()==0)//第一次termInDoc为空，则直接把数据加入termInDoc
		{
			termInDoc.add(new Term(stringTerm));
		}
		else//第二次termInDoc不为空，则进入else
		{
			Boolean isFoundMatched = false;//标示符 用于标识新传进来的词与termInDoc里面的词是否一致
			for(int i=0;i<termInDoc.size();i++)
			{
				if(termInDoc.get(i).getTerm().equals(stringTerm))//如果新传进来的词与termInDoc里面的某个词一致
				{
					isFoundMatched = true;//标示符设置为true
					termInDoc.get(i).addTermFreq(1);//相应的把这个词的termFreq值增加1
				}
			}
			if(!isFoundMatched)//如果新传进来的词与termInDoc里面的所有词都不一致，则把这个词加入termInDoc里面
			{
				termInDoc.add(new Term(stringTerm));
			}
		}		
	}
	
	
	
	/**
	 * 设置idf属性
	 */
	private void setIDF()
	{
		for(int i=0;i<termList.size();i++)
		{
			double idf = Math.log10(docNum/termList.get(i).getTermFreq());
			termList.get(i).setIDF(idf);
		}
	}
	
	
	/**
	 * 比较doc1 和doc2 文档的相似度
	 * @param doc1
	 * @param doc2
	 * @return double
	 */
	private double calSim(String doc1) {
		String doc2 = docs.get(0);//标准答案
		int start  = 0;
		int end  = 0;
		
		String docInstance = doc1;
		ArrayList<Term> termInDoc1 = new ArrayList<Term>();		
		for(end=0; end<docInstance.length();end++)
		{
			if(docInstance.substring(end, end+1).equals(" "))
			{
				start = end;
			}
			if(docInstance.substring(end, end+1).equals("/"))
			{
				if(!filter.isFiltered(docInstance.substring(start+1, end)))
				{					
					addItemInDoc(docInstance.substring(start+1, end),termInDoc1);
				}
				//System.out.println("add item: "+docInstance.substring(start+1, end));
			}
		}
		start = 0;
		end = 0;
		docInstance = doc2;
		ArrayList<Term> termInDoc2 = new ArrayList<Term>();		
		for(end=0; end<docInstance.length();end++)
		{
			if(docInstance.substring(end, end+1).equals(" "))
			{
				start = end;
			}
			if(docInstance.substring(end, end+1).equals("/"))
			{
				if(!filter.isFiltered(docInstance.substring(start+1, end)))
				{					
					addItemInDoc(docInstance.substring(start+1, end),termInDoc2);
				}
				//System.out.println("add item: "+docInstance.substring(start+1, end));
			}
		}
//		for(int i = 0; i <termInDoc1.size(); i ++){
//			System.out.println(termInDoc1.get(i).getTerm());
//		}
		calTermInDoc(termInDoc1);//设置TF*IDF
		calTermInDoc(termInDoc2);//设置TF*IDF
		double lengthDoc1 = calLengthOfDoc(termInDoc1);//分母上的开方值
		double lengthDoc2 = calLengthOfDoc(termInDoc2);//分母上的开方值
		double docProduct = calDocProduct(termInDoc1,termInDoc2);//分子上的连乘和
		return (docProduct/(lengthDoc1*lengthDoc2));//夹角余弦值
	}
	
	/**
	 * 分子上的连乘和
	 * @param termInDoc1
	 * @param termInDoc2
	 * @return
	 */
	private double calDocProduct(ArrayList<Term> termInDoc1,
			ArrayList<Term> termInDoc2) {
		double product = 0;
		for(int i=0;i<termInDoc1.size();i++)
		{
			for(int j=0;j<termInDoc2.size();j++)
			{
				if(termInDoc1.get(i).getTerm().equals(termInDoc2.get(j).getTerm()))
				{
					product = product+termInDoc1.get(i).getIDF()*termInDoc2.get(j).getIDF();
				}
			}
		}
		return product;
	}
	
	
	/**
	 * 分母上的开方值
	 * @param termInDoc
	 * @return
	 */
	private double calLengthOfDoc(ArrayList<Term> termInDoc) {
		double length = 0;
		for(int i=0;i<termInDoc.size();i++)
		{
			length=length+termInDoc.get(i).getIDF()*termInDoc.get(i).getIDF();
		}
		
		return Math.sqrt(length);
	}
	
	/**
	 * termList存放的是整个文档的关键词
	 * 设置tfidf
	 * 设置TF*IDF
	 * @param termInDoc
	 */
	private void calTermInDoc(ArrayList<Term> termInDoc) {
		for(int j =0;j<termList.size();j++){
			System.out.println("标准答案里面的关键字为：" + termList.get(j).getTerm());
		}
		
		
		for(int i=0;i<termInDoc.size();i++)
		{
			Boolean isFoundMatched = false;
			for(int j =0;j<termList.size();j++)
			{
				if(termInDoc.get(i).getTerm().equals(termList.get(j).getTerm()))
				{
					isFoundMatched = true;
					double tfidf = termInDoc.get(i).getTermFreq()*termList.get(j).getIDF();
					termInDoc.get(i).setIDF(tfidf);
				}
			}
			if(!isFoundMatched)
			{
//				System.out.println("unexpected error!!");
				System.out.println("标准答案里面没有关键字：" + termInDoc.get(i).getTerm() + "。");
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		IDFCal idfCal = new IDFCal();
		String doc1 ;
		String doc2 ;
		idfCal.getDocs();//逐行读取文档
		idfCal.getTermList();
		doc2 = idfCal.docs.get(0);//标准答案
		doc1 = "实际的物体，包括人和事物，有无生命都可以。"; //学生答案1
		doc1 = "抽象的概念和具体的事物组合在一起称为实体。"; //学生答案1
//		doc1 = "真实存在的可以区分开来的物体。"; //学生答案1
		SegTag st = new SegTag(1);  
		SegResult sr1 = st.split(doc1);  //学生答案1
		doc1 = sr1.getFinalResult();//学生答案1经过分词处理
//		System.out.println("doc1:" + doc1);
		Double score = idfCal.calSim(doc1);			
//		System.out.println("========="+doc2);
		System.out.println("学生答案和标准答案的相似度为：" + score);
		
	}	
}
