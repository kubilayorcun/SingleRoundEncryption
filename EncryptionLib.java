

public class EncryptionLib {
    // Generates binary 8 bit keys and fetches them into square matrix
    public static int[][] keyGenerator(char x, char y){
        int[][] keyMatrix = new int[4][4];

        int[] xArr;
        int [] yArr;

        x = Character.toUpperCase(x);
        y = Character.toUpperCase(y);


        xArr = decimal2binary(charEncoding(x));
        yArr = decimal2binary(charEncoding(y));
        int countX=0 , countY=0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (i < 2) {
                    keyMatrix[i][j] = xArr[countX];
                    countX++;
                }
                if (i >= 2) {
                    keyMatrix[i][j] = yArr[countY];
                    countY++;
                }

            }
        }

        return keyMatrix;
    }

    // Shifts d number of digits to right and rotates the remaning digits to the start.
    public static int[] rightShiftRotate(int[] arr, int d){
        int[] tempArr = new int[16];
        int temp;
        for (int i = 0; i < arr.length; i++){
            temp = i + d;
            if(temp >= 16) temp = temp - arr.length;
            tempArr[temp] = arr[i];
        }
        return tempArr;
    }

    // Reverse of rightShiftRotate method.
    public static int[] leftShiftRotate(int[] arr, int d) {
        int[] tempArr = new int[16];

        int temp;
        for (int i = 0; i < arr.length; i++){
            temp = i - (d);
            if(temp < 0) temp = temp + arr.length;
            tempArr[temp] = arr[i];
        }
        return tempArr;
    }

    // Compares two binary array in terms of exclusiveOr operation and returns resulting binaryArray.
    public static int[] XORencryption(int[] binArr, int[] keyArr){
        int[] encryptedArr = new int[16];
        for (int i = 0; i < 16; i++){
            if(binArr[i] == keyArr[i])
                encryptedArr[i] = 0;
            else encryptedArr[i] = 1;
        }
        return encryptedArr;
    }

    // Below two methods implements 'special encoding table' given in the assignment.
    public static int charEncoding(char C){
        int retVal;
        switch (C){
            case 'A': retVal = 1; break;
            case 'B': retVal = 2; break;
            case 'C': retVal = 3; break;
            case 'Ç': retVal = 4; break;
            case 'D': retVal = 5; break;
            case 'E': retVal = 6; break;
            case 'F': retVal = 7; break;
            case 'G': retVal = 8; break;
            case 'Ğ': retVal = 9; break;
            case 'H': retVal = 10; break;
            case 'I': retVal = 11; break;
            case 'İ': retVal = 12; break;
            case 'J': retVal = 13; break;
            case 'K': retVal = 14; break;
            case 'L': retVal = 15; break;
            case 'M': retVal = 16; break;
            case 'N': retVal = 17; break;
            case 'O': retVal = 18; break;
            case 'Ö': retVal = 19; break;
            case 'P': retVal = 20; break;
            case 'R': retVal = 21; break;
            case 'S': retVal = 22; break;
            case 'Ş': retVal = 23; break;
            case 'T': retVal = 24; break;
            case 'U': retVal = 25; break;
            case 'Ü': retVal = 26; break;
            case 'V': retVal = 27; break;
            case 'Y': retVal = 28; break;
            case 'Z': retVal = 29; break;
            default: retVal = 0; break;


        }
        return retVal;

    }
    public static char charDecoding(int C){
        switch (C){
            case 1 : return 'A';
            case 2 : return 'B';
            case 3 : return 'C';
            case 4 : return 'Ç';
            case 5 : return 'D';
            case 6 : return 'E';
            case 7 : return 'F';
            case 8 : return 'G';
            case 9 : return 'Ğ';
            case 10 : return 'H';
            case 11 : return 'I';
            case 12 : return 'İ';
            case 13 : return 'J';
            case 14 : return 'K';
            case 15 : return 'L';
            case 16 : return 'M';
            case 17 : return 'N';
            case 18 : return 'O';
            case 19 : return 'Ö';
            case 20 : return 'P';
            case 21 : return 'R';
            case 22 : return 'S';
            case 23 : return 'Ş';
            case 24 : return 'T';
            case 25 : return 'U';
            case 26 : return 'Ü';
            case 27 : return 'V';
            case 28 : return 'Y';
            case 29 : return 'Z';
            default: return '.';
        }

    }

    // Converts decimal value into binary array.
    public static  int[] decimal2binary(int decimal){
        int[] binArr = new int[16];

        int i = 0;

        while(decimal > 0){

            binArr[i] = decimal % 2 ;
            decimal = decimal / 2;

            i++;

        }
        while(i < 8){
            binArr[i] = 0;
            i++;
        }
        int start = 0 , end = 7;
        int temp;
        while(start < end){
            temp = binArr[start];
            binArr[start] = binArr[end];
            binArr[end] = temp;
            start++;
            end--;
        }

        return binArr;

    }

    // Converts binary array to decimal values.
    public static int binary2decimal(int[] binArr){
        int decimal = 0;
        int pow = 0;
        for(int i = 7; i >=0; i--){
            decimal += binArr[i] * Math.pow(2 , pow);
            pow++;
        }

        return decimal;
    }

    public static char[] shuffleIP(int[] IP, char[] arr) {

        char[] shuffledArr = new char[12];
        for(int i = 0; i < arr.length; i++) {
            shuffledArr[i] = arr[IP[i] - 1];
        }
        return shuffledArr;
    }
    public static char[] reverseShuffleIP(int[] IP , char[] arr) {
        char[] reverseArr = new char[12];
        for(int i = 0; i < arr.length; i++){
            reverseArr[IP[i] - 1] = arr[i];
        }
        return reverseArr;
    }
    public static void swapArrays(int[] left , int[] right) {
        int temp;
        for(int i = 0; i < 16; i++) {
            temp = left[i];
            left[i] = right[i];
            right[i] = temp;
        }
    }
























}
