
/**
 * Write a description of class MarkovZero here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import java.util.Random;
import java.util.ArrayList;

public class MarkovOne {
    private String myText;
    private Random myRandom;
    
    public MarkovOne() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    public void setTraining(String s){
        myText = s.trim();
    }
    
    // public String getRandomText(int numChars){
        // if (myText == null){
            // return "";
        // }
        // StringBuilder sb = new StringBuilder();
        // int index = myRandom.nextInt(myText.length()-2);
        // String key = myText.substring(index,index+2);
        // sb.append(key);
        
        // for(int k=0;k<numChars-2;k++){
            // ArrayList<String> follows = getFollows(key);
            // if(follows.size()==0) break;
            // index = myRandom.nextInt(follows.size());
            // String next = follows.get(index);
            // sb.append(next);
            // key=key.substring(1)+next;
        // }
        
        // return sb.toString();
    // }
    
public String getRandomText(int numChars) {
    if (myText == null || myText.length() < 2) {
        return "";
    }
    StringBuilder sb = new StringBuilder();
    int index = myRandom.nextInt(myText.length() - 1);
    char currentChar = myText.charAt(index);
    sb.append(currentChar);

    for (int k = 0; k < numChars - 1; k++) {
        ArrayList<Character> follows = getFollows(currentChar);
        if (follows.isEmpty()) {
            break;
        }
        index = myRandom.nextInt(follows.size());
        char next = follows.get(index);
        sb.append(next);
        currentChar = next;
    }

    return sb.toString();
}

private ArrayList<Character> getFollows(char currentChar) {
    ArrayList<Character> follows = new ArrayList<>();
    for (int i = 0; i < myText.length() - 1; i++) {
        if (myText.charAt(i) == currentChar) {
            follows.add(myText.charAt(i + 1));
        }
    }
    return follows;
}

    
    public ArrayList<String> getFollows(String key){
        ArrayList follows = new ArrayList<>();
        int pos = 0;
        while(pos<myText.length()-key.length()){
            int start = myText.indexOf(key,pos);
            if(start==-1){
                break;
            }
            // if(start+key.length()>=myText.length()-1){
                // break;
            // }
            String next=myText.substring(start+key.length(),start+key.length()+1);
            follows.add(next);
            pos = start+key.length();
        }
        return follows;
    }
    
    
    
}
