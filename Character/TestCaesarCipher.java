import edu.duke.*;
/**
 * 在这里给出对类 TestCaesarCipher 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class TestCaesarCipher {
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
    ;
    void simpleTest(){
        //FileResource fr = new FileResource();
        //String message= fr.asString();
        String message="Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCipher cc = new CaesarCipher(15);
        System.out.println("encrypted message is "+cc.encrypt(message));
        //System.out.println("decrypted message is "+cc.decrypt(message));
        //System.out.println("automatically decryption:"+ breakCaesarCipher(message)); 
    }
    
    String breakCaesarCipher(String input){
        int[] freqs = counterLetters(input);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex-4;
        if(maxDex<4){
            dkey=26-(4-maxDex);
        }
        CaesarCipher cc = new CaesarCipher(dkey);
        return cc.decrypt(input);
    }
    
    
    
}
