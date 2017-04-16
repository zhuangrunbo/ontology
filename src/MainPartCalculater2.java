import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 改良计算正确率测算和召回率测算.
 * @author bazi
 *
 */
public class MainPartCalculater2 {
	public static void main(String args[]) throws IOException{
	      		  FileInputStream excel1file = new FileInputStream(
	    	      new File("D:\\语义校正\\语料库\\新浪微博积极、消极、矛盾微博数据\\test3抽取.xlsx"));	//读入excel文件 //手动文件
	    	      FileInputStream excel2file = new FileInputStream(
	    	      new File("D:\\语义校正\\语料库\\新浪微博积极、消极、矛盾微博数据\\test3监督.xlsx"));	//机器文件
	    	      //构造函数
	    	      XSSFWorkbook workbook1 = new XSSFWorkbook(excel1file);
	    	      XSSFWorkbook workbook2 = new XSSFWorkbook(excel2file);
	    	      //获取工作表1
	    	      XSSFSheet sheet1 = workbook1.getSheetAt(0);
	    	      //获取工作表2
	    	      XSSFSheet sheet2 = workbook2.getSheetAt(0);
	    	      workbook1.close();
	    	      workbook2.close();
	    	      Row r,r2;	    	     
	    	      Cell c,c2,c3,c4,c5,c6;
	    	      //抽取出来的有效个数
	    	      List<Double> cardinal = new ArrayList<Double>();
	    	      //抽取出来的正确个数
	    	      List<Double> correct = new ArrayList<Double>();
	    	      //语料中的正确个数
	    	      List<Double> realcardinal = new ArrayList<Double>();
	    	      for(int i=0;i<(sheet1.getRow(0).getLastCellNum())/3;i++){
	    	    	  //初始化每一个句式的三个值
	    	    	  Double d = new Double(0);
	    	    	  cardinal.add(d);
	    	    	  correct.add(d);
	    	    	  realcardinal.add(d);
	    	      }
	    	      //excel整体循环,计算三个值的数量
	    	      for(int i=0;i<sheet1.getLastRowNum();i++){
	    	    	  r = sheet1.getRow(i);
	    	    	  r2 = sheet2.getRow(i);
	    	    	  int lastcellnum = r.getLastCellNum();
	    	    	  if(r2.getLastCellNum() > lastcellnum){
	    	    		  lastcellnum = r2.getLastCellNum();
	    	    	  }
	    	    	  //精确到行
	    	    	  for (int j = 1; j < lastcellnum; j = j + 3) {
	    	    		  boolean cardinal2 = true;
	    	    		  boolean realcardinal2 = true;
	    	    		  c = r.getCell(j);
	    	    		  c2 = r.getCell(j+1);
	    	    		  c3 = r.getCell(j+2);		
	    	    		  if(c==null||c2==null||c3==null||c.getStringCellValue().isEmpty()||c2.getStringCellValue().isEmpty()||c3.getStringCellValue().isEmpty()){
	    	    			  cardinal2 = false;
	    	    		  }
//	    	    		  if(c.getStringCellValue().isEmpty()||c2.getStringCellValue().isEmpty()||c3.getStringCellValue().isEmpty()){
//	    	    			  cardinal2 = false;
//	    	    		  }
	    	    		  //抽取出来的三元组不为空即为有效三元组.
    	    			  if(cardinal2)cardinal.set(j/3, new Double(cardinal.get(j/3).doubleValue()+1));  		  
    	    			  //获取语料库正确三元组表中的三元组
    	    			  c4 = r2.getCell(j);
	    	    		  c5 = r2.getCell(j+1);
	    	    		  c6 = r2.getCell(j+2);
	    	    		  if(c4==null||c5==null||c6==null||c4.getStringCellValue().isEmpty()||c5.getStringCellValue().isEmpty()||c6.getStringCellValue().isEmpty()){
	    	    			  //验证三元组中是否有空值，若有将跳过
	    	    			  realcardinal2 = false;
	    	    		  }
//	    	    		  if(c4.getStringCellValue().isEmpty()||c5.getStringCellValue().isEmpty()||c6.getStringCellValue().isEmpty()){
//	    	    			  //验证三元组中是否有空值，若有将跳过
//	    	    			  realcardinal2 = false;
//	    	    		  }
	    	    		  //语料库中正确的三元组
    	    			  if(realcardinal2)realcardinal.set(j/3, new Double(realcardinal.get(j/3).doubleValue()+1));  
	    	    		  //抽取正确个数
    	    			  if(cardinal2==true&&realcardinal2==true){
	    	    			  if(c.getStringCellValue().equals(c4.getStringCellValue())&&c2.getStringCellValue().equals(c5.getStringCellValue())&&c3.getStringCellValue().equals(c6.getStringCellValue())){
		    	    			  correct.set(j/3, new Double(correct.get(j/3).doubleValue()+1));
		    	    		  }
    	    			  }
	    	    		  
	    	    	  }
	    	    	  
	    	      }
	    	      //计算precision，recall，F-value
	    	      for(int i=0;i<cardinal.size();i++){
	    	    	  double precision = 0;
	    	    	  double recall = 0;
	    	    	  double f = 0;
	    	    	  //正确率
	    	    	  precision = correct.get(i).doubleValue()/cardinal.get(i).doubleValue();
	    	    	  //召回率
	    	    	  recall = correct.get(i).doubleValue()/realcardinal.get(i).doubleValue();
	    	    	  //F值
	    	    	  f = (precision * recall * 2)/(precision + recall);
	    	    	  System.out.println("sentence "+(i+1)+" precision:"+precision);
	    	    	  System.out.println("sentence "+(i+1)+" recall:"+recall);
	    	    	  System.out.println("sentence "+(i+1)+" f:"+f);
	    	    	  System.out.println("sentencecorrect "+correct.get(i));
	    	    	  System.out.println("sentencecardinal "+cardinal.get(i));
	    	    	  System.out.println("sentencerealcardinal "+realcardinal.get(i));
	    	      }
	    	      
	}
}
