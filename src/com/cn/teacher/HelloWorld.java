package com.cn.teacher;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorld extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = -758686623642845372L;

    
    public String login() throws Exception{
    	String uname;
    	String pwd;
    	uname = username;
    	pwd = password;
    	System.out.println("uname:" + uname + ",pwd:" + pwd);
    	return null;
    }

    public String execute() throws Exception {

//		HelloWorld idfCal = new HelloWorld();
		String doc1 ;
		String doc2 ;
//		idfCal.getDocs();//���ж�ȡ�ĵ�
//		idfCal.getTermList();
//		doc2 = idfCal.docs.get(0);//��׼��
//		doc1 = idfCal.ans;
		getDocs();//���ж�ȡ�ĵ�
		getTermList();
		doc2 = docs.get(0);//��׼��
		doc1 = ans;//ѧ����
		String ansans = ansbz;
		System.out.println("��׼��Ϊ������" + ansans);
//		doc1 = "ʵ�ʵ����壬�����˺�����������������ԡ�"; //ѧ����1
//		doc1 = "����ĸ���;�������������һ���Ϊʵ�塣"; //ѧ����1
//		doc1 = "��ʵ���ڵĿ������ֿ��������塣"; //ѧ����1
		SegTag st = new SegTag(1);  
		SegResult sr1 = st.split(doc1);  //ѧ����1
		doc1 = sr1.getFinalResult();//ѧ����1�����ִʴ���
		System.out.println("doc1:" + doc1);
//		System.out.println("doc1:" + doc1);
//		Double score = idfCal.calSim(doc1);	
		Double score = calSim(doc1);	//�Ա����ƶ�
//		System.out.println("========="+doc2);
		System.out.println("ѧ���𰸺ͱ�׼�𰸵����ƶ�Ϊ��" + score);
		
		
		Double ssssss = score * 100;
		DecimalFormat df = new DecimalFormat("0.0");
		String sksjk = df.format(ssssss) + "%";
		System.out.println(sksjk);
		
    	
    	setResultScore(sksjk);
        setMessage(ans);
        System.out.println(ans);
        return SUCCESS;
    }

    private String message;
    private String ans;
    private String resultScore;
    private String ansbz;
    private String username;
    private String password;
    
    


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAnsbz() {
		return ansbz;
	}

	public void setAnsbz(String ansbz) {
		this.ansbz = ansbz;
	}

	public String getResultScore() {
		return resultScore;
	}

	public void setResultScore(String resultScore) {
		this.resultScore = resultScore;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
    
    
    
    

	String dataFile;
	ArrayList<String> docs;
	ArrayList<Term> termList;//��ŵ��������ĵ��Ĺؼ��ֵĶ��� 
	WordFilter filter;
	double docNum;
	
	public HelloWorld()
	{
		dataFile = "data.txt";	
		docs = new ArrayList<String>();
		termList = new ArrayList<Term>();
		filter = new WordFilter(); 
	}
	
	/**
	 * �ѱ�׼�𰸷���list����
	 */
	public void getDocs() 
	{
		SegTag st = new SegTag(1);  
		//��׼��
		String docLine = "�͹۴��ڲ������໥���ֵĿ͹����������¼���";
		SegResult srans = st.split(docLine);  //��׼��
		System.out.println(srans.getFinalResult());//��׼��
		docs.add(srans.getFinalResult());
	}
    
	
	/**
	 * ȥ��û�õĶ���
	 *
	 */
	public void getTermList()
	{
		String docInstance;
		for(int i=0; i<docs.size();i++)
		{
			ArrayList<Term> termInDoc = new ArrayList<Term>();//����ĵ���������йؼ���
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
						//�ѹؼ��ַ��������У��ظ���ֻ��һ�Σ�����ظ��������ʵ�termFreqֵ����1��
						addItemInDoc(docInstance.substring(start+1, end),termInDoc);	
					}
					
					//System.out.println("add item: "+docInstance.substring(start+1, end));
				}
			}
			docNum = termInDoc.size();
			refreshTermList(termInDoc);//�����еĹؼ��ַ���Termlist����
		}
		setIDF();//����idf����
//		printTermList();//��ӡtermList����ľ�����Ϣ
		
	}
	
	
	/**
	 * �����еĹؼ��ַ���termList����
	 * @param termInDoc
	 */
	private void refreshTermList(ArrayList<Term> termInDoc) {
		for(int i=0;i<termInDoc.size();i++)
		{
			Boolean isMatchedFound = false;
			//˳����ң�д�����򵥣�����Ч�ʵ�
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
	 * ��ӡtermList����ľ�����Ϣ
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
	 * �ѹؼ��ַ��������У��ظ���ֻ��һ�Σ�����ظ��������ʵ�termFreqֵ����1��
	 * @param stringTerm
	 * @param termInDoc
	 */
	private void addItemInDoc(String stringTerm,ArrayList<Term> termInDoc) {
		if(termInDoc.size()==0)//��һ��termInDocΪ�գ���ֱ�Ӱ����ݼ���termInDoc
		{
			termInDoc.add(new Term(stringTerm));
		}
		else//�ڶ���termInDoc��Ϊ�գ������else
		{
			Boolean isFoundMatched = false;//��ʾ�� ���ڱ�ʶ�´������Ĵ���termInDoc����Ĵ��Ƿ�һ��
			for(int i=0;i<termInDoc.size();i++)
			{
				if(termInDoc.get(i).getTerm().equals(stringTerm))//����´������Ĵ���termInDoc�����ĳ����һ��
				{
					isFoundMatched = true;//��ʾ������Ϊtrue
					termInDoc.get(i).addTermFreq(1);//��Ӧ�İ�����ʵ�termFreqֵ����1
				}
			}
			if(!isFoundMatched)//����´������Ĵ���termInDoc��������дʶ���һ�£��������ʼ���termInDoc����
			{
				termInDoc.add(new Term(stringTerm));
			}
		}		
	}
	
	
	
	/**
	 * ����idf����
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
	 * �Ƚ�doc1 ��doc2 �ĵ������ƶ�
	 * @param doc1
	 * @param doc2
	 * @return double
	 */
	private double calSim(String doc1) {
		String doc2 = docs.get(0);//��׼��
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
		calTermInDoc(termInDoc1);//����TF*IDF
		calTermInDoc(termInDoc2);//����TF*IDF
		double lengthDoc1 = calLengthOfDoc(termInDoc1);//��ĸ�ϵĿ���ֵ
		double lengthDoc2 = calLengthOfDoc(termInDoc2);//��ĸ�ϵĿ���ֵ
		double docProduct = calDocProduct(termInDoc1,termInDoc2);//�����ϵ����˺�
		if(docProduct == 0.0){
			return 0.0;
		}
		return (docProduct/(lengthDoc1*lengthDoc2));//�н�����ֵ
	}
	
	/**
	 * �����ϵ����˺�
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
	 * ��ĸ�ϵĿ���ֵ
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
	 * termList��ŵ��������ĵ��Ĺؼ���
	 * ����tfidf
	 * ����TF*IDF
	 * @param termInDoc
	 */
	private void calTermInDoc(ArrayList<Term> termInDoc) {
		for(int j =0;j<termList.size();j++){
			System.out.println("��׼������Ĺؼ���Ϊ��" + termList.get(j).getTerm());
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
				System.out.println("��׼������û�йؼ��֣�" + termInDoc.get(i).getTerm() + "��");
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		HelloWorld idfCal = new HelloWorld();
		String doc1 ;
		String doc2 ;
		idfCal.getDocs();//���ж�ȡ�ĵ�
		idfCal.getTermList();
		doc2 = idfCal.docs.get(0);//��׼��
		doc1 = "ʵ�ʵ����壬�����˺�����������������ԡ�"; //ѧ����1
		doc1 = ""; //ѧ����1
//		doc1 = "���� ���� ���� �¼� �͹����� �໥ �͹� ";
//		doc1 = "��ʵ���ڵĿ������ֿ��������塣"; //ѧ����1
		SegTag st = new SegTag(1);  
		SegResult sr1 = st.split(doc1);  //ѧ����1
		doc1 = sr1.getFinalResult();//ѧ����1�����ִʴ���
//		System.out.println("doc1:" + doc1);
		Double score = idfCal.calSim(doc1);			
//		System.out.println("========="+doc2);
		System.out.println("ѧ���𰸺ͱ�׼�𰸵����ƶ�Ϊ��" + score);
		
		// double num=89898989090900989.887787878787878798;
		Double ssssss = score * 100;
		DecimalFormat df = new DecimalFormat("0.00");
		String sksjk = df.format(ssssss) + "%";
		System.out.println(sksjk);
		
	}	

}