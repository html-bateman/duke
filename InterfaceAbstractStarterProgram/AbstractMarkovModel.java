
/**
 * Abstract class AbstractMarkovModel - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */

import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected String myText;
    protected Random myRandom;
    
    public AbstractMarkovModel() {
        myRandom = new Random();
    }
    
    
    //abstract public String getRandomText(int numChars);
    
    protected ArrayList<String> getFollows(String key) {
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
}
