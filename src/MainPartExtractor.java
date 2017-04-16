
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.util.FileManager;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseTreebankLanguagePack;
import edu.stanford.nlp.util.CoreMap;

/**
 * 提取主谓宾
 *
 * @author bazi
 */
public class MainPartExtractor
{

    //private static final Logger LOG = LoggerFactory.getLogger(MainPartExtractor.class);
    private static LexicalizedParser lp;
    //private static DependencyParser lp;
    private static GrammaticalStructureFactory gsf;
    public static IndexedWord nothing = new IndexedWord().NO_WORD;
    static
    {
        //模型
        String models = "D:\\stanford-chinese-corenlp-2016-01-19-models\\edu\\stanford\\nlp\\models\\lexparser\\chineseFactored.ser.gz";
        //LOG.info("载入文法模型：" + models);
        //lp = LexicalizedParser.loadModel(models);
        lp = LexicalizedParser.loadModel(models);
        //汉语
        TreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        //TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        gsf =  tlp.grammaticalStructureFactory();
        
    }

    /**
     * 获取句子的主谓宾
     *
     * @param sentence 问题
     * @return 问题结构
     */
    public static MainPart getMainPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
        List<Word> wordList = seg(sentence);
        return getMainPart(wordList);
    }

    public static MainPart getSecondPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
        List<Word> wordList = seg(sentence);
        return getSecondPart(wordList);
    }
    /**
     * 获取句子的主谓宾
     *
     * @param words    HashWord列表
     * @return 问题结构
     */
    /* 主句分词 */
    public static MainPart getMainPart(List<Word> words)
    {

//    	List<List<IndexedWord>> r = new ArrayList<List<IndexedWord>>();//每次分词都重置容器上一次存储的分词结果
//    	List<IndexedWord> r2 = new ArrayList<IndexedWord>();
    	MainPart mainPart = new MainPart();
//    	mainPart.subject.setValue("-");
//    	mainPart.predicate.setValue("-");
//    	mainPart.object.setValue("-");
        if (words == null || words.size() == 0) return mainPart;
        Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
        switch (tree.firstChild().label().toString())
        {
            case "NP":
                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
                mainPart = getNPPhraseMainPart(tree);
                break;
            default:
            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
                Collection<TypedDependency> tdls = gs.allTypedDependencies();
                //LOG.info("依存关系：{}", tdls);
                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(tdls);
                if (rootNode == null)
                {
                    return getNPPhraseMainPart(tree);
                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : tdls)
                {	
                	System.out.println(td);
                    // 依存关系的出发节点，依存关系，以及结束节点
                	IndexedWord gov = td.gov();
                    GrammaticalRelation reln = td.reln();
                    String shortName = reln.getShortName();
                    IndexedWord dep = td.dep();
                    if (gov.equals(rootNode))
                    {
                        switch (shortName)
                        {
                        	
                            case "nsubjpass":
                            case "dobj":
                            case "attr":
                                mainPart.object = dep;
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep;
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }

                if(mainPart.subject == null){//防止为NULL出错
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // 尝试合并主语和谓语中的名词性短语
                combineNN(tdls, mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
//                r2.add(mainPart.subject);
//                r2.add(mainPart.predicate);
//                r2.add(mainPart.object);
//                r.add(r2);

        }

        return mainPart;
    }
    
    /* 主句下子节点分词 */
    public static MainPart getSecondPart(List<Word> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
        switch (tree.firstChild().label().toString())
        {
            case "NP":
                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
                mainPart = getNPPhraseMainPart(tree);
                break;
            default:
            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
                Collection<TypedDependency> tdls = gs.allTypedDependencies();
                //LOG.info("依存关系：{}", tdls);
                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(tdls);
                if (rootNode == null)
                {
                    return getNPPhraseMainPart(tree);
                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : tdls)
                {	
                	System.out.println(td);
                    // 依存关系的出发节点，依存关系，以及结束节点
                	IndexedWord gov = td.gov();
                    GrammaticalRelation reln = td.reln();
                    String shortName = reln.getShortName();
                    IndexedWord dep = td.dep();
                    if (gov.equals(rootNode))
                    {
                        switch (shortName)
                        {
                        	
                            //case "nsubjpass":
                            //case "dobj":
                            //case "attr":   	
		                    case "nsubjpass":
		                    case "dobj":
		                    case "attr":
                                mainPart.object = dep;
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep;
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                if(mainPart.subject == null){//防止为NULL出错
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // 尝试合并主语和谓语中的名词性短语
                combineNN2(tdls, mainPart.predicate);
                replaceObjectNN(tdls,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
//                r2.add(mainPart.subject);
//                r2.add(mainPart.predicate);
//                r2.add(mainPart.object);
//                r.add(r2);

        }

        return mainPart;
    }

    public static MainPart getThirdPart(List<Word> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
        switch (tree.firstChild().label().toString())
        {
            case "NP":
                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
                mainPart = getNPPhraseMainPart(tree);
                break;
            default:
            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
                Collection<TypedDependency> tdls = gs.allTypedDependencies();
                //LOG.info("依存关系：{}", tdls);
                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(tdls);
                if (rootNode == null)
                {
                    return getNPPhraseMainPart(tree);
                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : tdls)
                {	
                	System.out.println(td);
                    // 依存关系的出发节点，依存关系，以及结束节点
                	IndexedWord gov = td.gov();
                    GrammaticalRelation reln = td.reln();
                    String shortName = reln.getShortName();
                    IndexedWord dep = td.dep();
                    if (gov.equals(rootNode))
                    {
                        switch (shortName)
                        {
                        	
                            //case "nsubjpass":
                            //case "dobj":
                            //case "attr":   	
		                    case "nsubjpass":
		                    case "dobj":
		                    case "attr":
                                mainPart.object = dep;
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep;
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }

                // 尝试合并主语和谓语中的名词性短语
                combineNN2(tdls, mainPart.predicate);
                replaceObjectNN(tdls,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
//                r2.add(mainPart.subject);
//                r2.add(mainPart.predicate);
//                r2.add(mainPart.object);
//                r.add(r2);

        }

        return mainPart;
    }

    
    private static MainPart getNPPhraseMainPart(Tree tree)
    {
        MainPart mainPart = new MainPart();
        StringBuilder sbResult = new StringBuilder();
        List<String> phraseList = getPhraseList("NP", tree);
        for (String phrase : phraseList)
        {
            sbResult.append(phrase);
        }
        mainPart.result = sbResult.toString();
        return mainPart;
    }

    /**
     * 从句子中提取最小粒度的短语
     * @param type
     * @param sentence
     * @return
     */
    public static List<String> getPhraseList(String type, CoreMap sentence)
    {
        return getPhraseList(type, lp.apply(seg(sentence)));
    }

    private static List<String> getPhraseList(String type, Tree tree)
    {
        List<String> phraseList = new LinkedList<String>();
        for (Tree subtree : tree)
        {
            if(subtree.isPrePreTerminal() && subtree.label().value().equals(type))
            {
                StringBuilder sbResult = new StringBuilder();
                for (Tree leaf : subtree.getLeaves())
                {
                    sbResult.append(leaf.value());
                }
                phraseList.add(sbResult.toString());
            }
        }
        return phraseList;
    }

    /**
     * 合并名词性短语为一个节点
     * @param tdls 依存关系集合
     * @param target 目标节点 合并名词于目标节点前
     */
    private static void combineNN(Collection<TypedDependency> tdls, IndexedWord target)
    {
        if (target == null) return;
        for (TypedDependency td : tdls)
        {
            // 依存关系的出发节点，依存关系，以及结束节点
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if (gov.equals(target))
            {
                switch (shortName)
                {
                    case "nn":
                    case "dobj":
                        target.setValue(dep.value().toString() + target.value());
                        return;
                }
            }
        }

    }
    
    /**
     * 合并名词性短语为一个节点
     * @param tdls 依存关系集合
     * @param target 目标节点 合并名词于目标节点后
     */
    private static void combineNN2(Collection<TypedDependency> tdls, IndexedWord target)
    {
        if (target == null) return;
        for (TypedDependency td : tdls)
        {
            // 依存关系的出发节点，依存关系，以及结束节点
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if (gov.equals(target))
            {
                switch (shortName)
                {
                    case "nn":
                    case "dobj":
                        target.setValue(target.value()+dep.value().toString());
                        return;
                }
            }
        }
    }
    
    /**
     * 将宾语替换为名词性短语为一个节点
     * @param tdls 依存关系集合
     * @param target 直接覆盖宾语
     */
    private static void replaceObjectNN(Collection<TypedDependency> tdls, IndexedWord target)
    {
        for (TypedDependency td : tdls)
        {
            // 依存关系的出发节点，依存关系，以及结束节点
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            switch (shortName)
            {
                case "nn":
                    target.setValue(dep.value().toString());
                    return;
            }
        }
    }
    
    private static IndexedWord getRootNode(Collection<TypedDependency> tdls)
    {
        for (TypedDependency td : tdls)
        {
            if (td.reln() == GrammaticalRelation.ROOT)
            {
                return td.dep();
            }
        }

        return null;
    }

    /**
     * 分词
     *
     * @param sentence 句子
     * @return 分词结果
     */
    private static List<Word> seg(CoreMap sentence)
    {
        //分词
        //LOG.info("正在对短句进行分词：" + sentence);
        List<Word> wordList = new LinkedList<>();
        //MainPartSegment seg = new MainPartSegment();
        
        List<CoreLabel> terms = sentence.get(TokensAnnotation.class);
        StringBuffer sbLogInfo = new StringBuffer();
        for (CoreLabel term : terms)
        {
            Word word = new Word(term.word());
            wordList.add(word);
            sbLogInfo.append(word);
            sbLogInfo.append(' ');
        }
        //LOG.info("分词结果为：" + sbLogInfo);
        return wordList;
    }

    public static MainPart getMainPart(String sentence, String delimiter)
    {
        List<Word> wordList = new LinkedList<>();
        for (String word : sentence.split(delimiter))
        {
            wordList.add(new Word(word));
        }
        return getMainPart(wordList);
    }
    
    //从当前语法树获取子节点下其他三元体
    public static List<List<IndexedWord>> getOthersTriple(){
		return null;
    	
    }

    /**
     * 调用演示
     * @param args
     */
    public static void main(String[] args)
    {	
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date d1 = new Date();
    	//System.out.println(format.format(d2));
    	//long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

    	//抽取文章中句子，并从中抽取出主谓宾
    	//List<List<IndexedWord>> r ;
    	//List<IndexedWord> r2;
		//Properties props = new Properties();
		StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
		String text2 ="台湾总统是马英九";
		String text = "马英九担任台湾总统，我一直很喜欢你，台湾总统是马英九";
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		System.out.println(sentences);
		//命名实体识别器，用于将抽取的词进行
        //String serializedClassifier = "stanford-chinese-corenlp-2016-01-19-models\\edu\\stanford\\nlp\\models\\ner\\ner-model.ser.gz";  
        //AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
    	List<List<IndexedWord>> result = new ArrayList<List<IndexedWord>>();
    	List<IndexedWord> result2 = new ArrayList<IndexedWord>();
		String ClassName1 =""; //主语分类
		String ClassName2 =""; //宾语分类
		String indiv1 = ""; //主语
		String indiv2 = ""; //宾语	
		String annotation =""; //谓语
		String SchemaAdd = "D:\\语义校正\\protege\\owl\\Ontology1357149768709(1).owl";
		String DataAdd="D:\\语义校正\\protege\\owl\\Ontology010_data.owl";
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModel.read(SchemaAdd);
		String schemaprefix = ontModel.getNsPrefixURI("");
		for(CoreMap sentence: sentences) {

			//获取三个词的分类
            MainPart mp = MainPartExtractor.getMainPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            mp = MainPartExtractor.getSecondPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result.addAll(mp.r);
            //获取三个词的分类
            //String subjectclass = classifier.classifyToString(mp.subject.value()).substring(mp.subject.value().length()+1);
            //System.out.println(subjectclass);
            //此处设置为当宾语为空值时自动将谓语的值设定为null
            //String predicateclass = "";
//            if(mp.object == null){
//            mp.object = mp.predicate.makeCopy();
//           	mp.predicate.setValue("");
//            }
//            else{
//            predicateclass = classifier.classifyToString(mp.predicate.value()).substring(mp.predicate.value().length()+1);
//            }
            //System.out.println(predicateclass);
            
          //此处设置为当宾语为空值时自动将宾语的值设定为谓语
          //  String objectclass = classifier.classifyToString(mp.object.value()).substring(mp.object.value().length()+1);
            //System.out.println(objectclass);
            //mp.subject.setNER(subjectclass);
        	//mp.predicate.setNER(predicateclass);
        	//mp.object.setNER(objectclass);
		//抽取文章中句子，并从中抽取出主谓宾
		//将抽取的词语进行owl规则构建

		System.out.println("schema 缩短网址：    "+schemaprefix); 
		
		}
		
		for(int k = 0;k<result.size();k++){
		indiv1 = result.get(k).get(0).value(); //主语
		indiv2 = result.get(k).get(2).value(); //宾语	
		annotation = result.get(k).get(1).value(); //谓语

		 Resource sta1 = ontModel.createResource(schemaprefix + indiv1);
		 Resource sta2 = ontModel.createResource(schemaprefix + indiv2);
		 Property pro= ontModel.createAnnotationProperty(schemaprefix +annotation);
		 System.out.println("创建属性:  "+pro);
		 sta1.addProperty(pro, sta2);
		 System.out.println("属性添加成功！ ");

//		 Statement statement = ontModel.createStatement(sta1,pro,sta2);
//		 ontModel.add(statement);
//		 System.out.println("属性:   "+statement);
		 
			for (Iterator i = ontModel.listClasses();i.hasNext();){
				OntClass c =(OntClass) i.next();
				if(!c.isAnon()){					//获取所有类 
					System.out.print("Class :   ");
					System.out.println(c.getURI()); //获取类完整URI
					System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI())); //获取类简短URI 即#之后内容
					System.out.println("ontClass:  " + c.getLocalName());	//获取类名
				}
			}
    	}	
		 
		try{
			ontModel.write(new FileOutputStream(DataAdd),"RDF/XML-ABBREV");
			System.out.println("生成完成!");
		}catch (IOException ioe ){
			System.err.println(ioe.toString());
		}
		
		//推理部分
		Model immodel = FileManager.get().loadModel(SchemaAdd);
		Model ismodel = FileManager.get().loadModel(SchemaAdd);
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
		//将抽取的词语进行owl规则构建
    	Date d2 = new Date();
    	long t1 = d1.getTime();
    	long t2 = d2.getTime(); 	 
    	long millis = (t2 - t1)/1000; 	
    	System.out.println(millis);
    }

}