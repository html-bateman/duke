import java.util.ArrayList;
import java.util.Random;

public class MarkovModel extends AbstractMarkovModel{
    private int N; // Number of characters to use for prediction

    public MarkovModel(int n) {
        myRandom = new Random();
        N = n;
    }
    @Override
    public void setTraining(String s) {
        myText = s.trim();
    }
    @Override
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    @Override
    public String getRandomText(int numChars){
        if (myText == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-N);
        String key = myText.substring(index, index+N);
        sb.append(key);
        
        for(int k=0; k < numChars-N; k++){
            ArrayList<String> followSet = getFollows(key);
            if (followSet.size() == 0)
                break;
            index = myRandom.nextInt(followSet.size());
            String next = followSet.get(index);
            sb.append(next);
            key = key.substring(1) + next;
        }
        return sb.toString();
    }
    
    @Override
    public String toString(){
        return "MarkovModel of order "+N;
    }
}
