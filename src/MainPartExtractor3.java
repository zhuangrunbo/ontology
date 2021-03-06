
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseTreebankLanguagePack;
import edu.stanford.nlp.util.CoreMap;

/**
 * 提取主谓宾
 *
 * @author bazi
 */
public class MainPartExtractor3
{
	
	
    //private static final Logger LOG = LoggerFactory.getLogger(MainPartExtractor.class);
    private static LexicalizedParser lp;
//    private static DependencyParser lp2;
    //private static GrammaticalStructureFactory gsf;
    private static GrammaticalStructureFactory gsf;
    public static IndexedWord nothing = new IndexedWord().NO_WORD;
    static
    {
        //模型
        String models = "D:\\语义校正\\本体库\\NLP-API\\stanford-chinese-corenlp-2016-10-31-models\\edu\\stanford\\nlp\\models\\lexparser\\chineseFactored.ser.gz";
        String models2 = "D:\\语义校正\\本体库\\NLP-API\\stanford-chinese-corenlp-2016-10-31-models\\edu\\stanford\\nlp\\models\\parser\\nndep\\CTB_CoNLL_params.txt.gz";
        //LOG.info("载入文法模型：" + models);
        lp = LexicalizedParser.loadModel(models);

        //lp2 = DependencyParser.loadFromModelFile(models2);
        //汉语
        //TreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        ChineseTreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        gsf = tlp.grammaticalStructureFactory();
    }

    /**
     * 获取句子的主谓宾
     *
     * @param sentence 问题
     * @return 问题结构
     */
    public  MainPart getMainPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
    	Collection<TypedDependency> wordList = seg(sentence);
        //System.out.println(lp2.predict(sentence));
        return getMainPart(wordList);
    }

    public  MainPart getSecondPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
    	Collection<TypedDependency> wordList = seg(sentence);
        return getSecondPart(wordList);
    }
    
    public  MainPart getThirdPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
        Collection<TypedDependency> wordList = seg(sentence);
        return getThirdPart(wordList);
    }
    
    public  MainPart getFourthPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
        Collection<TypedDependency> wordList = seg(sentence);
        return getFourthPart(wordList);
    }
    
    public  MainPart getFifthPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
        Collection<TypedDependency> wordList = seg(sentence);
        return getFifthPart(wordList);
    }
    
    public  MainPart getSixthPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
        Collection<TypedDependency> wordList = seg(sentence);
        return getSixthPart(wordList);
    }
    
    public MainPart getSeventhPart(CoreMap sentence)
    {
        // 去掉不可见字符
        //sentence = sentence.replace("\\s+", "");
        // 分词，用空格隔开
        Collection<TypedDependency> wordList = seg(sentence);
        return getSeventhPart(wordList);
    }
    /**
     * 获取句子的主谓宾
     *
     * @param words    HashWord列表
     * @return 问题结构
     */
    /* 基本句型 */
    public static MainPart getMainPart(Collection<TypedDependency> words)
    {

//    	List<List<IndexedWord>> r = new ArrayList<List<IndexedWord>>();//每次分词都重置容器上一次存储的分词结果
//    	List<IndexedWord> r2 = new ArrayList<IndexedWord>();
    	MainPart mainPart = new MainPart();
//    	mainPart.subject.setValue("-");
//    	mainPart.predicate.setValue("-");
//    	mainPart.object.setValue("-");
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//            	//GrammaticalStructure gs = gsf.newGrammaticalStructure(tree); 
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
                //LOG.info("依存关系：{}", tdls);

                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
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
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


        return mainPart;
    }
    
    /* 第二句型 */
    public static MainPart getSecondPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("依存关系：{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
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
                combineObject2(words, mainPart.predicate,mainPart.object);
                //replaceToNN(tdls,mainPart.object,mainPart.object);
                replaceTotraverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);

        return mainPart;
    }
    /*第三句型 */
    public static MainPart getThirdPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("依存关系：{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
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
                traverseNN(words,mainPart.subject,mainPart.subject);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


        return mainPart;
    }

    /*第四句型 */
    public static MainPart getFourthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("依存关系：{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
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
                traverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);



        return mainPart;
    }
    
    /*第五句型 */
    public static MainPart getFifthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("依存关系：{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
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
                traverseNN(words,mainPart.subject,mainPart.subject);
                traverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


        return mainPart;
    }
    
    /*第六句型 */
    public static MainPart getSixthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
//        Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("依存关系：{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
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
                replaceToCCOMP(words,mainPart.predicate);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


       

        return mainPart;
    }
    
    /*第七句型 */
    public static MainPart getSeventhPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
//        Tree tree = lp.apply(words);
       //LOG.info("句法树:{}", tree.pennString());
        // 根据整个句子的语法类型来采用不同的策略提取主干
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // 名词短语，认为只有主语，将所有短NP拼起来作为主语即可
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("依存关系：{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("中心词语：", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
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
		                    case "attr":
                                mainPart.object = dep;
                                break;
                           
                            case "conj":
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
                replaceToCCOMP(words,mainPart.predicate);
                combineObject2(words,mainPart.predicate,mainPart.object);
                replaceTotraverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);



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
//    public static List<String> getPhraseList(String type, CoreMap sentence)
//    {
//        return getPhraseList(type, lp.apply(seg(sentence)));
//    }

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
    private static void combineObject2(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
    {
        if (target == null) return;
        for (TypedDependency td : tdls)
        {
            // 依存关系的出发节点，依存关系，以及结束节点
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if (dep.equals(locate))
            {
                switch (shortName)
                {
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
    private static void replaceToNN(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
    {
        for (TypedDependency td : tdls)
        {
            // 依存关系的出发节点，依存关系，以及结束节点
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if(gov.equals(locate)){
            switch (shortName)
	            {
	                case "nn":
	                    target.setValue(dep.value().toString());
	                    return;
	            }
            }
        }
    }
    
    private static void traverseNN(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
    {
        for (TypedDependency td : tdls)
        {
            // 依存关系的出发节点，依存关系，以及结束节点
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if(gov.equals(locate)){
            switch (shortName)
	            {
	                case "nn":
	                    target.setValue(dep.value().toString()+target.value());
	                    traverseNN(tdls,target,dep);
	                    return;
	            }
            }
        }
    }
    
    private static void replaceToCCOMP(Collection<TypedDependency> tdls, IndexedWord target)
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
	                case "ccomp":
	                    target.setValue(dep.value().toString());
	                    return;
	            }
        }
    }
    
    private static void replaceTotraverseNN(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
    {
        for (TypedDependency td : tdls)
        {
            // 依存关系的出发节点，依存关系，以及结束节点
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if(gov.equals(locate)){
            switch (shortName)
	            {
	                case "nn":
	                    target.setValue(dep.value().toString());
	                    traverseNN(tdls,target,dep);
	                    return;
	            }
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
    private  Collection<TypedDependency> seg(CoreMap sentence)
    {
        //分词
        //LOG.info("正在对短句进行分词：" + sentence);
        List<Word> wordList = new LinkedList<>();
        //MainPartSegment seg = new MainPartSegment();
        //List<CoreLabel> terms = new ArrayList<CoreLabel>();
        SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
        Collection<TypedDependency> terms2;
        terms2 = graph.typedDependencies();
        return terms2;
    }

//    public static MainPart getMainPart(String sentence, String delimiter)
//    {
//        List<Word> wordList = new LinkedList<>();
//        for (String word : sentence.split(delimiter))
//        {
//            wordList.add(new Word(word));
//        }
//        return getMainPart(wordList);
//    }
    
    //从当前语法树获取子节点下其他三元体
    public static List<List<IndexedWord>> getOthersTriple(){
		return null;
    	
    }

    public static void FileRead(List<List<String>> strings,String path) throws IOException{
		File f = new File(path);
		File[] list = f.listFiles();
		boolean exist;
		for(int i=0;i<list.length;i++){
			List<String> strings2 = new ArrayList<String>();
			if(list[i].isDirectory()){
				FileRead(strings,list[i].getAbsolutePath());
			}
			else
			{
				BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(list[i].getAbsolutePath()),"UTF-8"));
				//StringBuilder content = new StringBuilder();
				while(true){
					String line = new String();
					line = bf.readLine();
					if(line==null){
						break;
					}
					if(line.isEmpty()){
						continue;
					}
					strings2.add(line);

				}
				bf.close();
				System.out.println(strings2);
				strings.add(strings2);
			}
		}
    }
    /**
     * 调用演示
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {	
    	
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date d1 = new Date();
    	//System.out.println(format.format(d2));
    	//long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

    	//抽取文章中句子，并从中抽取出主谓宾
    	//List<List<IndexedWord>> r ;
    	//List<IndexedWord> r2;
		//Properties props = new Properties();
		List<CoreMap> sentences = new ArrayList<CoreMap>();
		List<CoreMap> sentences2 = new ArrayList<CoreMap>();
		List<List<String>> strings = new ArrayList<List<String>>();
    	FileRead(strings,"D:\\语义校正\\语料库\\新浪微博积极、消极、矛盾微博数据\\test");
    	StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
		String text2 ="台湾总统是马英九";
		String text = "马英九担任台湾总统，我一直很喜欢你，台湾总统是马英九";
		for(int i=0;i<strings.size();i++){
			for(int k=0;k<strings.get(i).size();k++){
			Annotation document = new Annotation(strings.get(i).get(k));
			pipeline.annotate(document);
			sentences2 = document.get(SentencesAnnotation.class);
			System.out.println(sentences2);
			sentences.addAll(sentences2);
			}
		}

		//命名实体识别器，用于将抽取的词进行
        //String serializedClassifier = "stanford-chinese-corenlp-2016-01-19-models\\edu\\stanford\\nlp\\models\\ner\\ner-model.ser.gz";  
        //AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
    	List<List<IndexedWord>> result = new ArrayList<List<IndexedWord>>();
		String ClassName1 =""; //主语分类
		String ClassName2 =""; //宾语分类
		String indiv1 = ""; //主语
		String indiv2 = ""; //宾语	
		String annotation =""; //谓语
		String SchemaAdd = "D:\\语义校正\\protege\\owl\\Ontology350041(5).owl";
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		ontModel.read(SchemaAdd);
		String schemaprefix = ontModel.getNsPrefixURI("");
		for(CoreMap sentence: sentences) {
	    	List<IndexedWord> result2 = new ArrayList<IndexedWord>();

			//获取三个词的分类
			MainPart mp = new MainPart();
			MainPartExtractor3 me = new MainPartExtractor3();
            //mp.sentence.setValue(sentence.toString());//先将句子放入list中第一个位置
            mp = me.getMainPart(sentence);
        	mp.sentence.setValue(sentence.toString());
        	result2.add(mp.sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result2.addAll(mp.r2);
            mp = me.getSecondPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result2.addAll(mp.r2);
            mp = me.getThirdPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result2.addAll(mp.r2);
            mp = me.getFourthPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result2.addAll(mp.r2);
            mp = me.getFifthPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result2.addAll(mp.r2);
            mp = me.getSixthPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result2.addAll(mp.r2);
            mp = me.getSeventhPart(sentence);
            System.out.printf("%s\t%s\n", sentence, mp);
            result2.addAll(mp.r2);
            System.out.println(mp.r);
        	result.add(result2);

		}
		System.out.println(result);

		//将抽取的词语进行owl规则构建
    	Date d2 = new Date();
    	long t1 = d1.getTime();
    	long t2 = d2.getTime(); 	 
    	long millis = (t2 - t1)/1000; 	
    	System.out.println(millis);
    }

}
