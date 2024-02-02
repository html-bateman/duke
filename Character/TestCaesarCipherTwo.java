import edu.duke.FileResource;

public class TestCaesarCipherTwo {

    // Assume these methods are present as they were defined in the previous lesson
    // You might need to adjust them based on your actual implementation.
    private String halfOfString(String message, int start) {
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

    private int[] countLetters(String message) {
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

    private int maxIndex(int[] vals) {
        int maxDex=0;
        for(int k=0;k<vals.length;k++){
            if(vals[k]>vals[maxDex]){
                maxDex=k;
            }
        } 
        return maxDex;
    }

    public void simpleTests() {
        //FileResource fr = new FileResource();
        //String message = fr.asString();
        //String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        String message = "Hfs cpwewloj loks cd Hoto kyg Cyy.";
        // Create CaesarCipherTwo object with keys 17 and 3
        //CaesarCipherTwo cipher = new CaesarCipherTwo(21, 8);

        // Encrypt the message
        //String encryptedMessage = cipher.encrypt(message);
        //System.out.println("Encrypted message:\n" + encryptedMessage);

        // Decrypt the message using the decrypt method
        //String decryptedMessage = cipher.decrypt(message);
        //System.out.println("Decrypted message:\n" + decryptedMessage);

        // Break the CaesarCipherTwo and automatically decrypt
        breakCaesarCipher("Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!");
    }

    private void breakCaesarCipher(String input) {
        // Determine the keys used for encryption
        String half1 = halfOfString(input, 0);
        String half2 = halfOfString(input, 1);

        int[] freqs1 = countLetters(half1);
        int[] freqs2 = countLetters(half2);

        int maxIndex1 = maxIndex(freqs1);
        int maxIndex2 = maxIndex(freqs2);

        // Assume maxIndex1 and maxIndex2 are the keys used for encryption
        // Create CaesarCipherTwo object with the found keys and decrypt the message
        CaesarCipherTwo breakCipher = new CaesarCipherTwo(maxIndex1, maxIndex2);
        String decryptedMessage = breakCipher.decrypt(input);

        System.out.println("Decrypted message using automatic key detection:\n" + decryptedMessage);
    }

    public static void main(String[] args) {
        TestCaesarCipherTwo test = new TestCaesarCipherTwo();
        test.simpleTests();
    }
}
