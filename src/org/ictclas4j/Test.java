package org.ictclas4j;
import org.ictclas4j.bean.SegResult;  
import org.ictclas4j.segment.SegTag;  

public class Test {  
    public static void main(String[] args) throws Exception{  
        SegTag st = new SegTag(1);  
//        String line = "这是ictclas4j中文分词测试程序。";  
        String lineans = "客观存在并可以相互区分的客观事物或抽象事件。客观存在并可以相互区分的客观事物或抽象事件。客观存在并可以相互区分的客观事物或抽象事件。";
        String line1 = "实际的物体，包括人和事物，有无生命都可以。";
        String line2 = "抽象的概念和具体的事物组合在一起称为实体。";
        String line3 = "真实存在的可以区分开来的物体。";
        SegResult srans = st.split(lineans);  //标准答案
        SegResult sr1 = st.split(line1);  //学生答案1
        SegResult sr2 = st.split(line2);  //学生答案2
        SegResult sr3 = st.split(line3);  //学生答案3s
        System.out.println(srans.getFinalResult());//标准答案
        System.out.println(sr1.getFinalResult());//学生答案1
        System.out.println(sr2.getFinalResult());//学生答案2
        System.out.println(sr3.getFinalResult());//学生答案3
        }  
    }  