import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

/**
 * ��ʾ����.
 * ����MainPartExtractor6��������button�뺯�����¼���.
 * @author bazi
 *
 */
public class MainPartExtractorGUI{
	public static void main(String args[]){
		{
			JFrame jf = new JFrame("��Ԫ���ȡ�㷨GUI");
			JPanel jp = new JPanel();
			JTextField jt = new JTextField(20);
			JTextArea ja = new JTextArea(20,60);
			ja.setLineWrap(true);
			JPanel scrollPane = new JPanel();
			JScrollPane scroll = new JScrollPane(ja);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setVisible(true);
			scrollPane.add(scroll);
			JButton jb = new JButton("��ȡ��Ԫ��");
			//pipeline���������������pipeline.
	    	StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
			//�����
	    	String SchemaAdd = "D:\\����У��\\��ҵ����\\�����.owl";
			String DataAdd="D:\\����У��\\��ҵ����\\��ȡ���.owl";
			OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
			ontModel.read(SchemaAdd);
			OntModel dataModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
			//�����URIǰ׺
			String schemaprefix = ontModel.getNsPrefixURI("");
	    	jb.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
					//��ȡ��ʼʱ��
					double startTime = System.currentTimeMillis(); 
					//word1
			    	String indiv1 = "";
			    	//word2
					String indiv2 = "";	
					//property
					String property ="";
					//�洢����
					List<CoreMap> sentences = new ArrayList<CoreMap>();
					//�洢��ȡ���
			    	List<List<IndexedWord>> result = new ArrayList<List<IndexedWord>>();
					String text=jt.getText();
					Annotation document = new Annotation(text);
					pipeline.annotate(document);
					sentences = document.get(SentencesAnnotation.class);
					//�����һ������
					ja.setText("");
						//���־��ĵ�����н���
						for(CoreMap sentence: sentences) {
							//��ȡ�����ʵķ���
							MainPart mp = new MainPart();
							MainPartExtractor6 me = new MainPartExtractor6();
							Collection<TypedDependency> terms = new ArrayList<TypedDependency>();
							SemanticGraph graph = sentence.get(BasicDependenciesAnnotation.class);
							terms = graph.typedDependencies();
							//��һ��ʽ
				            mp = me.getMainPart(terms);
				            if(mp.subject.value()!=null)result.add(mp.r2);
				        	mp.sentence.setValue(sentence.toString());
							ja.append("graph " +graph+ "\n");
							ja.append("sentence " +sentence+ "\n");
							ja.append("sentence 1" +mp+ "\n");
				        	//�ڶ���ʽ
				            mp = me.getSecondPart(terms);
				            if(mp.subject.value()!=null)result.add(mp.r2);
							ja.append("sentence 2" +mp+ "\n");
				            //������ʽ
				            mp = me.getThirdPart(terms);
				            if(mp.subject.value()!=null)result.add(mp.r2);
							ja.append("sentence 3" +mp+ "\n");
				            //���ľ�ʽ
				            mp = me.getFourthPart(terms);
				            if(mp.subject.value()!=null)result.add(mp.r2);
							ja.append("sentence 4" +mp+ "\n");
				            //�����ʽ
				            mp = me.getFifthPart(terms);
				            if(mp.subject.value()!=null)result.add(mp.r2);
							ja.append("sentence 5" +mp+ "\n");
				            //������ʽ
				            mp = me.getSixthPart(terms);
				            if(mp.subject.value()!=null)result.add(mp.r2);
							ja.append("sentence 6" +mp+ "\n");
				            //���߾�ʽ
				            mp = me.getSeventhPart(terms);
				            if(mp.subject.value()!=null)result.add(mp.r2);
							ja.append("sentence 7" +mp+ "\n");
							System.out.println(result);
						}
						for(int k = 0;k<result.size();k++){
							for(int i = 0;i<result.get(k).size();i=i+3){
								indiv1 = result.get(k).get(i).value(); 
								indiv2 = result.get(k).get(i+2).value(); 
								property = result.get(k).get(i+1).value(); 
								Resource sta1 = ontModel.createResource(schemaprefix+indiv1);
								Resource sta2 = ontModel.createResource(schemaprefix+indiv2);
								Property pro= ontModel.createAnnotationProperty(schemaprefix +property);
								sta1.addProperty(pro, sta2);
								System.out.println("������ӳɹ��� ");
								//������RDFչʾ����
								ja.append(sta1.getProperty(pro)+"\n");
							}
						}
						try{
							ontModel.write(new FileOutputStream(DataAdd),"RDF/XML-ABBREV");
							System.out.println("�������!");
						}catch (IOException ioe ){
							System.err.println(ioe.toString());
						}
						//��ȡ����ʱ��
						double endTime = System.currentTimeMillis();
						ja.append(" runtime " +(endTime-startTime)/1000.0+ "s\n");
					}
					catch(Exception ex){
						if(jt.getText().length()<1){
							JOptionPane.showMessageDialog(null,"���������","����",JOptionPane.WARNING_MESSAGE);
						}
					}

				}
			});
			jp.add(jt);
			jp.add(jb);
			jf.setSize(800,600);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setLayout(new BorderLayout());
			jf.getContentPane().add("North", jp);
			jf.getContentPane().add("Center",scrollPane);
			jf.setVisible(true);
		}
	}
}