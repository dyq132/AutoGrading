package org.ictclas4j;
import org.ictclas4j.bean.SegResult;  
import org.ictclas4j.segment.SegTag;  

public class Test {  
    public static void main(String[] args) throws Exception{  
        SegTag st = new SegTag(1);  
//        String line = "����ictclas4j���ķִʲ��Գ���";  
        String lineans = "�͹۴��ڲ������໥���ֵĿ͹����������¼����͹۴��ڲ������໥���ֵĿ͹����������¼����͹۴��ڲ������໥���ֵĿ͹����������¼���";
        String line1 = "ʵ�ʵ����壬�����˺�����������������ԡ�";
        String line2 = "����ĸ���;�������������һ���Ϊʵ�塣";
        String line3 = "��ʵ���ڵĿ������ֿ��������塣";
        SegResult srans = st.split(lineans);  //��׼��
        SegResult sr1 = st.split(line1);  //ѧ����1
        SegResult sr2 = st.split(line2);  //ѧ����2
        SegResult sr3 = st.split(line3);  //ѧ����3s
        System.out.println(srans.getFinalResult());//��׼��
        System.out.println(sr1.getFinalResult());//ѧ����1
        System.out.println(sr2.getFinalResult());//ѧ����2
        System.out.println(sr3.getFinalResult());//ѧ����3
        }  
    }  