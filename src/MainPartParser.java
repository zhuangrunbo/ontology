import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;






public class MainPartParser {
	//读取文本文件路径并封装句子
    public static void FileRead(List<List<String>> strings,String path) throws IOException{
		File f = new File(path);
		File[] list = f.listFiles();
		boolean exist;
		for(int i=0;i<list.length;i++){
			List<String> strings2 = new ArrayList<String>();
			if(list[i].isDirectory()){
				FileRead(strings,list[i].getAbsolutePath());
			}
			else
			{
				BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(list[i].getAbsolutePath()),"GBK"));
				//StringBuilder content = new StringBuilder();
				System.out.println(list[i].getAbsolutePath());
				String line ="";
				String line2 ="";
				while(line!=null){
					line = bf.readLine();
					if(line==null){
						break;
					}
					line2 = line.replaceAll("\n\r","");
					if(line.length()==2){
					continue;//识别换行符
					}
					if(line.trim().isEmpty()){
						continue;
					}
					System.out.println(line2);
					strings2.add(line2);
					
				}
				bf.close();
				System.out.println(strings2); 
				strings.add(strings2);
			}
		}
    }
    //从excel中获取句子.
    public static List<String> getSenetenceFromExcel(final String path) throws IOException{
    	//存放句子的list
    	List<String> sentences = new ArrayList<String>();
    	//读入excel文件
    	FileInputStream excel1file = new FileInputStream(
	    new File("D:\\语义校正\\语料库\\新浪微博积极、消极、矛盾微博数据\\test3-4.xlsx"));
    	//构造工作簿
	    XSSFWorkbook workbook1 = new XSSFWorkbook(excel1file);
	     //获取sheet
	    XSSFSheet sheet1 = workbook1.getSheetAt(0);
	    workbook1.close();
	    for(int i = 0; i<sheet1.getLastRowNum(); i++){
	    	sentences.add(sheet1.getRow(i).getCell(0).getStringCellValue());
	    }
    	return sentences;
    }
    
    //主函数
	public static void main(String args[]) throws IOException{
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, parse, lemma, ner, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
		String text = "我借他一块橡皮";
		System.out.println(text);
		List<String> strings = getSenetenceFromExcel("D:\\语义校正\\语料库\\新浪微博积极、消极、矛盾微博数据\\test5（1）.xlsx");
//		Annotation document = new Annotation(text);
		for(int i=0;i<strings.size();i++){
			Annotation document = new Annotation(strings.get(i));
			pipeline.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for(CoreMap sentence: sentences) {

	            SemanticGraph dependencies = sentence.get(BasicDependenciesAnnotation.class);
	            IndexedWord root = dependencies.getFirstRoot();
	            System.out.println(i+":"+sentence);
	            System.out.printf("root(ROOT-0, %s-%d)%n", root.word(), root.index());
	            for (SemanticGraphEdge e : dependencies.edgeIterable()) {
	                System.out.printf ("%s(%s-%d, %s-%d)%n", e.getRelation().toString(), e.getGovernor().word(), e.getGovernor().index(), e.getDependent().word(), e.getDependent().index());
	            }
	            System.out.println(dependencies);
			  // this is the parse tree of the current sentence
			}


		}
	}
}

