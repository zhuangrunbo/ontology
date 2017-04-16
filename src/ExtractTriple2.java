//	import java.util.Collection;
//	import java.util.Properties;
//	
//	import edu.stanford.nlp.ie.util.RelationTriple;
//	import edu.stanford.nlp.ling.CoreAnnotations;
//	import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
//	import edu.stanford.nlp.pipeline.Annotation;
//	import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//	import edu.stanford.nlp.util.CoreMap;
//	
//	
//	public class ExtractTriple2 {
//
//	/**
//	 * A demo illustrating how to call the OpenIE system programmatically.
//	 */
//
//	  public static void main(String[] args) throws Exception {
//	    // Create the Stanford CoreNLP pipeline
//	    Properties props = new Properties();
////	    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
//	    StanfordCoreNLP pipeline = new StanfordCoreNLP("stanford-chinese-corenlp-2016-01-19-models\\StanfordCoreNLP-chinese2.properties");
//
//	    // Annotate an example document.
//	    Annotation doc = new Annotation("我的名字叫做智慧与帅气并存的拔子");
//	    pipeline.annotate(doc);
//	    System.out.println(pipeline);
//	    // Loop over sentences in the document
//	    for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
//	      // Get the OpenIE triples for the sentence
//	      Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
//	      // Print the triples
//	      for (RelationTriple triple : triples) {
//	        System.out.println(triple.confidence + "\t" +
//	            triple.subjectLemmaGloss() + "\t" +
//	            triple.relationLemmaGloss() + "\t" +
//	            triple.objectLemmaGloss());
//	      }
//	    }
//	  }
//
//}
