import edu.duke.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 在这里给出对类 WordFrequencies 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies(){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique(){
        myWords.clear();
        myFreqs.clear();
        FileResource resource = new FileResource();
        for(String s:resource.words()){
            s = s.toLowerCase();
            int index = myWords.indexOf(s);
            if(!s.equals("")){
                if(index==-1){
                    myWords.add(s);
                    myFreqs.add(1);
                }else{
                    int value = myFreqs.get(index);
                    myFreqs.set(index,value+1);
                }
            }
        }
    }
    
    public HashMap<String,Integer> findUniqueHashMap(){
        FileResource resource = new FileResource();
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        for(String w:resource.words()){
            w=w.toLowerCase();
            if(!map.containsKey(w)){
                map.put(w,1);
            }else{
                map.put(w,map.get(w)+1);
            }
        }
        return map;
    }
    
    public void tester(){
        findUnique();
        System.out.println("#unique words:"+myWords.size());
        for(int k=0;k<myWords.size();k++){
           // System.out.println(myWords.get(k)+"\t"+myFreqs.get(k));
        }
        System.out.println("the most frequent word is:"+
                                myWords.get(findIndexOfMax())+"\t"+
                                myFreqs.get(findIndexOfMax()));
    }
    
    public void testHashMap(){
        HashMap<String,Integer> myMap = findUniqueHashMap();
        for(String s:myMap.keySet()){
            System.out.println(myMap.get(s)+"\t"+s);
        }
    }
    
    int findIndexOfMax(){
        int indexOfMax=0;
        for(int k=0;k<myFreqs.size();k++){
            if(myFreqs.get(k)>myFreqs.get(indexOfMax)){
                indexOfMax = k;
            }
        }
        return indexOfMax;
    }
    
}
