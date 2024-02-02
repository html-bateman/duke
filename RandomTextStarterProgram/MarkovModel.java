import java.util.ArrayList;
import java.util.Random;

public class MarkovModel {
    private String myText;
    private Random myRandom;
    private int N; // Number of characters to use for prediction

    public MarkovModel(int n) {
        myRandom = new Random();
        N = n;
    }

    public void setTraining(String s) {
        myText = s.trim();
    }

    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    public ArrayList<String> getFollows(String key) {
		ArrayList<String> followSet = new ArrayList<String>();
		int pos = 0;
		while (pos < myText.length()-key.length()) {
			int index = myText.indexOf(key, pos);
			if (index == -1)
				break;
			int nextIndex = index + key.length();
			if (nextIndex >= myText.length())
				break;
			followSet.add(myText.substring(nextIndex, nextIndex+1));
			pos = index + 1;
		}
		return followSet;
	}
	
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
}
