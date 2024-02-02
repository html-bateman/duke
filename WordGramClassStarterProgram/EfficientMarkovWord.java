import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> followsMap;

    public EfficientMarkovWord(int order) {
        myOrder = order; 
        myRandom = new Random();
        followsMap = new HashMap<>();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text) {
        myText = text.split("\\s+");
        buildMap();
    }

    private void buildMap() {
        followsMap.clear();
        for (int i = 0; i <= myText.length - myOrder; i++) {
            WordGram key = new WordGram(myText, i, myOrder);
            if (!followsMap.containsKey(key)) {
                followsMap.put(key, new ArrayList<>());
            }
            if (i + myOrder < myText.length) {
                followsMap.get(key).add(myText[i + myOrder]);
            }
        }
    }

    private ArrayList<String> getFollows(WordGram kGram) {
        return followsMap.get(kGram);
    }

    public void printHashMapInfo() {
        System.out.println("HashMap: " + followsMap);
        System.out.println("Number of keys: " + followsMap.size());

        int maxSize = 0;
        WordGram maxKey = null;

        for (WordGram key : followsMap.keySet()) {
            int size = followsMap.get(key).size();
            if (size > maxSize) {
                maxSize = size;
                maxKey = key;
            }
        }

        System.out.println("Size of the largest value: " + maxSize);

        if (maxKey != null) {
            System.out.println("Keys with the maximum size value: " + maxKey);
        }
    }

    public String getRandomText(int numWords) {
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);
        WordGram currentGram = new WordGram(myText, index, myOrder);
        sb.append(currentGram.toString());
        sb.append(" ");

        for (int k = 0; k < numWords - myOrder; k++) {
            ArrayList<String> follows = getFollows(currentGram);
            if (follows == null || follows.size() == 0)
                break;
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            currentGram = currentGram.shiftAdd(next);
        }

        return sb.toString().trim();
    }
}
