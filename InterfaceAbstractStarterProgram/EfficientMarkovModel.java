import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EfficientMarkovModel extends AbstractMarkovModel {

    private int N;
    private HashMap<String, ArrayList<String>> followsMap;

    public EfficientMarkovModel(int n) {
        myRandom = new Random();
        N = n;
        followsMap = new HashMap<>();
    }

    @Override
    public void setTraining(String s) {
        myText = s.trim();
        buildMap();
    }
    
    @Override
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }

    @Override
    public String getRandomText(int numChars) {
        if (myText == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - N);
        String key = myText.substring(index, index + N);
        sb.append(key);

        for (int k = 0; k < numChars - N; k++) {
            ArrayList<String> follows = followsMap.get(key);
            if (follows == null || follows.isEmpty()) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            key = key.substring(1) + next;
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "EfficientMarkovModel of order " + N;
    }

    private void buildMap() {
        followsMap.clear();
        for (int i = 0; i <= myText.length() - N; i++) {
            String key = myText.substring(i, i + N);
            if (!followsMap.containsKey(key)) {
                followsMap.put(key, new ArrayList<>());
            }
            if (i + N < myText.length()) {
                String next = myText.substring(i + N, i + N + 1);
                followsMap.get(key).add(next);
            }
        }
    }

    @Override
    public ArrayList<String> getFollows(String key) {
        return followsMap.getOrDefault(key, new ArrayList<>());
    }
    
    
    public void printHashMapInfo() {
        System.out.println("HashMap Info:");

        // Print the HashMap (only if it is small)
        if (followsMap.size() <= 10) {
            System.out.println("HashMap: " + followsMap);
        } else {
            System.out.println("HashMap is too large to display.");
        }

        // Print the number of keys in the HashMap
        System.out.println("Number of Keys: " + followsMap.size());

        // Find the size of the largest value (ArrayList)
        int maxSize = 0;
        for (ArrayList<String> follows : followsMap.values()) {
            maxSize = Math.max(maxSize, follows.size());
        }
        System.out.println("Size of Largest ArrayList: " + maxSize);

        // Print the keys that have the maximum size value
        System.out.print("Keys with Maximum Size Value: ");
        for (String key : followsMap.keySet()) {
            if (followsMap.get(key).size() == maxSize) {
                System.out.print(key + " ");
            }
        }
        System.out.println();
    }
    
    
}
