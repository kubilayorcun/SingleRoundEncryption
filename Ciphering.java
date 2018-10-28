import java.io.*;
import java.nio.charset.StandardCharsets;


public class Ciphering extends EncryptionLib {
    public void Ciphering(){
        File file = new File("PlainTextFile");
        String line;
        EncryptionLib eu = new EncryptionLib();

        int[] leftNibArr;
        int[] leftNibArr2;
        int[] rightNibArr;
        int[] rightNibArr2;
        int[] leftNib0  = new int[8];
        int[] leftNib1  = new int[8];
        int[] rightNib0 = new int[8];
        int[] rightNib1 = new int[8];

        int[] colSelect2 = {3,1,0,2};
        int[] colSelect3 = {0,1,3,2};
        int[] key2arr = new int[16];
        int[] key3arr = new int[16];
        int[] key4arr;
        int[] key5arr;
        int[][] originalKey;

        char[] pWordArr;
        char[] shuffledPass;
        int[] initialPermArr = {7 , 2 , 1, 3 , 5 , 6 , 9 , 10 , 4 , 8 , 12 , 11};
        char[] reverseIP = new char[12];


        try{
            PrintWriter wrt = new PrintWriter("CipherTextFile" , StandardCharsets.UTF_8);
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

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null){
                line.toUpperCase(); // Input_handling for lowerCase input cases
                pWordArr = line.toCharArray();    // Fetching the readed line to char[] array

                // Shuffling password with respect to IP
                shuffledPass = eu.shuffleIP(initialPermArr , pWordArr);

                for(int j = 0; j < 12; j=j+4){
                    leftNibArr = eu.decimal2binary(eu.charEncoding(shuffledPass[j]));
                    rightNibArr = eu.decimal2binary(eu.charEncoding(shuffledPass[j+1]));
                    leftNibArr2 = eu.decimal2binary(eu.charEncoding(shuffledPass[j+2]));
                    rightNibArr2 = eu.decimal2binary(eu.charEncoding(shuffledPass[j+3]));

                    for (int k = 0; k < 8; k++){ // Creating final LeftNib and RightNib as 16 bits to send to XORenc
                        leftNibArr[k+8] = leftNibArr2[k];
                        rightNibArr[k+8] = rightNibArr2[k];
                    }

                    // (SRR , 4) / step 4 from projSheet
                    leftNibArr = eu.rightShiftRotate(leftNibArr , 4); // SRR,4
                    rightNibArr = eu.rightShiftRotate(rightNibArr, 4); // SRR,4

                    // XORencryption part / steps 6 , 8 from projSheet
                    leftNibArr = eu.XORencryption(leftNibArr, key2arr);
                    rightNibArr = eu.XORencryption(rightNibArr, key3arr);

                    // Swapping left and right nibbles / step 9 from projSheet
                    eu.swapArrays(leftNibArr , rightNibArr);

                    leftNibArr = eu.XORencryption(key4arr , leftNibArr);
                    rightNibArr = eu.XORencryption(key5arr , rightNibArr);

                    // Swapping left and right nibbles back and merging resulting chars
                    eu.swapArrays(leftNibArr, rightNibArr);

                    // For nxt two iterations: Dividing the 16 bit to 8 bits. For char Conversion.
                    for (int c = 0; c < 8; c++){
                        leftNib0[c] = leftNibArr[c];
                        rightNib0[c] = rightNibArr[c];

                    }
                    for (int c = 8; c < 16; c++){
                        leftNib1[c-8] = leftNibArr[c];
                        rightNib1[c-8] = rightNibArr[c];
                    }

                    // Fetching chars into new array preparing to reverseIP operation.
                    reverseIP[j]   = (char)(eu.binary2decimal(leftNib0));
                    reverseIP[j+1] = (char)(eu.binary2decimal(rightNib0));
                    reverseIP[j+2] = (char)(eu.binary2decimal(leftNib1));
                    reverseIP[j+3] = (char)(eu.binary2decimal(rightNib1));

                }
                // Implements reverseIP operation.
                pWordArr = eu.reverseShuffleIP(initialPermArr , reverseIP);

                // Writes final ciphered text blocks to 'CipherTextFile' file.
                for (char c : pWordArr){
                    wrt.append(c);
                }
                // Creates seperate line by using \n 'new line'
                wrt.append("\n");
            }
            // Closes fileWriter
            wrt.close();
        } catch (FileNotFoundException ex) {

            System.out.println("Unable to open the file: " + file);

        } catch (IOException ioEx) {

            System.out.println("Error reading the file: " + file);

        }
    }




}
