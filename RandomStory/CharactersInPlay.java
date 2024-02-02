import edu.duke.*;
import java.util.Random;
import java.util.ArrayList;
/**
 * 在这里给出对类 CharactersInPlay 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class CharactersInPlay {
    private ArrayList<String> names;
    private ArrayList<Integer> freqs;
    public CharactersInPlay(){
        names = new ArrayList<String>();
        freqs = new ArrayList<Integer>();
    }
    
    void update(String person){
        int index = names.indexOf(person);
        if(index==-1){
            names.add(person);
            freqs.add(1);
        }else{
            int freq = freqs.get(index);
            freqs.set(index,freq+1);
        }
    }
    
    void findAllCharacters(){
        names.clear();
        freqs.clear();
        FileResource resource = new FileResource();
        for(String line:resource.lines()){
            if(!line.equals("")){
                int indexOfDot = line.indexOf(".");
                if(indexOfDot!=-1){
                    String name = line.substring(0,indexOfDot);
                    update(name);
                }
            }
        }
    }
    
    void tester(){
        findAllCharacters();
        System.out.println("-----------------------");
        // for(int k=0;k<5;k++){
            // System.out.print(names.get(k)+"\t"+freqs.get(k)+"\n");
        // }
        charactersWithNumParts(10,15);
        System.out.println("most speaking parts:"+
                            names.get(findIndexOfMax())+"\t"+
                            freqs.get(findIndexOfMax()));
        int thirdMostIndex = findIndexOfNthMax(3);
        System.out.println("Third most speaking parts: " + names.get(thirdMostIndex) + "\t" + freqs.get(thirdMostIndex));
    }
    
    void charactersWithNumParts(int num1,int num2){
        int higestFreq = freqs.get(findIndexOfMax());
        int lowerIndex = num1;
        int upperIndex = num2;
        if(upperIndex<higestFreq){
            for(int k=lowerIndex;k<=upperIndex;k++){
                if(freqs.indexOf(k)!=-1)
                System.out.println(names.get(freqs.indexOf(k)));
            }
        }else{
            for(int k=lowerIndex;k<=higestFreq;k++){
                if(freqs.indexOf(k)!=-1)
                System.out.println(names.get(freqs.indexOf(k)));
            }
        }
    }
    
    int findIndexOfMax(){
        int indexOfMax=0;
        for(int k=0;k<freqs.size();k++){
            if(freqs.get(k)>freqs.get(indexOfMax)){
                indexOfMax = k;
            }
        }
        return indexOfMax;
    }
    
    
    int findIndexOfNthMax(int n) {
        int currentMax;
        int currentMaxIndex=0;

        for (int i = 1; i <= n; i++) {
            currentMax = Integer.MIN_VALUE;
            currentMaxIndex = -1;

            for (int k = 0; k < freqs.size(); k++) {
                if (freqs.get(k) > currentMax) {
                    currentMax = freqs.get(k);
                    currentMaxIndex = k;
                }
            }

            freqs.set(currentMaxIndex, Integer.MIN_VALUE); // Mark the current max as processed
        }
        return currentMaxIndex;
    }
}
