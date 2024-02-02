import edu.duke.*;
import java.util.Random;
import java.util.ArrayList;
/**
 * 在这里给出对类 CountWords 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class CountWords {
    StorageResource myWords;
    
    public CountWords(){
        myWords = new StorageResource();
    }
    
    public int getCount(){
        return myWords.size();
    }
    
    public void readWords(String source){
        myWords.clear();
        if(source.startsWith("http")){
            URLResource resource = new URLResource(source);
            for(String word:resource.words()){
                myWords.add(word.toLowerCase());
            }
        }else{
            FileResource resource = new FileResource(source);
            for(String word:resource.words()){
                //myWords.add(word.toLowerCase());
                word = word.toLowerCase();
                if(!myWords.contains(word)){
                    myWords.add(word);
                }
            }
        }
    }
    
    public String getRandomWord(){
        Random rand = new Random();
        int choice = rand.nextInt(myWords.size());
        for(String s:myWords.data()){
            if(choice==0){
                return s;
            }
            choice = choice -1;
        }
        return "";
    }
    
    public String getRandomWord(String[] words){
        Random rand = new Random();
        int index = rand.nextInt(words.length);
        return words[index];
    }
    
    public String getRandomWord(ArrayList<String> words){
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        return words.get(index);
    }
    
}
