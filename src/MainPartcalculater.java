import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class MainPartcalculater {
	public static void main(String args[]) throws IOException{
	      		  FileInputStream excel1file = new FileInputStream(
	    	      new File("D:\\语义校正\\语料库\\新浪微博积极、消极、矛盾微博数据\\test5.xlsx"));	//读入excel文件 //手动文件
	    	      FileInputStream excel2file = new FileInputStream(
	    	      new File("D:\\语义校正\\语料库\\新浪微博积极、消极、矛盾微博数据\\test6.xlsx"));	//机器文件
	    	      XSSFWorkbook workbook1 = new XSSFWorkbook(excel1file);//构造函数
	    	      XSSFWorkbook workbook2 = new XSSFWorkbook(excel2file);
	    	      XSSFSheet sheet1 = workbook1.getSheetAt(0);//获取工作
	    	      XSSFSheet sheet2 = workbook2.getSheetAt(0);
	    	      workbook1.close();
	    	      workbook2.close();
	    	      Row r,r2;	    	     
	    	      Cell c,c2,c3,c4,c5,c6;
	    	      List<Double> cardinal = new ArrayList<Double>();
	    	      List<Double> correct = new ArrayList<Double>();
	    	      double realcardinal = 0;
	    	      for(int i=0;i<(sheet1.getRow(0).getLastCellNum())/3;i++){
	    	    	  Double d = new Double(0);
	    	    	  cardinal.add(d);
	    	    	  correct.add(d);
	    	      }
	    	      for(int i=0;i<sheet1.getLastRowNum();i++){
	    	    	  r = sheet1.getRow(i);
	    	    	  r2 = sheet2.getRow(i);
//    	    		  List<Cell> l = new ArrayList<Cell>();
	    	    	  for(int j=1;j<r.getLastCellNum();j=j+3){
	    	    		  c = r.getCell(j);
	    	    		  c2 = r.getCell(j+1);
	    	    		  c3 = r.getCell(j+2);
	    	    		  if(c==null||c2==null||c3==null){
	    	    			  continue;
	    	    		  }
	    	    		  if(c.getStringCellValue().isEmpty()||c2.getStringCellValue().isEmpty()||c3.getStringCellValue().isEmpty()){
	    	    			  continue;
	    	    		  }//验证三元组中是否有空值，若有将跳过
//	    	    		  if(j==1){
//    	    			  l.add(c);
//    	    			  l.add(c2);//首次加入三元组无需查重	
//    	    			  l.add(c3);
    	    			  cardinal.set(j/3, new Double(cardinal.get(j/3).doubleValue()+1));
//	    	    		  }else{
//	    	    			  boolean check = false;
//	    	    			  for(int n=0;n<l.size();n=n+3){
//	    	    				  if(l.get(n).getStringCellValue()==c.getStringCellValue()&&l.get(n+1).getStringCellValue()==c2.getStringCellValue()&&l.get(n+2).getStringCellValue()==c3.getStringCellValue()){
//	    	    					  check = true;
//	    	    				  }
//	    	    			  }
//	    	    			  if(check){continue;}//如果三元组与前面相同则跳过此次循环；
//	    	    			  l.add(c);
//	    	    			  l.add(c2);	
//	    	    			  l.add(c3);
//	    	    			  cardinal.set(j/3, new Double(cardinal.get(j/3).doubleValue()+1));
//	    	    		  }//基数计算
	    	    		  
	    	    		  for(int k=1;k<r2.getLastCellNum();k=k+3){
		    	    		  c4 = r2.getCell(k);
		    	    		  c5 = r2.getCell(k+1);
		    	    		  c6 = r2.getCell(k+2);
		    	    		  if(c4.getStringCellValue().isEmpty()||c5.getStringCellValue().isEmpty()||c6.getStringCellValue().isEmpty()){
		    	    			  continue;//验证三元组中是否有空值，若有将跳过
		    	    		  }
		    	    		  if(c.getStringCellValue().equals(c4.getStringCellValue())&&c2.getStringCellValue().equals(c5.getStringCellValue())&&c3.getStringCellValue().equals(c6.getStringCellValue())){
		    	    			  correct.set(j/3, new Double(correct.get(j/3).doubleValue()+1));
		    	    			  break;
		    	    		  }
		    	    		  
	    	    		  }//正确个数计算
	    	    		  
	    	    		  double each = (r2.getLastCellNum()-1)/3;
	    	    		  realcardinal = realcardinal + each;
	    	    		  //该预料下真正的三元组基数计算
	    	    		  
	    	    	  }//精确到行
	    	    	  
	    	      }//excel整体循环
	    	      for(int i=0;i<cardinal.size();i++){
	    	    	  double precision=0;
	    	    	  precision = correct.get(i).doubleValue()/cardinal.get(i).doubleValue();
	    	    	  System.out.println(correct.get(i).doubleValue());
	    	    	  System.out.println(cardinal.get(i).doubleValue());
	    	    	  System.out.println("sentence "+(i+1)+" precision:"+precision); 
	    	      }
//	    	      for(int i=0;i<cardinal.size();i++){
//	    	    	  double precision=0;
//	    	    	  precision = correct.get(i).doubleValue();
//	    	    	  System.out.println(precision); 
//	    	      }
	    	      
	}
}
