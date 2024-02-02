import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import edu.duke.*;
import java.util.HashSet;

/**
 * 在这里给出对类 WordsInFiles 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class WordsInFiles {
    private HashMap<String,ArrayList<String>> wordMap;
    public WordsInFiles(){
        wordMap = new HashMap<>();
    }
    private void addWordsFromFile(File f){
        FileResource file = new FileResource(f);
        for(String word:file.words()){
            if(!wordMap.containsKey(word)){
                ArrayList<String> fileList = new ArrayList<>();
                fileList.add(f.getName());
                wordMap.put(word,fileList);
            }else{
                ArrayList<String> fileList = wordMap.get(word);
                if(!fileList.contains(f.getName())){
                    fileList.add(f.getName());
                }
            }
        }   
    }
    
    private void buildWordFileMap(){
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    
    public int maxNumber(){
        int maxCount=0;
        for(ArrayList<String> fileList: wordMap.values()){
            int currentCount = fileList.size();
            if(currentCount>maxCount){
                maxCount=currentCount;
            }
        }
        return maxCount;
    }
    
    public ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> resultWords = new ArrayList<>();
        for(String word:wordMap.keySet()){
            ArrayList<String> fileList = wordMap.get(word);
            HashSet<String> uniqueFiles = new HashSet<>(fileList);
            if(uniqueFiles.size()==number){
                resultWords.add(word);
            }
        }
        return resultWords;
    }
    
    public void printFilesIn(String word){
        if(wordMap.containsKey(word)){
            ArrayList<String> fileList = wordMap.get(word);
            for(String fileName:fileList){
                System.out.println(fileName);
            }
        }else{
            System.out.println("Word not found in any files.");
        }
    }
    
    public void tester() {
        // Call buildWordFileMap to select a group of files and build a HashMap of words
        buildWordFileMap();

        // Determine all the words that are in the maximum number of files
        ArrayList<String> maxNumberWords = wordsInNumFiles(7);
        System.out.println("Words that occur in all files: " + maxNumberWords.size());
        // For each such word, print the filenames of the files it is in
        // for (String word : maxNumberWords) {
            // System.out.println("Word: " + word);
             printFilesIn("sea");
             System.out.println("----------------");
             printFilesIn("tree");
            // System.out.println();
        // }
        
        ArrayList<String> wordsInAllFiles = wordsInNumFiles(4);
        System.out.println("Words that occur in all files: " + wordsInAllFiles.size());
    }
    
    
    
    
    
    
}
