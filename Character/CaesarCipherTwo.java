public class CaesarCipherTwo {
    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainKey1;
    private int mainKey2;

    public CaesarCipherTwo(int key1, int key2) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet1 = alphabet.substring(key1) + alphabet.substring(0, key1);
        shiftedAlphabet2 = alphabet.substring(key2) + alphabet.substring(0, key2);
        mainKey1 = key1;
        mainKey2 = key2;
    }

    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder(input);

        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx;
            if (Character.isUpperCase(currChar)) {
                idx = alphabet.indexOf(currChar);
                if (idx != -1) {
                    char newChar = (i % 2 == 0) ? shiftedAlphabet1.charAt(idx) : shiftedAlphabet2.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
            } else if (Character.isLowerCase(currChar)) {
                idx = alphabet.toLowerCase().indexOf(currChar);
                if (idx != -1) {
                    char newChar = (i % 2 == 0) ? shiftedAlphabet1.toLowerCase().charAt(idx) : shiftedAlphabet2.toLowerCase().charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
            }
        }

        return encrypted.toString();
    }

    public String decrypt(String input) {
        CaesarCipherTwo decryptCipher = new CaesarCipherTwo(26 - mainKey1, 26 - mainKey2);
        return decryptCipher.encrypt(input);
    }
}
