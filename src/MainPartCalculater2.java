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
 * ����������ȷ�ʲ�����ٻ��ʲ���.
 * @author bazi
 *
 */
public class MainPartCalculater2 {
	public static void main(String args[]) throws IOException{
	      		  FileInputStream excel1file = new FileInputStream(
	    	      new File("D:\\����У��\\���Ͽ�\\����΢��������������ì��΢������\\test3��ȡ.xlsx"));	//����excel�ļ� //�ֶ��ļ�
	    	      FileInputStream excel2file = new FileInputStream(
	    	      new File("D:\\����У��\\���Ͽ�\\����΢��������������ì��΢������\\test3�ල.xlsx"));	//�����ļ�
	    	      //���캯��
	    	      XSSFWorkbook workbook1 = new XSSFWorkbook(excel1file);
	    	      XSSFWorkbook workbook2 = new XSSFWorkbook(excel2file);
	    	      //��ȡ������1
	    	      XSSFSheet sheet1 = workbook1.getSheetAt(0);
	    	      //��ȡ������2
	    	      XSSFSheet sheet2 = workbook2.getSheetAt(0);
	    	      workbook1.close();
	    	      workbook2.close();
	    	      Row r,r2;	    	     
	    	      Cell c,c2,c3,c4,c5,c6;
	    	      //��ȡ��������Ч����
	    	      List<Double> cardinal = new ArrayList<Double>();
	    	      //��ȡ��������ȷ����
	    	      List<Double> correct = new ArrayList<Double>();
	    	      //�����е���ȷ����
	    	      List<Double> realcardinal = new ArrayList<Double>();
	    	      for(int i=0;i<(sheet1.getRow(0).getLastCellNum())/3;i++){
	    	    	  //��ʼ��ÿһ����ʽ������ֵ
	    	    	  Double d = new Double(0);
	    	    	  cardinal.add(d);
	    	    	  correct.add(d);
	    	    	  realcardinal.add(d);
	    	      }
	    	      //excel����ѭ��,��������ֵ������
	    	      for(int i=0;i<sheet1.getLastRowNum();i++){
	    	    	  r = sheet1.getRow(i);
	    	    	  r2 = sheet2.getRow(i);
	    	    	  int lastcellnum = r.getLastCellNum();
	    	    	  if(r2.getLastCellNum() > lastcellnum){
	    	    		  lastcellnum = r2.getLastCellNum();
	    	    	  }
	    	    	  //��ȷ����
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
	    	    		  //��ȡ��������Ԫ�鲻Ϊ�ռ�Ϊ��Ч��Ԫ��.
    	    			  if(cardinal2)cardinal.set(j/3, new Double(cardinal.get(j/3).doubleValue()+1));  		  
    	    			  //��ȡ���Ͽ���ȷ��Ԫ����е���Ԫ��
    	    			  c4 = r2.getCell(j);
	    	    		  c5 = r2.getCell(j+1);
	    	    		  c6 = r2.getCell(j+2);
	    	    		  if(c4==null||c5==null||c6==null||c4.getStringCellValue().isEmpty()||c5.getStringCellValue().isEmpty()||c6.getStringCellValue().isEmpty()){
	    	    			  //��֤��Ԫ�����Ƿ��п�ֵ�����н�����
	    	    			  realcardinal2 = false;
	    	    		  }
//	    	    		  if(c4.getStringCellValue().isEmpty()||c5.getStringCellValue().isEmpty()||c6.getStringCellValue().isEmpty()){
//	    	    			  //��֤��Ԫ�����Ƿ��п�ֵ�����н�����
//	    	    			  realcardinal2 = false;
//	    	    		  }
	    	    		  //���Ͽ�����ȷ����Ԫ��
    	    			  if(realcardinal2)realcardinal.set(j/3, new Double(realcardinal.get(j/3).doubleValue()+1));  
	    	    		  //��ȡ��ȷ����
    	    			  if(cardinal2==true&&realcardinal2==true){
	    	    			  if(c.getStringCellValue().equals(c4.getStringCellValue())&&c2.getStringCellValue().equals(c5.getStringCellValue())&&c3.getStringCellValue().equals(c6.getStringCellValue())){
		    	    			  correct.set(j/3, new Double(correct.get(j/3).doubleValue()+1));
		    	    		  }
    	    			  }
	    	    		  
	    	    	  }
	    	    	  
	    	      }
	    	      //����precision��recall��F-value
	    	      for(int i=0;i<cardinal.size();i++){
	    	    	  double precision = 0;
	    	    	  double recall = 0;
	    	    	  double f = 0;
	    	    	  //��ȷ��
	    	    	  precision = correct.get(i).doubleValue()/cardinal.get(i).doubleValue();
	    	    	  //�ٻ���
	    	    	  recall = correct.get(i).doubleValue()/realcardinal.get(i).doubleValue();
	    	    	  //Fֵ
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
