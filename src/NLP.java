import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
//import edu.stanford.nlp.hcoref.CorefCoreAnnotations.CorefChainAnnotation;
//import edu.stanford.nlp.hcoref.data.CorefChain;






public class NLP {
	public static void main(String args[]){
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
//		Properties props = PropertiesUtils.asProperties("props", "StanfordCoreNLP-chinese.properties");
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, parse, lemma, ner, dcoref");
//		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
//		props.setProperty("annotators", "segment, ssplit, pos, lemma, ner, parse, mention, coref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
		// read some text in the text variable
		//String text = "我的名字叫做拔子，萌妹叫做刘津帆。碰碰叫做王玮嘉,她住在香港"; // Add your text here!
		String text = "我借他一块橡皮";
		System.out.println(text);
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);
		System.out.println(pipeline);
		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for(CoreMap sentence: sentences) {

            SemanticGraph dependencies = sentence.get(BasicDependenciesAnnotation.class);
            IndexedWord root = dependencies.getFirstRoot();
            System.out.printf("root(ROOT-0, %s-%d)%n", root.word(), root.index());
            for (SemanticGraphEdge e : dependencies.edgeIterable()) {
                System.out.printf ("%s(%s-%d, %s-%d)%n", e.getRelation().toString(), e.getGovernor().word(), e.getGovernor().index(), e.getDependent().word(), e.getDependent().index());
            }
            System.out.println(dependencies);
		  // this is the parse tree of the current sentence
//		  Tree tree = sentence.get(TreeAnnotation.class);
//		  System.out.println(tree);
		  // lexicalparser
//		  LexicalizedParser lp = LexicalizedParser.loadModel();
//		  
//		  Tree t = lp.parse(sentence.toString());
//
//		  System.out.println(t);
		  // this is the Stanford dependency graph of the current sentence
		  //SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//		  SemanticGraph dpd = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
//		  //System.out.println(dependencies);
//		  System.out.println("test"+dpd);
		}

		// This is the coreference link graph
		// Each chain stores a set of mentions that link to each other,
		// along with a method for getting the most representative mention
		// Both sentence and token offsets start at 1!
//		Map<Integer, CorefChain> graph = 
//		  document.get(CorefChainAnnotation.class);
//		System.out.println(graph);
	}
}
