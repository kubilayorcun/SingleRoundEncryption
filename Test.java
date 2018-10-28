import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);


        Ciphering ciphering = new Ciphering();
        ciphering.Ciphering();
        Deciphering deciphering = new Deciphering();
        deciphering.Deciphering();

        File filePlainText = new File("PlainTextFile");
        File fileDeciphered = new File("Deciphered.txt");
        File fileCipher = new File("CipherTextFile");


        try {
            FileReader fReaderPlain = new FileReader(filePlainText);
            FileReader fReaderDecipher = new FileReader(fileDeciphered);
            FileReader fReaderCipher = new FileReader(fileCipher);
            BufferedReader bufferedReaderPlain = new BufferedReader(fReaderPlain);
            BufferedReader bufferedReadeDecipher = new BufferedReader(fReaderDecipher);
            BufferedReader bufferedReaderCipher = new BufferedReader(fReaderCipher);
            String linePlain;
            String lineDecipher;
            String lineCipher;
            System.out.println("Plain Text File:\n ");
            while(((linePlain = bufferedReaderPlain.readLine()) != null)){

                System.out.println(linePlain);
            }

            System.out.println("\nCiphered Text File:\n ");
            while ((lineCipher = bufferedReaderCipher.readLine()) != null){
                System.out.println(lineCipher);
            }

            System.out.println("\nDeciphered Text File:\n ");
            while ((lineDecipher= bufferedReadeDecipher.readLine()) != null){
                System.out.println(lineDecipher);
            }

            int lineCounter = 0;
            while(((linePlain = bufferedReaderPlain.readLine()) != null)){
                lineCounter++;
                lineDecipher = bufferedReadeDecipher.readLine();
                if(!(linePlain.equals(lineDecipher))){
                    System.out.println("Deciphering is NOT successfull\nNON-matching line found !");
                    System.out.println("Non matching lines (first encounter @line number: " +lineCounter + " )");
                    System.out.println("Plain text file:    " + linePlain);
                    System.out.println("Deciphered text file: " + lineDecipher);
                    System.exit(0);
                }

            }
            System.out.println("\nDECIPHERING IS SUCCESSFUL !\n\n");
        } catch (IOException ex){
            System.err.println("Exception caught: " + ex);
        }

    }

}
