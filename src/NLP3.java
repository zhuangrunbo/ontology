import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.tagger.io.TaggedFileRecord.Format;
import edu.stanford.nlp.util.CoreMap;


public class NLP3 {
		public static void main(String args[]){	    
	    //props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
	    //StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
        String models = "D:\\����У��\\�����\\NLP-API\\stanford-chinese-corenlp-2016-10-31-models\\edu\\stanford\\nlp\\models\\lexparser\\chineseFactored.ser.gz";
        LexicalizedParser lp = LexicalizedParser.loadModel(models);

	    StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
	    String text = "Ҧ����Ҷ���Ƿ���"; // Add your text here!
	    List<Word> words = new ArrayList<Word>();
	    Annotation document = new Annotation(text);
	    pipeline.annotate(document);
	    String[] myStringArray = {"SentencesAnnotation"};
	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		    for(CoreMap sentence: sentences) {
		        SemanticGraph dependencies = sentence.get(BasicDependenciesAnnotation.class);
		        System.out.println(dependencies);
		        IndexedWord root = dependencies.getFirstRoot();
		        //�����������tag  
		        System.out.println(root.tag());   
		        System.out.printf("root(ROOT-0, %s-%d)%n", root.word(), root.index());
		        for (SemanticGraphEdge e : dependencies.edgeIterable()) {
		            System.out.printf ("%s(%s-%d, %s-%d)%n", e.getRelation().toString(), e.getGovernor().word(), e.getGovernor().index(), e.getDependent().word(), e.getDependent().index());
		        }
		    }

		}
}
