import edu.duke.*;
import java.util.HashSet;
import java.util.HashMap;

/**
 * 在这里给出对类 VigenereBreaker 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class VigenereBreaker {
    
    public String sliceString(String message, int whichSlice, int totalSlices){
        StringBuilder slicedMessage = new StringBuilder();
        for(int i = whichSlice;i<message.length();i+=totalSlices){
            slicedMessage.append(message.charAt(i));
        }
        return slicedMessage.toString();
    }
    
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon){
        int[] key = new int[klength];
        for(int i=0;i<klength;i++){
            String slicedMessage = sliceString(encrypted,i,klength);
            CaesarCracker cracker = new CaesarCracker(mostCommon);
            key[i] = cracker.getKey(slicedMessage);
        }
        return key;
    }
    
    public void breakVigenere(){
        FileResource fileResource = new FileResource();
        String encryptedText = fileResource.asString();
        int keyLength = 4;
        char mostCommon = 'e';
        int[] key = tryKeyLength(encryptedText,keyLength,mostCommon);
        System.out.print("Key: {");
        for (int i = 0; i < key.length; i++) {
            System.out.print(key[i]);
            if (i < key.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
        // VigenereCipher vigenereCipher = new VigenereCipher(key);
        // String decryptedMessage = vigenereCipher.decrypt(encryptedText);
        // System.out.println(decryptedMessage);
    }
    
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<>();
        for(String word:fr.words()){
            dictionary.add(word.toLowerCase());
        }
        return dictionary;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        int validWordCount=0;
        String[] words = message.split("\\W+");
        for(String word:words){
            if(dictionary.contains(word.toLowerCase())){
                validWordCount++;
            }
        }
        return validWordCount;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character, Integer> charFrequency = new HashMap<>();

        // Count the frequency of each character in the dictionary words
        for (String word : dictionary) {
            for (char c : word.toCharArray()) {
                charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
            }
        }

        // Find the character with the highest frequency
        char mostCommonChar = ' ';
        int maxFrequency = 0;

        for (char c : charFrequency.keySet()) {
            int frequency = charFrequency.get(c);
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mostCommonChar = c;
            }
        }

        return mostCommonChar;
    }
    
    public void testtryKeyLength(){
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        FileResource dictionaryFile = new FileResource("dictionaries/English");
        HashSet<String> englishDictionary = readDictionary(dictionaryFile);
        int[] key = tryKeyLength(encrypted,38,mostCommonCharIn(englishDictionary));
        VigenereCipher vigenereCipher = new VigenereCipher(key);
        String decryptedMessage = vigenereCipher.decrypt(encrypted);
        int validWordCount = countWords(decryptedMessage,englishDictionary);
        System.out.println(validWordCount);  
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        int maxValidWordCount = 0;
        String bestDecryption = "";
        int bestKeyLength = 0;
        for(int keyLength=1;keyLength<=100;keyLength++){
            int[] key = tryKeyLength(encrypted,keyLength,mostCommonCharIn(dictionary));
            VigenereCipher vigenereCipher = new VigenereCipher(key);
            String decryptedMessage = vigenereCipher.decrypt(encrypted);
            int validWordCount = countWords(decryptedMessage,dictionary);
            
            if(validWordCount>maxValidWordCount){
                maxValidWordCount = validWordCount;
                bestDecryption = decryptedMessage;
                bestKeyLength = keyLength;
            }
        }
        System.out.println("Best key length found:"+bestKeyLength);
        System.out.println("Valid word count:"+maxValidWordCount);
        return bestDecryption;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        int maxValidWordCount = 0;
        String bestDecryption = "";
        String bestLanguage = "";

        // Iterate over each language in the provided HashMap
        for (String language : languages.keySet()) {
            HashSet<String> dictionary = languages.get(language);

            // Use the breakForLanguage method to decrypt the message for each language
            String decryptedMessage = breakForLanguage(encrypted, dictionary);

            // Count how many valid words are in the decrypted message
            int validWordCount = countWords(decryptedMessage, dictionary);

            // Update if the current decryption has more valid words
            if (validWordCount > maxValidWordCount) {
                maxValidWordCount = validWordCount;
                bestDecryption = decryptedMessage;
                bestLanguage = language;
            }
        }

        // Print out the decrypted message and the identified language
        System.out.println("Decrypted message:\n" + bestDecryption);
        System.out.println("Identified language: " + bestLanguage);
    }
    
    public void breakVigenereNoKey(){
        FileResource fr = new FileResource();
        String encryptedMessage = fr.asString();
        FileResource dictionaryFile = new FileResource("dictionaries/English");
        HashSet<String> englishDictionary = readDictionary(dictionaryFile);

        // Step 4: Use the breakForLanguage method to decrypt the message
        String decryptedMessage = breakForLanguage(encryptedMessage, englishDictionary);

        // Step 5: Print out the decrypted message
        System.out.println("Decrypted message:\n" + decryptedMessage);
    }
    
    public void breakVigenereMultiLang() {
        FileResource fr = new FileResource();

        // Step 2: Read the entire contents of the file into a String
        String encryptedMessage = fr.asString();

        // Step 3: Read multiple dictionaries and create a HashMap
        HashMap<String, HashSet<String>> languages = new HashMap<>();
        String[] languageNames = {"Danish", "Dutch", "English", "French", "German", "Italian", "Portuguese", "Spanish"};

        for (String languageName : languageNames) {
            FileResource dictionaryFile = new FileResource("dictionaries/" + languageName);
            HashSet<String> dictionary = readDictionary(dictionaryFile);
            languages.put(languageName, dictionary);
            System.out.println("Read dictionary for " + languageName);
        }

        // Step 4: Use breakForAllLangs to decrypt the message for each language
        breakForAllLangs(encryptedMessage, languages);
    }

}
