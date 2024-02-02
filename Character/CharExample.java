import java.io.*;
import java.util.Random;
import edu.duke.*;
import java.util.Arrays;
/**
 * 在这里给出对类 CharExample 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class CharExample {
    void digitTest(){
        String test="ABCabc0123456789!@#$";
        for(int k=0;k<test.length();k++){
            char ch = test.charAt(k);
            if(Character.isDigit(ch)){
                System.out.println(ch+" is a digit");
            }
            if(Character.isAlphabetic(ch)){
                System.out.println(ch+"is a alphabetic");
            }
            if(ch=='#'){
                System.out.println(ch+"#hashtag");
            }
            char uch=Character.toUpperCase(ch);
            char lch=Character.toLowerCase(ch);
        }
    }
    
    String encrypt(String input,int key){
        StringBuilder encrypted = new StringBuilder(input);
        String alphabetU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetL = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphabetU = alphabetU.substring(key)+alphabetU.substring(0,key);
        String shiftedAlphabetL = alphabetL.substring(key)+alphabetL.substring(0,key);
        for(int i=0;i<encrypted.length();i++){
            char currChar = encrypted.charAt(i);
            int idxU = alphabetU.indexOf(currChar);
            int idxL = alphabetL.indexOf(currChar);
            if(idxU!=-1){
                char newChar = shiftedAlphabetU.charAt(idxU);
                encrypted.setCharAt(i,newChar);
            }
            if(idxL!=-1){
                char newChar = shiftedAlphabetL.charAt(idxL);
                encrypted.setCharAt(i,newChar);
            }
        }
        return encrypted.toString();
    }
    
    String encryptTwoKeys(String input,int key1, int key2){
        StringBuilder encrypted = new StringBuilder(input);
        String alphabetU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetL = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphabetU;
        String shiftedAlphabetL;
        for(int i=0;i<encrypted.length();i++){
            if((i+1)%2==0){
                shiftedAlphabetU = alphabetU.substring(key2)+alphabetU.substring(0,key2);
                shiftedAlphabetL = alphabetL.substring(key2)+alphabetL.substring(0,key2);
            }else{
                shiftedAlphabetU = alphabetU.substring(key1)+alphabetU.substring(0,key1);
                shiftedAlphabetL = alphabetL.substring(key1)+alphabetL.substring(0,key1);
            }
            char currChar = encrypted.charAt(i);
            int idxU = alphabetU.indexOf(currChar);
            int idxL = alphabetL.indexOf(currChar);
            if(idxU!=-1){
                char newChar = shiftedAlphabetU.charAt(idxU);
                encrypted.setCharAt(i,newChar);
            }
            if(idxL!=-1){
                char newChar = shiftedAlphabetL.charAt(idxL);
                encrypted.setCharAt(i,newChar);
            }
        }
        return encrypted.toString();
    }


    void testencryptTwoKeys(){
        String s = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        System.out.println(encryptTwoKeys(s, 8, 21));
    }
    
    void testCaesar(){
        int key=15;
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encrypt(message,key);
        System.out.println(encrypted);
        //String decrypted = encrypt(encrypted,26-key);
        //System.out.println(decrypted);
    }
    
    String halfOfString(String message,int start){
        String halfOfString1="";
        String halfOfString0="";
        for(int i=0;i<message.length();i++){
            char currChar = message.charAt(i);
            if((i+1)%2==0){
                halfOfString1=halfOfString1+currChar;
            }else{
                halfOfString0=halfOfString0+currChar;
            }
        }
        if(start==1){
            return halfOfString1;
        }
        return halfOfString0;
    }
    
    
    void testhalfOfString(){
        System.out.println(halfOfString("Qbkm Zgis", 0));
        System.out.println(halfOfString("Qbkm Zgis", 1));
    }
    
    int getKey(String s){
        int[] freqs = counterLetters(s);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex-4;
        if(maxDex<4){
            dkey=26-(4-maxDex);
        }
            System.out.println("String: " + s);
    System.out.println("Frequencies: " + Arrays.toString(freqs));
    System.out.println("Max Index: " + maxDex);
    System.out.println("Calculated dkey: " + dkey);
        return dkey;
    
    }
    

    String encryptTwoKeysV7(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder(input);
        String alphabetU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetL = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphabetU1 = alphabetU.substring(key1) + alphabetU.substring(0, key1);
        String shiftedAlphabetL1 = alphabetL.substring(key1) + alphabetL.substring(0, key1);
        String shiftedAlphabetU2 = alphabetU.substring(key2) + alphabetU.substring(0, key2);
        String shiftedAlphabetL2 = alphabetL.substring(key2) + alphabetL.substring(0, key2);
    
        int halfLength = encrypted.length() / 2;
    
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idxU = alphabetU.indexOf(currChar);
            int idxL = alphabetL.indexOf(currChar);
    
            char newChar;
            if (idxU != -1) {
                newChar = (i >= halfLength) ? shiftedAlphabetU2.charAt(idxU) : shiftedAlphabetU1.charAt(idxU);
            } else if (idxL != -1) {
                newChar = (i >= halfLength) ? shiftedAlphabetL2.charAt(idxL) : shiftedAlphabetL1.charAt(idxL);
            } else {
                newChar = currChar;  // Keep non-alphabetic characters unchanged
            }
    
            encrypted.setCharAt(i, newChar);
        }
    
        return encrypted.toString();
    }

 
    void decryptTwoKeys(String encrypted){
        int key1=getKey(halfOfString(encrypted,0));
        int key2=getKey(halfOfString(encrypted,1));
        System.out.println("key1 is "+key1);
        System.out.println("key2 is "+key2);
        String decrypted = encryptTwoKeysV7(encrypted,26-key1,26-key2);
        System.out.println("the decrypted message is "+decrypted);
    }
    
    void testdecryptTwoKeys(){
        String s = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        String encrypted=encryptTwoKeys(s, 8, 21);
        System.out.println(encrypted);
        FileResource file = new FileResource();
        String message = file.asString();
        decryptTwoKeys(message);
    }
    
    void DukeEncrypted(){
        String encrypted = "Hfs cpwewloj loks cd Hoto kyg Cyy.";
        String decrypted = encryptTwoKeys(encrypted,26-14,26-24);
        System.out.println("the decrypted message is "+decrypted);
        
        String message = "Akag tjw Xibhr awoa aoee xakex znxag xwko";
        decryptTwoKeys(message);
    }
    
    void eyeballDecrypt(String encrypted){
        for(int k=0;k<26;k++){
            String decrypted = encrypt(encrypted,26-k);
            System.out.println(k+"\t"+decrypted);
        }
    }
    
    String decrypt(String encrypted){
        int[] freqs = counterLetters(encrypted);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex-4;
        if(maxDex<4){
            dkey=26-(4-maxDex);
        }
        return encrypt(encrypted,26-dkey);
    }
    
    int[] counterLetters(String message){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int k=0;k<message.length();k++){
            char ch=Character.toLowerCase(message.charAt(k));
            int dex=alph.indexOf(ch);
            if(dex!=-1){
                counts[dex]+=1;
            }
        }
        return counts;
    }
    
    int maxIndex(int[] vals){
        int maxDex=0;
        for(int k=0;k<vals.length;k++){
            if(vals[k]>vals[maxDex]){
                maxDex=k;
            }
        } 
        return maxDex;
    }
    
    boolean isVowel(char ch){
        ch = Character.toLowerCase(ch);
        if(ch=='a'||ch=='e'||ch=='i'||ch=='o'||ch=='u'){
            return true; 
        }
        return false;
    }
    
    String replace(String phrase,char ch){
        StringBuilder phrased = new StringBuilder(phrase);
        for(int i=0;i<phrase.length();i++){
            if(isVowel(phrased.charAt(i))==true){
                phrased.setCharAt(i,ch);
            }
        }
        return phrased.toString();
    }
    
    String emphasize(String phrase,char ch){
        StringBuilder phrased = new StringBuilder(phrase);
        for(int i=0;i<phrase.length();i++){
            if(phrased.charAt(i)==ch){
                if((i+1)%2==0){
                    phrased.setCharAt(i,'*');
                }else{
                    phrased.setCharAt(i,'+');
                }
            }
        }
        return phrased.toString();
    }   
    
    void textFingerPrint(String s){
        String alpha="abcdefghijklmnopqrstuvwxyz";
        int[] counters = new int[26];
        for(int k=0;k<s.length();k++){
            char ch=s.charAt(k);
            int index=alpha.indexOf(Character.toLowerCase(ch));
            if(index!=-1){
                //counters[index] stores the occurences of each alphabet
                counters[index]+=1;
            }
        }
        for(int k=0;k<counters.length;k++){
            System.out.println(alpha.charAt(k)+"\t"+counters[k]);
        }
    }
    
    void simpleSimulate(int rolls){
        Random rand = new Random();
        int twos = 0;
        int twelves = 0;
        for(int k=0;k<rolls;k++){
            int d1 = rand.nextInt(6)+1;
            int d2 = rand.nextInt(6)+1;
            if(d1+d2==2){
                twos+=1;
            }else if(d1+d2==12){
                twelves+=1;
            }
        }
        System.out.println("2's=\t"+twos+"\t"+ 100.0*twos/rolls);
        System.out.println("12's=\t"+twelves+"\t"+ 100.0*twelves/rolls);        
    }
    
    void simulate(int rolls){
        Random rand = new Random();
        int[] counts = new int[13];
        for(int k=0;k<rolls;k++){
            int d1 = rand.nextInt(6)+1;
            int d2 = rand.nextInt(6)+1;
            System.out.println("roll is "+d1+"+"+d2+"="+(d1+d2));
            counts[d1+d2]+=1;
        }
        for(int k=2;k<=12;k++){
            System.out.println(k+"'s=\t"+counts[k]+"\t"+100.0*k/rolls);
        }  
    }
    
    String[] getCommon(){
        FileResource resource = new FileResource("data/commmon.txt");
        String[] common = new String[20];
        int index=0;
        for(String s:resource.words()){
            common[index]=s;
            index+=1;
        }
        return common;
    }
    
    void countWords(FileResource resource,String[] common,int[] counts){
        for(String word:resource.words()){
            word=word.toLowerCase();
            int index=indexOf(common,word);
            if(index!=-1){
                counts[index]+=1;
            }
        }
    }
    
    int indexOf(String[] list,String word){
        for(int k=0;k<list.length;k++){
            if(list[k].equals(word)){
                return k;
            }
        }
        return -1;
    }
    
    void countShakespeare(){
        String[] plays = {"caesar.txt","errors.txt","hamlet.txt",
                        "likeit.txt","macbeth.txt","romeo.txt"};
        String[] common = getCommon();
        int[] counts=new int[common.length];
        for(int k=0;k<plays.length;k++){
            FileResource resource = new FileResource("data/"+plays[k]);
            countWords(resource,common,counts);
            System.out.println("done with "+plays[k]);
        }
        for(int k=0;k<common.length;k++){
            System.out.println(common[k]+"\t"+counts[k]);
        }
    }
    
    void countWordLengths(FileResource resource,int[] counts){
        for(String word:resource.words()){
            System.out.println(word+"\n");
            int wordLength=word.length();
            //System.out.println(wordLength);
            if(word.length()!=0){
                if(word.charAt(0)==','||word.charAt(0)=='"'){
                    wordLength-=1;
                }
                if(!Character.isLetter(word.charAt(word.length()-1))){
                    //System.out.println(word.charAt(word.length()-1));
                    wordLength-=1;
                    
                }
                counts[wordLength]+=1;   
            }
            System.out.println(wordLength+"\n");
        }
    }
    
    void testcountWordLengths(){
        FileResource file = new FileResource();
        int[] counts = new int[31];
        countWordLengths(file,counts);
        for(int k=2;k<counts.length;k++){
            System.out.print(counts[k]+" words of length "+k+"\n");
        }
        System.out.print("most common word length is"+maxIndex(counts));
    }
    
    
    
    
    
    
    
    
    
}
