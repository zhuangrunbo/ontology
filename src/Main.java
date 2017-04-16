
import java.util.Iterator;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;

public class Main {
	public static void main(String agrs[]){
		OntModel base = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		// 创建基于owl规则和reasoner的模型s
//		 base.read("D:\\语义校正\\protege\\owl\\ontology006ok.owl", "RDF/XML");
//		 Iterator iter = base.listClasses(); 
//		 while (iter.hasNext()) { 
//		     System.out.println(iter.next()); 
//		 } 
		 Model data = FileManager.get().loadModel("D:\\语义校正\\protege\\owl\\Ontology009_data.owl");
		 Model schema = FileManager.get().loadModel("D:\\语义校正\\protege\\owl\\Ontology010.owl");
		 Reasoner rs = ReasonerRegistry.getOWLReasoner();
		 InfModel im = ModelFactory.createInfModel(rs,schema,data);
		 ValidityReport vp = im.validate();
		 if (vp.isValid()) {
			    System.out.println("OK");
		} else {
			    System.out.println("Conflicts");
			    for (Iterator i = vp.getReports(); i.hasNext(); ) {
			        ValidityReport.Report report = (ValidityReport.Report)i.next();
			        System.out.println(" - " + report);
			    }
		}

		 
	}
}
