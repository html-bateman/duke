
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*; 
import java.util.Random;

public class MarkovRunnerWithInterface {
    public void runModel(IMarkovModel markov, String text, int size,int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }
    }
    
    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 200;
        int seed = 38;
        
        MarkovZero mz = new MarkovZero();
        runModel(mz, st, size,seed);
    
        MarkovOne mOne = new MarkovOne();
        runModel(mOne, st, size,seed);
        
        MarkovModel mThree = new MarkovModel(3);
        runModel(mThree, st, size,seed);
        
        MarkovFour mFour = new MarkovFour();
        runModel(mFour, st, size,seed);

    }
    
    public void compareMethods() {
        int order = 2;
        int seed = 42;
        int textLength = 1000;
        String trainingTextFile = "data/hawthorne.txt";

        // Run MarkovModel
        long startTimeMarkovModel = System.nanoTime();
        runModel(new MarkovModel(order), trainingTextFile,textLength,seed);
        long endTimeMarkovModel = System.nanoTime();
        long durationMarkovModel = endTimeMarkovModel - startTimeMarkovModel;

        // Run EfficientMarkovModel
        long startTimeEfficientMarkovModel = System.nanoTime();
        runModel(new EfficientMarkovModel(order),trainingTextFile,textLength,seed);
        long endTimeEfficientMarkovModel = System.nanoTime();
        long durationEfficientMarkovModel = endTimeEfficientMarkovModel - startTimeEfficientMarkovModel;

        System.out.println("MarkovModel Duration: " + durationMarkovModel / 1_000_000 + " milliseconds");
        System.out.println("EfficientMarkovModel Duration: " + durationEfficientMarkovModel / 1_000_000 + " milliseconds");
    }

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println();
                psize = 0;
            }
        }
        System.out.println("\n----------------------------------");
    }
    
    public void testHashMap() {
        int order = 2;
        int seed = 42;
        String trainingText = "yes-this-is-a-thin-pretty-pink-thistle";
        int size = 50;

        EfficientMarkovModel emm = new EfficientMarkovModel(order);
        emm.setRandom(seed);
        emm.setTraining(trainingText);

        System.out.println("Generating text with EfficientMarkovModel:");
        String generatedText = emm.getRandomText(size);
        System.out.println(generatedText);

        emm.printHashMapInfo();
    }
    
    public void testRunModelWithEfficientModel(){
        int order =5;
        int seed = 615;
        int size = 200;
        FileResource fr = new FileResource("data/romeo.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');
        EfficientMarkovModel em = new EfficientMarkovModel(order);
        runModel(em,st,size,seed); 
        em.printHashMapInfo();

    }
    
    
    
}
