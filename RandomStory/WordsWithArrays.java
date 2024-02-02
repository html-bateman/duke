import java.util.*;
import edu.duke.*;
/**
 * 在这里给出对类 WordsWithArray 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class WordsWithArrays {
    StorageResource myWords;
    
    public WordsWithArrays(){
        myWords = new StorageResource();
    }
    
    public void readWords(){
        myWords.clear();
        FileResource resource = new FileResource();
        for(String word:resource.words()){
            myWords.add(word.toLowerCase());
        }
    }
    
    public boolean contains(String[] list,String word,int number){
        //out of bound error for list.length
        //for(int k=0;k<list.length;k++){
        for(int k=0;k<number;k++){
            if(list[k].equals(word)){
                return true;
            }
        }
        return false;
    }
    
    public int numberOfUniqueWords(){
        int numStored = 0;
        String[] words = new String[myWords.size()];
        for(String s:myWords.data()){
            if(!contains(words,s,numStored)){
                words[numStored] = s;
                numStored++;
            }
        }
        return numStored;
    }
    
    public void tester(){
        readWords();
        System.out.println("number of words read:"+myWords.size());
        int unique = numberOfUniqueWords();
        System.out.println("array count"+unique);
    }
    
}
