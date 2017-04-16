
    import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


	public class NLP2 {


        public static void main(String[] args) {

            // �����Զ����Properties�ļ�
            StanfordCoreNLP pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");

            // ��һЩ�ı�����ʼ��һ��ע�͡��ı��ǹ��캯���Ĳ�����
            Annotation annotation;
            annotation = new Annotation("̨�������ɽ��ѧ����Ӣ�ŵ���̨����ͳ����Ӣ����̨����ͳ");

            // ��������ѡ���Ĵ����ڱ���
            pipeline.annotate(annotation);

            // ��ע���л�ȡCoreMap List����ȡ��0��ֵ
            List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
            CoreMap sentence = sentences.get(0);

            // ��CoreMap��ȡ��CoreLabel List����һ��ӡ����
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
            System.out.println("��/��" + "\t " + "����" + "\t " + "ʵ����");
            System.out.println("-----------------------------");
            for (CoreLabel token : tokens) {
                String word = token.getString(TextAnnotation.class);
                String pos = token.getString(PartOfSpeechAnnotation.class);
                String ner = token.getString(NamedEntityTagAnnotation.class);
                System.out.println(word + "\t " + pos + "\t " + ner);
            }


    }
}
