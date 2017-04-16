
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
import java.util.LinkedList;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.trees.GrammaticalRelation;
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
public class MainPartExtractor6
{
	
	
    private static LexicalizedParser lp;
    private static GrammaticalStructureFactory gsf;
    public static IndexedWord nothing = new IndexedWord().NO_WORD; 
    public Collection<TypedDependency> wordList = new ArrayList<TypedDependency>();
    public static Collection<TypedDependency> wordList2;
    boolean invalidate;
    static
    {
        //ģ��
        String models = "D:\\����У��\\�����\\NLP-API\\stanford-chinese-corenlp-2016-10-31-models\\edu\\stanford\\nlp\\models\\lexparser\\chineseFactored.ser.gz";
        String models2 = "D:\\����У��\\�����\\NLP-API\\stanford-chinese-corenlp-2016-10-31-models\\edu\\stanford\\nlp\\models\\parser\\nndep\\CTB_CoNLL_params.txt.gz";
        //LOG.info("�����ķ�ģ�ͣ�" + models);
        lp = LexicalizedParser.loadModel(models);
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
    public MainPart getMainPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        wordList2 = new ArrayList<TypedDependency>();
    	Collection<TypedDependency> wordList = seg(sentence);
    	for(TypedDependency td:wordList){
    		wordList2.add(td);
    	}
        //System.out.println(lp2.predict(sentence));
        return getMainPart(wordList2);
    }

    public MainPart getSecondPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        wordList2 = new ArrayList<TypedDependency>();
    	Collection<TypedDependency> wordList = seg(sentence);
    	for(TypedDependency td:wordList){
    		wordList2.add(td);
    	}
        //System.out.println(lp2.predict(sentence));
        return getSecondPart(wordList2);
    }
    
    public MainPart getThirdPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        wordList2 = new ArrayList<TypedDependency>();
    	Collection<TypedDependency> wordList = seg(sentence);
    	for(TypedDependency td:wordList){
    		wordList2.add(td);
    	}
        //System.out.println(lp2.predict(sentence));
        return getThirdPart(wordList2);
    }
    
    public MainPart getFourthPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        wordList2 = new ArrayList<TypedDependency>();
    	Collection<TypedDependency> wordList = seg(sentence);
    	for(TypedDependency td:wordList){
    		wordList2.add(td);
    	}
        //System.out.println(lp2.predict(sentence));
        return getFourthPart(wordList2);
    }
    
    public MainPart getFifthPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        wordList2 = new ArrayList<TypedDependency>();
    	Collection<TypedDependency> wordList = seg(sentence);
    	for(TypedDependency td:wordList){
    		wordList2.add(td);
    	}
        //System.out.println(lp2.predict(sentence));
        return getFifthPart(wordList2);
    }
    
    public MainPart getSixthPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        wordList2 = new ArrayList<TypedDependency>();
    	Collection<TypedDependency> wordList = seg(sentence);
    	for(TypedDependency td:wordList){
    		wordList2.add(td);
    	}
        //System.out.println(lp2.predict(sentence));
        return getSixthPart(wordList2);
    }
    
    public MainPart getSeventhPart(CoreMap sentence)
    {
        // ȥ�����ɼ��ַ�
        //sentence = sentence.replace("\\s+", "");
        // �ִʣ��ÿո����
        wordList2 = new ArrayList<TypedDependency>();
    	Collection<TypedDependency> wordList = seg(sentence);
    	for(TypedDependency td:wordList){
    		wordList2.add(td);
    	}
        //System.out.println(lp2.predict(sentence));
        return getSeventhPart(wordList2);
    }
    /**
     * ��ȡ���ӵ���ν��
     *
     * @param words    HashWord�б�
     * @return ����ṹ
     */
    /* �������� */
    public MainPart getMainPart(Collection<TypedDependency> words)   
    {
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;

                IndexedWord rootNode = getRootNode(words);
                System.out.println(rootNode);
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
		                    case "range":
                                mainPart.object = dep.makeCopy(0);
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep.makeCopy(0);
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                this.invalidate = false;
                // ���Ժϲ������ν���е������Զ���
                if (!mainPart.isDone()) mainPart.done();
                if(invalidate||mainPart.subject == null||mainPart.predicate == null||mainPart.object == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                	mainPart.predicate = nothing;
                	mainPart.object = nothing;
                }
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);


        return mainPart;
    }
    
    /* �ڶ����� */
    public MainPart getSecondPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
                IndexedWord rootNode = getRootNode(words);
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
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
		                    case "range":
                                mainPart.object = dep.makeCopy(0);
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep.makeCopy(0);
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                // ���Ժϲ������ν���е������Զ���
                invalidate = false;
                combineObject2(words, mainPart.predicate, mainPart.object);
                replaceTotraverseNN(words, mainPart.object, mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                if(invalidate||mainPart.subject == null||mainPart.predicate == null||mainPart.object == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                	mainPart.predicate = nothing;
                	mainPart.object = nothing;
                }
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
                

        return mainPart;
    }
    /*�������� */
    public MainPart getThirdPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;    
                IndexedWord rootNode = getRootNode(words);
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
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
		                    case "range":
                                mainPart.object = dep.makeCopy(0);
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep.makeCopy(0);
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                // ���Ժϲ������ν���е������Զ���
                invalidate = false;
                traverseNN(words,mainPart.subject,mainPart.subject);
                if (!mainPart.isDone()) mainPart.done();
                if(invalidate||mainPart.subject == null||mainPart.predicate == null||mainPart.object == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                	mainPart.predicate = nothing;
                	mainPart.object = nothing;
                }
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
                
        return mainPart;
    }

    /*���ľ��� */
    public MainPart getFourthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
                IndexedWord rootNode = getRootNode(words);
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
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
		                    case "range":
                                mainPart.object = dep.makeCopy(0);
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep.makeCopy(0);
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                // ���Ժϲ������ν���е������Զ���
                invalidate = false;
                traverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                if(invalidate||mainPart.subject == null||mainPart.predicate == null||mainPart.object == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                	mainPart.predicate = nothing;
                	mainPart.object = nothing;
                }
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
                



        return mainPart;
    }
    
    /*������� */
    public MainPart getFifthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
        	IndexedWord rootNode = getRootNode(words);
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
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
		                    case "range":
                                mainPart.object = dep.makeCopy(0);
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep.makeCopy(0);
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                invalidate = false;
                // ���Ժϲ������ν���е������Զ���
                traverseNN(words,mainPart.subject,mainPart.subject);
                traverseNN(words,mainPart.object,mainPart.object);
                if (!mainPart.isDone()) mainPart.done();
                if(invalidate||mainPart.subject == null||mainPart.predicate == null||mainPart.object == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                	mainPart.predicate = nothing;
                	mainPart.object = nothing;
                }
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
                


        return mainPart;
    }
    
    /*�������� */
    public MainPart getSixthPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
                IndexedWord rootNode = getRootNode(words);
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
                    // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
                	IndexedWord gov = td.gov();
                    GrammaticalRelation reln = td.reln();
                    String shortName = reln.getShortName();
                    IndexedWord dep = td.dep();
//                    if (gov.equals(rootNode))
//                    {
                    //�˴�ѡ����ν�ﲻһ����root��ѡ��
                        switch (shortName)
                        {
                        	
                            //case "nsubjpass":
                            //case "dobj":
                            //case "attr":   	
		                    case "nsubjpass":
		                    case "dobj":
		                    case "attr":
		                    case "range":
                                mainPart.object = dep.makeCopy(0);
                                break;
                           
                            case "nsubj":
                            case "top":
                                mainPart.subject = dep.makeCopy(0);
                                break;
                        }
//                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                // ���Ժϲ������ν���е������Զ���
                invalidate = false;
                combineCCOMP(words, mainPart.predicate, mainPart.predicate);
                if (!mainPart.isDone()) mainPart.done();
                if(invalidate||mainPart.subject == null||mainPart.predicate == null||mainPart.object == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                	mainPart.predicate = nothing;
                	mainPart.object = nothing;
                }
                mainPart.r2.add(mainPart.subject);
                mainPart.r2.add(mainPart.predicate);
                mainPart.r2.add(mainPart.object);
                mainPart.r.add(mainPart.r2);
                

       

        return mainPart;
    }
    
    /*���߾��� */
    public MainPart getSeventhPart(Collection<TypedDependency> words){
    	MainPart mainPart = new MainPart();
        if (words == null || words.size() == 0) return mainPart;
                IndexedWord rootNode = getRootNode(words);
                //LOG.info("���Ĵ��", rootNode);
                mainPart = new MainPart(rootNode);
                for (TypedDependency td : words)
                {	
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
		                    case "nsubjpass":
		                    case "dobj":
		                    case "range":
                                mainPart.object = dep.makeCopy(0);
                                break;
                           
                            case "conj":
                            case "top":
                                mainPart.subject = dep.makeCopy(0);
                                break;
                        }
                    }
                    if (mainPart.object != null && mainPart.subject != null)
                    {
                        break;
                    }
                }
                // ���Ժϲ������ν���е������Զ���
                invalidate = false;
                traverseNN(words,mainPart.subject,mainPart.subject);
                traverseNN(words,mainPart.object,mainPart.object);
                combineModifier(words, mainPart.predicate, mainPart.predicate);
                if (!mainPart.isDone()) mainPart.done();
                if(invalidate||mainPart.subject == null||mainPart.predicate == null||mainPart.object == null){//��ֹΪNULL����
                	mainPart.subject = nothing;
                	mainPart.predicate = nothing;
                	mainPart.object = nothing;
                }
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
	 * �ϲ�root����ǰ������δ�.
	 * @param tdls �﷨������
	 * @param target Ŀ��Ԫ��
	 * @param locate ��λԪ��
	 */
    private void combineModifier(Collection<TypedDependency> tdls, IndexedWord target ,IndexedWord locate)
    {
        if (target == null) return;
        for (TypedDependency td : tdls)
        {
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if (gov.equals(locate))
            {
                switch (shortName)
                {
                    case "mmod":
                    case "advmod":
                        target.setValue(dep.value().toString() + target.value());
                        return;
                }
            }
        }
        //��δ�ҵ���Ӧ���ԵĴ������ΪʧЧ��Ԫ��
    	this.invalidate = true;

    }
    
    /**
     * �ϲ�root��Object.
     * @param tdls �﷨������
     * @param target Ŀ��Ԫ��
     * @param locate ��λԪ��
     */
    private void combineObject2(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
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
                	case "nsubjpass":
                    case "dobj":
                    case "attr":
                    case "range":
                        target.setValue(target.value()+dep.value().toString());
                        return;
                }
            }
        }
        //��δ�ҵ���Ӧ���ԵĴ������ΪʧЧ��Ԫ��
    	this.invalidate = true;
    }
    
    /**
     * �������滻Ϊ�����Զ���Ϊһ���ڵ�
     * @param tdls �����ϵ����
     * @param target ֱ�Ӹ��Ǳ���
     */
    private void replaceToNN(Collection<TypedDependency> tdls, IndexedWord target, IndexedWord locate)
    {
    	if(target==null||locate==null)return;
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
                    case "assmod":
                    case "nummod":
	                    target.setValue(dep.value().toString());
	                    return;
	            }
            }
        }
        //��δ�ҵ���Ӧ���ԵĴ������ΪʧЧ��Ԫ��
    	this.invalidate = true;
    }
    /**
     * �ݹ�Ѱ�����δ�.
     * @param tdls �﷨������
     * @param target Ŀ��Ԫ��
     * @param locate ��λԪ��
     */
    private void traverseNN(Collection<TypedDependency> tdls, IndexedWord target, IndexedWord locate)
    {
    	if(target==null||locate==null)return;
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
                    case "assmod":
                    case "nummod":
                    case "amod":
	                    target.setValue(dep.value().toString()+target.value());
	                    traverseNN(tdls,target,dep);
	                    return;
	            }
            }
        }
    }
    
    private void replaceToCCOMP(Collection<TypedDependency> tdls, IndexedWord target)
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
        //��δ�ҵ���Ӧ���ԵĴ������ΪʧЧ��Ԫ��
    	this.invalidate = true;
    }
    /**
     * �ϲ�root��CCOMP.
     * @param tdls �﷨������
     * @param target Ŀ��Ԫ��
     * @param locate ��λԪ��
     */
    private void combineCCOMP(Collection<TypedDependency> tdls, IndexedWord target, IndexedWord locate)
    {
        if (target == null) return;
        for (TypedDependency td : tdls)
        {
            // �����ϵ�ĳ����ڵ㣬�����ϵ���Լ������ڵ�
            IndexedWord gov = td.gov();
            GrammaticalRelation reln = td.reln();
            String shortName = reln.getShortName();
            IndexedWord dep = td.dep();
            if (gov.equals(locate))
            {
                switch (shortName)
                {
                case "ccomp":
                        target.setValue(target.value()+dep.value().toString());
                        return;
                }
            }
        }
        //��δ�ҵ���Ӧ���ԵĴ������ΪʧЧ��Ԫ��
    	this.invalidate = true;
    }
    /**
     * �ݹ�Ѱ�����δʲ��滻target.
     * @param tdls �﷨������
     * @param target Ŀ��Ԫ��
     * @param locate ��λԪ��
     */
    private void replaceTotraverseNN(Collection<TypedDependency> tdls, IndexedWord target,IndexedWord locate)
    {
    	if(target==null||locate==null)return;
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
                    case "assmod":
                    case "nummod":
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
                return td.dep().makeCopy(0);
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
    private static Collection<TypedDependency> seg(CoreMap sentence)
    {
    	Collection<TypedDependency> terms2 = new ArrayList<TypedDependency>();
        //�ִ�
        //LOG.info("���ڶԶ̾���зִʣ�" + sentence);
        //MainPartSegment seg = new MainPartSegment();
        //List<CoreLabel> terms = new ArrayList<CoreLabel>();
        SemanticGraph graph2 = sentence.get(BasicDependenciesAnnotation.class);
        terms2 = graph2.typedDependencies();
        //System.out.println(terms2);
        return terms2;
    }

    private static List<Word> seg2(CoreMap sentence)
    {
        //�ִ�
        //LOG.info("���ڶԶ̾���зִʣ�" + sentence);
        List<Word> wordList = new LinkedList<>();
        //MainPartSegment seg = new MainPartSegment();
        //List<CoreLabel> terms = new ArrayList<CoreLabel>();
        List<CoreLabel> terms = sentence.get(TokensAnnotation.class);
        for (CoreLabel term : terms)
        {
            Word word = new Word(term.word());
            wordList.add(word);
        }
        //LOG.info("�ִʽ��Ϊ��" + sbLogInfo);
        return wordList;
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
				BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(list[i].getAbsolutePath()),"GBK"));
				//StringBuilder content = new StringBuilder();
				System.out.println(list[i].getAbsolutePath());
				String line ="";
				String line2 ="";
				while(line!=null){
					line = bf.readLine();
					if(line==null){
						break;
					}
					line2 = line.replaceAll("\n\r","");
					if(line.length()==2){
					continue;//ʶ���з�
					}
					if(line.trim().isEmpty()){
						continue;
					}
					strings2.add(line2);
					
				}
				System.out.println(strings2);
				bf.close();
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
		List<CoreMap> sentences = new ArrayList<CoreMap>();
		List<CoreMap> sentences2 = new ArrayList<CoreMap>();
		List<List<String>> strings = new ArrayList<List<String>>();
    	FileRead(strings,"D:\\����У��\\���Ͽ�\\test2");
    	StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
		String text2 ="̨����ͳ����Ӣ��";
		String text = "��Ӣ�ŵ���̨����ͳ����һֱ��ϲ���㣬̨����ͳ����Ӣ��";
		for(int i=0;i<strings.size();i++){
			for(int k=0;k<strings.get(i).size();k++){
			Annotation document = new Annotation(strings.get(i).get(k));
			pipeline.annotate(document);
			sentences2 = document.get(SentencesAnnotation.class);
			sentences.addAll(sentences2);
			//StanfordCoreNLP.clearAnnotatorPool();
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
			MainPartExtractor6 me = new MainPartExtractor6();
			Collection<TypedDependency> terms = new ArrayList<TypedDependency>();
			SemanticGraph graph = sentence.get(BasicDependenciesAnnotation.class);
			terms = graph.typedDependencies();
            mp = me.getMainPart(terms);
        	mp.sentence.setValue(sentence.toString());
        	result2.add(mp.sentence);
            result2.addAll(mp.r2);
			terms = graph.typedDependencies();
            mp = me.getSecondPart(terms);
            result2.addAll(mp.r2);
            
			terms = graph.typedDependencies();
            mp = me.getThirdPart(terms);
            result2.addAll(mp.r2);
            
			terms = graph.typedDependencies();
            mp = me.getFourthPart(terms);
            result2.addAll(mp.r2);
            
			terms = graph.typedDependencies();
            mp = me.getFifthPart(terms);
            result2.addAll(mp.r2);
            
			terms = graph.typedDependencies();
            mp = me.getSixthPart(terms);
            result2.addAll(mp.r2);
                     
			terms = graph.typedDependencies();	
            mp = me.getSeventhPart(terms);
            result2.addAll(mp.r2);
        	result.add(result2);
        	System.out.println(sentence);
		}
		  //����excel���֣��������
	      XSSFWorkbook workbook1 = new XSSFWorkbook();//����excel
	      XSSFWorkbook workbook2 = new XSSFWorkbook();
	      XSSFSheet sheet1 = workbook1.createSheet();//��ȡ������
	      XSSFSheet sheet2 = workbook2.createSheet();
	      for(int i=0;i<result.size();i++){
	    	  Row r;
    		  r = sheet1.createRow(i);
	    	  for(int j=0;j<result.get(i).size();j++){
	    		  Cell c;
	    		  c = r.createCell(j);
	    		  c.setCellValue(result.get(i).get(j).value());
	    	  }
	    	  Cell c2;
	    	  r = sheet2.createRow(i);
	    	  c2 = r.createCell(0);
	    	  c2.setCellValue(result.get(i).get(0).value());
	    	  
	      }
	      FileOutputStream excel1file = new FileOutputStream(
	      new File("D:\\����У��\\���Ͽ�\\����΢��������������ì��΢������\\test3-1.xlsx"));	//����excel�ļ� //�ֶ��ļ�
	      FileOutputStream excel2file = new FileOutputStream(
	      new File("D:\\����У��\\���Ͽ�\\����΢��������������ì��΢������\\test3-2.xlsx"));	//ֻ�о��ӵ�excel�ļ�2
	      workbook1.write(excel1file);
	      workbook2.write(excel2file);
	      workbook1.close();
	      workbook2.close();
	    //����ȡ�Ĵ������owl���򹹽�
    	Date d2 = new Date();
    	long t1 = d1.getTime();
    	long t2 = d2.getTime(); 	 
    	long millis = (t2 - t1)/1000; 	
    	System.out.println(millis);
    }

}
