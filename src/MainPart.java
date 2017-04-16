
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.IndexedWord;

/**
 * 对主谓宾结果的封装
 * @author bazi
 */
public class MainPart
{
    /**
     * 主语
     */
    public IndexedWord subject;
    public String subjectstr;
    /**
     * 谓语
     */
    public IndexedWord predicate;
    public String predicatestr;
    /**
     * 宾语
     */
    public IndexedWord object;
    public String objectstr;
    /**
     * 结果
     */

    
	public List<List<IndexedWord>> r = new ArrayList<List<IndexedWord>>();
	public List<IndexedWord> r2 = new ArrayList<IndexedWord>();
	public IndexedWord sentence = new IndexedWord();
    public String result;

    public MainPart(IndexedWord subject, IndexedWord predicate, IndexedWord object)
    {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public MainPart(IndexedWord rootNode)
    {
        this.predicate = rootNode;
    }

    public MainPart()
    {
        result = "";
    }

    /**
     * 结果填充完成
     */
    public void fill(){
    	subjectstr = subject.value();
    	predicatestr = predicate.value();
    	objectstr = object.value();
    }
    public void done()
    {
        result = predicate.toString();
        if (subject != null)
        {
            result = subject.toString() + result;
        }
        if (object != null)
        {
            result = result  + object.toString();
        }
    }

    public boolean isDone()
    {
        return result != null;
    }
    
    public boolean invalid()
    {
    	return this.subject==null||this.object==null||this.predicate==null;
    }

    @Override
    public String toString()
    {
        //if (result != null) return result;
        return "MainPart{" +
                " word1= ' " + subject + '\'' +
                ", word2= ' " + predicate + '\'' +
                ",  word3= ' " + object + '\'' +
                '}';
    }
}