import java.util.ArrayList;
import edu.duke.*;
/**
 * 在这里给出对类 tester 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Tester {
    public  void testGetFollows(){
        MarkovOne one = new MarkovOne();
        one.setTraining("this is a test yes this is a test.");
        ArrayList<String> follows = one.getFollows("t");
        for(String item : follows){
            System.out.println(item);
        }
    }
    
    public  void testGetFollowsWithFile(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovOne markov = new MarkovOne();
        markov.setTraining(st);
        ArrayList<String> follows = markov.getFollows("th");
        for(String item : follows){
            System.out.println(item);
        }
        System.out.println(follows.size());
    }
}
