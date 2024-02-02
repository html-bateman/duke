
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*;
import java.util.Random;

public class MarkovRunner {
    public void runMarkovZero() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovZero markov = new MarkovZero();
        markov.setTraining(st);
        markov.setRandom(88);
        for(int k=0; k < 3; k++){
            String text = markov.getRandomText(500);
            printOut(text);
        }
    }
    
    public void runMarkovOne() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovOne markov = new MarkovOne();
        markov.setTraining(st);
        markov.setRandom(273);
        for(int k=0; k < 3; k++){
            String text = markov.getRandomText(500);
            printOut(text);
        }
    }

    public void runMarkovFour() {
        FileResource fr = new FileResource(); // Replace with your file resource or reading mechanism
        String text = fr.asString();
        text = text.replace('\n', ' '); // Replace newline characters with spaces if needed

        MarkovFour markov = new MarkovFour();
        markov.setTraining(text);
        markov.setRandom(37);

        int numChars = 500; // Adjust the desired length of generated text
        String generatedText = markov.getRandomText(numChars);

        System.out.println(generatedText);
    }


    public void runMarkovModel() {
        FileResource fr = new FileResource(); // Replace with your file resource or reading mechanism
        String text = fr.asString();
        text = text.replace('\n', ' '); // Replace newline characters with spaces if needed

        int N = 6; // Set the value of N
        MarkovModel markov = new MarkovModel(N);
        markov.setTraining(text);
        markov.setRandom(38);

        int numChars = 500; // Adjust the desired length of generated text
        String generatedText = markov.getRandomText(numChars);

        System.out.println(generatedText);
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
    
}
