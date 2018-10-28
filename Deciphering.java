import java.io.*;
import java.nio.charset.StandardCharsets;

public class Deciphering {
    public void Deciphering(){
        EncryptionLib eu = new EncryptionLib();
        // Arrays to be used for left - right nibble operations.
        int[] leftNibArr = new int[16];
        int[] rightNibArr = new int[16];
        int[] leftNib0;
        int[] leftNib1;
        int[] rightNib0;
        int[] rightNib1;

        // Arrays to be used for key generation.
        int[] colSelect2 = {3,1,0,2};
        int[] colSelect3 = {0,1,3,2};
        int[] key2arr = new int[16];
        int[] key3arr = new int[16];
        int[] key4arr;
        int[] key5arr;
        int[][] originalKey;

        // Char arrays for shuffling and finalizing.
        char[] reverse = new char[12];
        char[] initialCipher;
        char[] deCiphered;
        char[] shuffledPass;

        // Default initial permutation array.
        int[] initialPermArr = {7 , 2 , 1, 3 , 5 , 6 , 9 , 10 , 4 , 8 , 12 , 11};

        File file = new File("CipherTextFile");
        String line;

        try{
            // File IO operations
            PrintWriter wrt = new PrintWriter("Deciphered.txt" , StandardCharsets.UTF_8);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Generating necessary keys for algorithm.
            originalKey = eu.keyGenerator('t' , 'u');
            int selectorCount = 0;
            int count = 0;
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    key2arr[selectorCount] = originalKey[k][colSelect2[count]];
                    key3arr[selectorCount] = originalKey[k][colSelect3[count]];
                    selectorCount++;
                }
                count++;
            }
            key4arr = eu.leftShiftRotate(key2arr , 3);
            key5arr = eu.rightShiftRotate(key3arr , 5);

            while ((line = bufferedReader.readLine()) != null){
                initialCipher = line.toCharArray();
                for (int a = 0; a < 12; a=a+4){

                    // Reversing the last IP shuffle
                    shuffledPass = eu.shuffleIP(initialPermArr , initialCipher);

                    // Fetching characters as binary arrays.
                    leftNib0 = eu.decimal2binary((int)shuffledPass[a]);
                    rightNib0 = eu.decimal2binary((int)shuffledPass[a+1]);
                    leftNib1 = eu.decimal2binary((int)shuffledPass[a+2]);
                    rightNib1 = eu.decimal2binary((int)shuffledPass[a+3]);

                    // Merging nibbles to get 16bit arrays.
                    for (int h = 0; h < 8; h++){
                        leftNibArr[h] = leftNib0[h];
                        rightNibArr[h] = rightNib0[h];
                    }
                    for (int h = 8; h < 16; h++){
                        leftNibArr[h] = leftNib1[h-8];
                        rightNibArr[h] = rightNib1[h-8];
                    }

                    // Reversing last XORencryption
                    rightNibArr = eu.XORencryption(key4arr , rightNibArr);
                    leftNibArr = eu.XORencryption(key5arr, leftNibArr);

                    // Reversing other XORencryption
                    leftNibArr = eu.XORencryption(key2arr , leftNibArr);
                    rightNibArr = eu.XORencryption(key3arr , rightNibArr);

                    // Reversing shift-rotate operations.
                    leftNibArr = eu.leftShiftRotate(leftNibArr , 4);
                    rightNibArr = eu.leftShiftRotate(rightNibArr, 4);

                    rightNib0 = new int[8];
                    rightNib1 = new int[8];
                    leftNib0 = new int[8];
                    leftNib1 = new int[8];

                    // Dividing right - left nibbles back.
                    for (int k = 0; k < 8; k++) {
                        leftNib0[k] = leftNibArr[k];
                        rightNib0[k] = rightNibArr[k];
                    }
                    for (int k = 0; k < 8; k++){
                        leftNib1[k] = leftNibArr[k+8];
                        rightNib1[k] = rightNibArr[k+8];
                    }

                    // Fetching char values of nibbles to finalArray.
                    reverse[a] = eu.charDecoding(eu.binary2decimal(leftNib0));
                    reverse[a+1] = eu.charDecoding(eu.binary2decimal(rightNib0));
                    reverse[a+2] = eu.charDecoding(eu.binary2decimal(leftNib1));
                    reverse[a+3] = eu.charDecoding(eu.binary2decimal(rightNib1));
                }

                // Reverse of the initial permutation operation that took place first.

                deCiphered = eu.reverseShuffleIP(initialPermArr , reverse);

                // Write deciphered text to file.
                for (char g: deCiphered) wrt.append(g);

                // Break new line at the end of every line.
                wrt.append("\n");

            }
            // Close writer
            wrt.close();


        } catch (FileNotFoundException e){
            // Handle FileNotFound exceptions.
            System.err.println("woops! There seems to be a problem : " + e);

        } catch (IOException e){
            // Handle IO exceptions.
            System.err.println("Woops! There may be a problem" + e);

        }

    }
}
