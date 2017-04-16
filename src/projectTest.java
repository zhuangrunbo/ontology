

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class projectTest 
{
   static XSSFRow row;
   static XSSFRow row2;
   /**
 * @param args
 * @throws Exception
 */
public static void main(String[] args) throws Exception 
   {
      FileInputStream excel1file = new FileInputStream(
      new File("F:\\test.xlsx"));	//����excel�ļ� //�ֶ��ļ�
      FileInputStream excel2file = new FileInputStream(
      new File("F:\\test2.xlsx"));	//�����ļ�
      XSSFWorkbook workbook1 = new XSSFWorkbook(excel1file);//���캯��
      XSSFWorkbook workbook2 = new XSSFWorkbook(excel2file);
      XSSFSheet sheet1 = workbook1.getSheetAt(0);//��ȡ����
      XSSFSheet sheet2 = workbook2.getSheetAt(0);
      Row row1,row2,rowfirst;
      Cell cell1,cell2,cellfirst;
      int listFirstData=0,allNum=0,rownum2=-1,time=0;
      int[] a={0,0,0,0,0,0,0,};//�߸���ʽ˳�������
      for(int rownum1=0;rownum1<=sheet1.getLastRowNum();rownum1++){//ѭ���ֹ��ļ�
    	  row1=sheet1.getRow(rownum1);	//��ȡ��
    	  for (int listnum1=0;listnum1<row1.getLastCellNum();listnum1++){//ѭ���ֹ��ļ� ѭ��ÿһ�� 
    		  cell1 = row1.getCell(listnum1);
    		  String stringData=cell1.getStringCellValue();
//    		  System.out.println("��ʼ��"+(listFirstData+1));
    		  	  rownum2++;	//�����ļ�������
//    			  int firstNum=listFirstData;
//    			  System.out.println("firstNum = "+firstNum);
    			  row2=sheet2.getRow(rownum2);
    			  cell2=row2.getCell(0);
    			  rowfirst=sheet2.getRow(0);
//    			  cellfirst=rowfirst.getCell(0);
//    			  String firstLineData = "ԭ��";
    			  System.out.println("  cell1Data= "+cell1.getStringCellValue()+"  cell2Data= "+cell2.getStringCellValue());
//    			  listFirstData++;
//    			  if (firstLineData.equals(cell2.getStringCellValue())){
//    				  rownum2=rownum2+2;
//    				  break;
//    			  }else{
    				  if(cell1.getStringCellValue().equals(cell2.getStringCellValue())){
    					  allNum++;
    					  a[listnum1]++;	//��ʽ����
    					  System.out.println("allNum= "+allNum);
    					  
    				  }
//    		  }
    	  }
      }
      for(int i=0;i<7;i++){
    	  double avg=a[i]/3.0;
    	  System.out.println("�� "+i+" ����ʽ����ȷ��Ϊ ��"+avg);
      }
      System.out.println(allNum);
      excel1file.close();
      excel2file.close();
  }
}