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
		String ClassName1 ="国家";
		String ClassName2 ="地区";
		String indiv1 = "中国";
		String indiv2 = "台湾";
		String annotation ="借...之势";
		String SchemaAdd = "D:\\语义校正\\protege\\owl\\Ontology010.owl";
		String TestDataAdd= "D:\\语义校正\\protege\\owl\\Ontology009_data3.owl";
		String DataAdd="D:\\语义校正\\protege\\owl\\Ontology010_data.owl";
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModel.read(SchemaAdd);
		OntModel dataModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		dataModel.read(TestDataAdd);
		String schemaprefix = ontModel.getNsPrefixURI("");
		String dataprefix=dataModel.getNsPrefixURI("");
		System.out.println("schema 缩短网址：    "+schemaprefix); 
		System.out.println("data 缩短网址：    "+dataprefix);
		

//			if(c.getLocalName().equals(ClassName)){		//获取某个类 UIR
//				System.out.println("当前类  URI:  " + c.getURI());
//
//				for (Iterator it = c.listSuperClasses();it.hasNext();){ 	// 遍历父类
//					OntClass sp = (OntClass)it.next();
//					System.out.println("父类  URI:  " + sp.getURI());		//输出父类URI
//				}
//			}
//		}
		
		

//		//构建第一个实例
		OntClass ocs1=dataModel.createClass(schemaprefix + ClassName1);	
		ocs1.createIndividual(schemaprefix + indiv1);
//		for (Iterator <OntClass> in = ontModel.listClasses();in.hasNext();){
//			OntClass oc =(OntClass)in.next();
//			if(!oc.isAnon()){
//				System.out.println(oc);
//			}
//		}
//		
//		//构建第二个实例
//		//OntClass ocs3 = dataModel.createClass(dataprefix + indiv2);	//构建新类
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
		 System.out.println("创建属性:  "+pro);
		 sta1.addProperty(pro, sta2);
		 System.out.println("属性添加成功！ ");
//		 Statement statement = ontModel.createStatement(sta1,pro,sta2);
//		 ontModel.add(statement);
//		 System.out.println("属性:   "+statement);
		 
			for (Iterator i = dataModel.listClasses();i.hasNext();){
				OntClass c =(OntClass) i.next();
				if(!c.isAnon()){					//获取所有类 
					System.out.print("Class :   ");
					System.out.println(c.getURI()); //获取类完整URI
					System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI())); //获取类简短URI 即#之后内容
					System.out.println("ontClass:  " + c.getLocalName());	//获取类名
				}
			}
		 
		 
		 
		try{
			dataModel.write(new FileOutputStream(DataAdd),"RDF/XML-ABBREV");
			System.out.println("生成完成!");
		}catch (IOException ioe ){
			System.err.println(ioe.toString());
		}
		
		//推理部分
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
