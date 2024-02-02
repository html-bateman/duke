public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;

    public CaesarCipher(int key) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
        mainKey = key;
    }

    public String encrypt(String input) {
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            int idx;
            if (Character.isUpperCase(c)) {
                idx = alphabet.indexOf(c);
                if (idx != -1) {
                    c = shiftedAlphabet.charAt(idx);
                    sb.setCharAt(i, c);
                }
            } else if (Character.isLowerCase(c)) {
                idx = alphabet.toLowerCase().indexOf(c);
                if (idx != -1) {
                    c = Character.toLowerCase(shiftedAlphabet.charAt(idx));
                    sb.setCharAt(i, c);
                }
            }
        }
        return sb.toString();
    }

    public String decrypt(String input) {
        CaesarCipher cc = new CaesarCipher(26 - mainKey);
        return cc.encrypt(input);
    }

    public static void main(String[] args) {
        CaesarCipher cipher = new CaesarCipher(3);
        String message = "Hello World! This is a Test.";
        String encrypted = cipher.encrypt(message);
        String decrypted = cipher.decrypt(encrypted);

        System.out.println("Original: " + message);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
