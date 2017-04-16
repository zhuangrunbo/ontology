
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
 * ��ȡ��ν��
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
        //ģ��
        String models = "D:\\����У��\\�����\\NLP-API\\stanford-chinese-corenlp-2016-10-31-models\\edu\\stanford\\nlp\\models\\lexparser\\chineseFactored.ser.gz";
        String models2 = "D:\\����У��\\�����\\NLP-API\\stanford-chinese-corenlp-2016-10-31-models\\edu\\stanford\\nlp\\models\\parser\\nndep\\CTB_CoNLL_params.txt.gz";
        //LOG.info("�����ķ�ģ�ͣ�" + models);
        lp = LexicalizedParser.loadModel(models);

        //lp2 = DependencyParser.loadFromModelFile(models2);
        //����
        //TreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        ChineseTreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        gsf = tlp.grammaticalStructureFactory();
    }

    /**
     * ��ȡ���ӵ���ν��
     *
     * @param sentence ����
     * @return ����ṹ
     */
    public  MainPart getMainPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
    	Collection<TypedDependency> wordList = seg(sentence);
        //System.out.println(lp2.predict(sentence));
        return getMainPart(wordList);
    }

    public  MainPart getSecondPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
    	Collection<TypedDependency> wordList = seg(sentence);
        return getSecondPart(wordList);
    }
    
    public  MainPart getThirdPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        Collection<TypedDependency> wordList = seg(sentence);
        return getThirdPart(wordList);
    }
    
    public  MainPart getFourthPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        Collection<TypedDependency> wordList = seg(sentence);
        return getFourthPart(wordList);
    }
    
    public  MainPart getFifthPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        Collection<TypedDependency> wordList = seg(sentence);
        return getFifthPart(wordList);
    }
    
    public  MainPart getSixthPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        Collection<TypedDependency> wordList = seg(sentence);
        return getSixthPart(wordList);
    }
    
    public MainPart getSeventhPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        Collection<TypedDependency> wordList = seg(sentence);
        return getSeventhPart(wordList);
    }
    /**
     * ��ȡ���ӵ���ν��
     *
     * @param words    HashWord�б�
     * @return ����ṹ
     */
    /* �������� */
    public static MainPart getMainPart(Collection<TypedDependency> words)
    {

//    	List<List<IndexedWord>> r = new ArrayList<List<IndexedWord>>();//ÿ�ηִʶ�����������һ�δ洢�ķִʽ��
//    	List<IndexedWord> r2 = new ArrayList<IndexedWord>();
    	MainPart mainPart = new MainPart();
//    	mainPart.subject.setValue("-");
//    	mainPart.predicate.setValue("-");
//    	mainPart.object.setValue("-");
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("�䷨��:{}", tree.pennString());
        // �����������ӵ��﷨���������ò�ͬ�Ĳ�����ȡ����
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // ���ʶ����Ϊֻ����������ж�NPƴ������Ϊ���Ｔ��
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//            	//GrammaticalStructure gs = gsf.newGrammaticalStructure(tree); 
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
                //LOG.info("�����ϵ��{}", tdls);

                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                	System.out.println(td);
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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

                if(mainPart.subject == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // ���Ժϲ������ν���е������Զ���
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


        return mainPart;
    }
    
    /* �ڶ����� */
    public static MainPart getSecondPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("�䷨��:{}", tree.pennString());
        // �����������ӵ��﷨���������ò�ͬ�Ĳ�����ȡ����
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // ���ʶ����Ϊֻ����������ж�NPƴ������Ϊ���Ｔ��
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("�����ϵ��{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                	System.out.println(td);
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
                if(mainPart.subject == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // ���Ժϲ������ν���е������Զ���
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
    /*�������� */
    public static MainPart getThirdPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("�䷨��:{}", tree.pennString());
        // �����������ӵ��﷨���������ò�ͬ�Ĳ�����ȡ����
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // ���ʶ����Ϊֻ����������ж�NPƴ������Ϊ���Ｔ��
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("�����ϵ��{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                	System.out.println(td);
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
                if(mainPart.subject == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // ���Ժϲ������ν���е������Զ���
                traverseNN(words,mainPart.subject,mainPart.subject);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


        return mainPart;
    }

    /*���ľ��� */
    public static MainPart getFourthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("�䷨��:{}", tree.pennString());
        // �����������ӵ��﷨���������ò�ͬ�Ĳ�����ȡ����
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // ���ʶ����Ϊֻ����������ж�NPƴ������Ϊ���Ｔ��
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("�����ϵ��{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                	System.out.println(td);
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
                if(mainPart.subject == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // ���Ժϲ������ν���е������Զ���
                traverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);



        return mainPart;
    }
    
    /*������� */
    public static MainPart getFifthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        //Tree tree = lp.apply(words);
       //LOG.info("�䷨��:{}", tree.pennString());
        // �����������ӵ��﷨���������ò�ͬ�Ĳ�����ȡ����
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // ���ʶ����Ϊֻ����������ж�NPƴ������Ϊ���Ｔ��
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("�����ϵ��{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                	System.out.println(td);
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
                if(mainPart.subject == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // ���Ժϲ������ν���е������Զ���
                traverseNN(words,mainPart.subject,mainPart.subject);
                traverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


        return mainPart;
    }
    
    /*�������� */
    public static MainPart getSixthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
//        Tree tree = lp.apply(words);
       //LOG.info("�䷨��:{}", tree.pennString());
        // �����������ӵ��﷨���������ò�ͬ�Ĳ�����ȡ����
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // ���ʶ����Ϊֻ����������ж�NPƴ������Ϊ���Ｔ��
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("�����ϵ��{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                	System.out.println(td);
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
                if(mainPart.subject == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // ���Ժϲ������ν���е������Զ���
                replaceToCCOMP(words,mainPart.predicate);
                if (!mainPart.isDone()) mainPart.done();
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


       

        return mainPart;
    }
    
    /*���߾��� */
    public static MainPart getSeventhPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
//        Tree tree = lp.apply(words);
       //LOG.info("�䷨��:{}", tree.pennString());
        // �����������ӵ��﷨���������ò�ͬ�Ĳ�����ȡ����
//        switch (tree.firstChild().label().toString())
//        {
//            case "NP":
//                // ���ʶ����Ϊֻ����������ж�NPƴ������Ϊ���Ｔ��
//                mainPart = getNPPhraseMainPart(tree);
//                break;
//            default:
//            	GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
//                Collection<TypedDependency> tdls = gs.allTypedDependencies();
//                //LOG.info("�����ϵ��{}", tdls);
//                System.out.println(tdls);
                
                IndexedWord rootNode = getRootNode(words);
//                if (rootNode == null)
//                {
//                    return getNPPhraseMainPart(tree);
//                }
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                	System.out.println(td);
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
                if(mainPart.subject == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                }
                if(mainPart.predicate == null){
                	mainPart.predicate = nothing;
                }
                if(mainPart.object == null){
                	mainPart.object = nothing;
                }
                // ���Ժϲ������ν���е������Զ���
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
     * �Ӿ�������ȡ��С���ȵĶ���
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
     * �ϲ������Զ���Ϊһ���ڵ�
     * @param tdls �����ϵ����
     * @param target Ŀ��ڵ� �ϲ�������Ŀ��ڵ�ǰ
     */
    private static void combineNN(Collection<TypedDependency> tdls, IndexedWord target)
    {
        if (target == null) return;
        for (TypedDependency td : tdls)
        {
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
     * �ϲ������Զ���Ϊһ���ڵ�
     * @param tdls �����ϵ����
     * @param target Ŀ��ڵ� �ϲ�������Ŀ��ڵ��
     */
    private static void combineObject2(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
    {
        if (target == null) return;
        for (TypedDependency td : tdls)
        {
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
     * �������滻Ϊ�����Զ���Ϊһ���ڵ�
     * @param tdls �����ϵ����
     * @param target ֱ�Ӹ��Ǳ���
     */
    private static void replaceToNN(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
    {
        for (TypedDependency td : tdls)
        {
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
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
     * �ִ�
     *
     * @param sentence ����
     * @return �ִʽ��
     */
    private  Collection<TypedDependency> seg(CoreMap sentence)
    {
        //�ִ�
        //LOG.info("���ڶԶ̾���зִʣ�" + sentence);
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
    
    //�ӵ�ǰ�﷨����ȡ�ӽڵ���������Ԫ��
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
     * ������ʾ
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {	
    	
    	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date d1 = new Date();
    	//System.out.println(format.format(d2));
    	//long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

    	//��ȡ�����о��ӣ������г�ȡ����ν��
    	//List<List<IndexedWord>> r ;
    	//List<IndexedWord> r2;
		//Properties props = new Properties();
		List<CoreMap> sentences = new ArrayList<CoreMap>();
		List<CoreMap> sentences2 = new ArrayList<CoreMap>();
		List<List<String>> strings = new ArrayList<List<String>>();
    	FileRead(strings,"D:\\����У��\\���Ͽ�\\����΢��������������ì��΢������\\test");
    	StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
		String text2 ="̨����ͳ����Ӣ��";
		String text = "��Ӣ�ŵ���̨����ͳ����һֱ��ϲ���㣬̨����ͳ����Ӣ��";
		for(int i=0;i<strings.size();i++){
			for(int k=0;k<strings.get(i).size();k++){
			Annotation document = new Annotation(strings.get(i).get(k));
			pipeline.annotate(document);
			sentences2 = document.get(SentencesAnnotation.class);
			System.out.println(sentences2);
			sentences.addAll(sentences2);
			}
		}

		//����ʵ��ʶ���������ڽ���ȡ�Ĵʽ���
        //String serializedClassifier = "stanford-chinese-corenlp-2016-01-19-models\\edu\\stanford\\nlp\\models\\ner\\ner-model.ser.gz";  
        //AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
    	List<List<IndexedWord>> result = new ArrayList<List<IndexedWord>>();
		String ClassName1 =""; //�������
		String ClassName2 =""; //�������
		String indiv1 = ""; //����
		String indiv2 = ""; //����	
		String annotation =""; //ν��
		String SchemaAdd = "D:\\����У��\\protege\\owl\\Ontology350041(5).owl";
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		ontModel.read(SchemaAdd);
		String schemaprefix = ontModel.getNsPrefixURI("");
		for(CoreMap sentence: sentences) {
	    	List<IndexedWord> result2 = new ArrayList<IndexedWord>();

			//��ȡ�����ʵķ���
			MainPart mp = new MainPart();
			MainPartExtractor3 me = new MainPartExtractor3();
            //mp.sentence.setValue(sentence.toString());//�Ƚ����ӷ���list�е�һ��λ��
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

		//����ȡ�Ĵ������owl���򹹽�
    	Date d2 = new Date();
    	long t1 = d1.getTime();
    	long t2 = d2.getTime(); 	 
    	long millis = (t2 - t1)/1000; 	
    	System.out.println(millis);
    }

}
