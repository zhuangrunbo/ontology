import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;

public class SuperClass {
	public static void main(String args[]){
		String ClassName1 ="����";
		String ClassName2 ="����";
		String indiv1 = "�й�";
		String indiv2 = "̨��";
		String annotation ="��...֮��";
		String SchemaAdd = "D:\\����У��\\protege\\owl\\Ontology010.owl";
		String TestDataAdd= "D:\\����У��\\protege\\owl\\Ontology009_data3.owl";
		String DataAdd="D:\\����У��\\protege\\owl\\Ontology010_data.owl";
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModel.read(SchemaAdd);
		OntModel dataModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		dataModel.read(TestDataAdd);
		String schemaprefix = ontModel.getNsPrefixURI("");
		String dataprefix=dataModel.getNsPrefixURI("");
		System.out.println("schema ������ַ��    "+schemaprefix); 
		System.out.println("data ������ַ��    "+dataprefix);
		

//			if(c.getLocalName().equals(ClassName)){		//��ȡĳ���� UIR
//				System.out.println("��ǰ��  URI:  " + c.getURI());
//
//				for (Iterator it = c.listSuperClasses();it.hasNext();){ 	// ��������
//					OntClass sp = (OntClass)it.next();
//					System.out.println("����  URI:  " + sp.getURI());		//�������URI
//				}
//			}
//		}
		
		

//		//������һ��ʵ��
		OntClass ocs1=dataModel.createClass(schemaprefix + ClassName1);	
		ocs1.createIndividual(schemaprefix + indiv1);
//		for (Iterator <OntClass> in = ontModel.listClasses();in.hasNext();){
//			OntClass oc =(OntClass)in.next();
//			if(!oc.isAnon()){
//				System.out.println(oc);
//			}
//		}
//		
//		//�����ڶ���ʵ��
//		//OntClass ocs3 = dataModel.createClass(dataprefix + indiv2);	//��������
		OntClass ocs4=dataModel.createClass(schemaprefix + ClassName2);
		ocs4.createIndividual(schemaprefix + indiv2);
//		for (Iterator <OntClass> in2 = ontModel.listClasses();in2.hasNext();){
//			OntClass oc =(OntClass)in2.next();
//			if(!oc.isAnon()){
//				System.out.println(oc);
//			}
//		}
		
		
		 Resource sta1 = dataModel.createResource(schemaprefix + indiv1);
		 Resource sta2 = dataModel.createResource(schemaprefix + indiv2);
		 Property pro= dataModel.createAnnotationProperty(schemaprefix +annotation);
		 System.out.println("��������:  "+pro);
		 sta1.addProperty(pro, sta2);
		 System.out.println("������ӳɹ��� ");
//		 Statement statement = ontModel.createStatement(sta1,pro,sta2);
//		 ontModel.add(statement);
//		 System.out.println("����:   "+statement);
		 
			for (Iterator i = dataModel.listClasses();i.hasNext();){
				OntClass c =(OntClass) i.next();
				if(!c.isAnon()){					//��ȡ������ 
					System.out.print("Class :   ");
					System.out.println(c.getURI()); //��ȡ������URI
					System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI())); //��ȡ����URI ��#֮������
					System.out.println("ontClass:  " + c.getLocalName());	//��ȡ����
				}
			}
		 
		 
		 
		try{
			dataModel.write(new FileOutputStream(DataAdd),"RDF/XML-ABBREV");
			System.out.println("�������!");
		}catch (IOException ioe ){
			System.err.println(ioe.toString());
		}
		
		//������
		Model immodel = FileManager.get().loadModel(SchemaAdd);
		Model ismodel = FileManager.get().loadModel(DataAdd);
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(immodel);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, ismodel);
		
		ValidityReport validity = infmodel.validate();
		if (validity.isValid()) {
		    System.out.println("damn of bitch");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator i = validity.getReports(); i.hasNext(); ) {
		        ValidityReport.Report report = (ValidityReport.Report)i.next();
		        System.out.println(" - " + report);
		    }
		}
	}
}
