import edu.duke.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

/**
 * 在这里给出对类 CodonCount 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class CodonCount {
    private HashMap<String,Integer> codonMap;
    public CodonCount(){
        codonMap = new HashMap<>();
    }
    void buildCodonMap(int start,String dna){
        codonMap.clear();
        if(start<0||start>2){
            System.out.println("Invalid start position");
            return;
        }
        for(int i=start;i+3<=dna.length();i+=3){
            String codon=dna.substring(i,i+3);
            codonMap.put(codon,codonMap.getOrDefault(codon,0)+1);
        }
    }
    
    String getMostCommonCodon(){
        int maxCount = 0;
        String mostCommonCodon = null;
        Set<Map.Entry<String,Integer>> entrySet = codonMap.entrySet();
        for(Map.Entry<String,Integer> entry:entrySet){
            int currentCount = entry.getValue();
            if(currentCount>maxCount){
                maxCount = currentCount;
                mostCommonCodon = entry.getKey();
            }
        }
        return mostCommonCodon;
    }
    
    void printCodonCounts(int start,int end){
        System.out.println("Codon counts between "+start+" and "+end);
        Set<Map.Entry<String,Integer>> entrySet = codonMap.entrySet();
        for(Map.Entry<String,Integer> entry:entrySet){
            String codon = entry.getKey();
            int count = entry.getValue();     
            if(count>=start&&count<=end){
                System.out.println("Codon:"+codon+",Count: "+count);
            }
        }
    }
    
    void test(){
        FileResource file = new FileResource();
        String dnaStrand = file.asString();
        dnaStrand = dnaStrand.toUpperCase();
            // Test each reading frame
        for (int start = 0; start < 3; start++) {
            buildCodonMap(start, dnaStrand);
            System.out.println("Reading Frame: " + start);
            System.out.println("Total number of unique codons: " + codonMap.size());
            System.out.println("Most common codon: " +getMostCommonCodon());
            System.out.println("Codons and their occurrences between 1 and 4 (inclusive):");
            printCodonCounts(1, 4);
            System.out.println();        
        }
    }
    
}
