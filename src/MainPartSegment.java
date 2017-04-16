import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


public class MainPartSegment {
	public List<CoreLabel> segment(String text){
		Properties props = new Properties();
//		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//		props.setProperty("annotators", "segment, ssplit, pos, lemma, ner, parse, mention, coref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
		// read some text in the text variable
//		String text = "我的名字叫做拔子，萌妹叫做刘津帆。碰碰叫做王玮嘉"; // Add your text here!
//		System.out.println(text);
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);
		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
			

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//		List<SemanticGraphEdge> list = new ArrayList<SemanticGraphEdge>();
		List<CoreLabel> list3 = new ArrayList<CoreLabel>();
//		Collection<TypedDependency> list4 = new ArrayList<TypedDependency>();
//		SemanticGraph list = document.get(CollapsedCCProcessedDependenciesAnnotation.class);
		for(CoreMap sentence: sentences) {
//			  SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//			  list = dependencies.edgeListSorted();
//			  list4 = dependencies.typedDependencies();
			  list3 = sentence.get(TokensAnnotation.class);
//			  System.out.println(list3);
//			  for(SemanticGraphEdge list2:list){
//				  String ls2 = list2.getRelation().toString();
//				  System.out.println(list2);
//				  CoreLabel cb2 = new CoreLabel();
//
//				  
//			  }
		}

		return list3;
		
	}
}
