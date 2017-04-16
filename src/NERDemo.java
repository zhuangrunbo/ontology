import java.io.IOException;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
	
public class NERDemo {
	    public static void main(String[] args) throws IOException {       
	        String serializedClassifier = "stanford-chinese-corenlp-2016-01-19-models\\edu\\stanford\\nlp\\models\\ner\\ner-model.ser.gz";  
	        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
	        String s1 = "ÂíÓ¢¾Å";  
	        System.out.println(classifier.classifyToString(s1));  
	          
	        String s2 = "";  
	        System.out.println(classifier.classifyToString(s2));  
	    }
}
