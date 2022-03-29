/**
* Name: Lucy Fekade
* Pennkey: lucyfe
* Execution: java Caesar [commandLineArguements]
*
* Program Description: This program allows for a message to either be encrypted
* or decrypted with a given key to shift each character by. It can also decrypt
* any given message without a given key using the "crack" method.
*
*/

public class Caesar {



    /*
    * Description: converts a string to a symbol array,
    *              where each element of the array is an
    *              integer encoding of the corresponding
    *              element of the string.
    * Input:  the message text to be converted
    * Output: integer encoding of the message
    */
    public static int[] stringToSymbolArray(String str)
    {
        // TODO: Implement
        str = str.toUpperCase();
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++)
        {
            arr[i] = (int) str.charAt(i);
            arr[i] -= 65;
        }
        return arr;
    }

    /*
    * Description: converts an array of symbols to a string,
    *              where each element of the array is an
    *              integer encoding of the corresponding
    *              element of the string.
    * Input:  integer encoding of the message
    * Output: the message text
    */
    public static String symbolArrayToString(int[] symbols)
    {
        // TODO: Implement
        String str = "";
        for (int i = 0; i < symbols.length; i++)
        {
            int num = symbols[i];
            num += 65;
            char c = (char) num;
            str += c;
        }

        return str;


    }

    /**
    * TODO: Add Method Header
    * Description: shifts each symbol by an offset amount
    *
    * Input: the symbols or int representations of the chars in the message
    *        and the amount by which each symbol will be shifted
    * Output: the symbol will be shifted by that offset amount (returns integer)
    */
    public static int shift(int symbol, int offset)
    {
        // TODO: Implement
        if (symbol >= 0 && symbol <= 25)
        {
            symbol += offset;
            if (symbol > 25)
            {
                int num = symbol - 26;
                symbol = 0;
                symbol += num;
            }
        }
        return symbol;
    }

    /**
    * TODO: Add Method Header
    * Description: unshifts an already "encrypted" symbol
    *
    * Input: the symbols or int representations of the chars in the message
    *        and the amount by which each symbol will be unshifted
    * Output: symbol unshifted by an offset amount (returns integer)
    */
    public static int unshift(int symbol, int offset) {
        // TODO: Implement
        if (symbol >= 0 && symbol <= 25)
        {
            symbol -= offset;
            if (symbol < 0)
            {
                int num = symbol + 26;
                return num;
            }
        }
        return symbol;
    }

    /**
    * TODO: Add Method Header
    * Description: given a message, this function will encrypt it
    *              by the given key amount
    * Input: message to be incrypted, key amount to encrypt message by
    * Output: returns a string of the given message after it has been
    *         encrypted
    */
    public static String encrypt(String message, int key) {
        // TODO: Implement
        int [] arr = stringToSymbolArray(message);
        int[] arr2 = new int[message.length()];
        for (int i = 0; i < message.length(); i++)
        {
            //System.out.print("This is the number unshifted" + arr[i]);
            int num = shift(arr[i], key);
            //System.out.println("This is the number shifted " + num);
            arr2[i] = num;
            //System.out.println(arr2[i]);
        }
        return symbolArrayToString(arr2);
    }

    /**
    * TODO: Add Method Header
    * Description: decrypts an already encrypted cipher by a key amount
    *
    * Input: a string that has been encrypted, an integer to decrypts the string by
    * Outout: a string that has been decrypted
    */
    public static String decrypt(String cipher, int key) {
        // TODO: Implement
        int[] arr = stringToSymbolArray(cipher);
        int [] arr2 = new int[cipher.length()];
        for (int i = 0; i < cipher.length(); i++)
        {
            int num = unshift(arr[i], key);
            arr2[i] = num;
        }
        return symbolArrayToString(arr2);
    }

    /**
    * TODO: Add Method Header
    * Description: turns the frequencies from a given file and puts them
    *              into a double array
    * Input: a filename as a string
    * Output: file in array form (double array)
    */
    public static double[] getDictionaryFrequencies(String filename) {
        // TODO: Implement
        In inStream = new In(filename);
       // double num = inStream.readDouble();
        double[] frequencies = new double[26];
        for (int i = 0; i < 26; i++)
        {
            frequencies[i] = inStream.readDouble();
        }
        return frequencies;
    }

    /**
    * TODO: Add Method Header
    * Description: calculates frequency of each letter in the "string"
    *               (their integer representations)
    * Input: a string as its characters integer representations (int array)
    * Output: frequency of each character (in order where index 0 represents
    *         frequency of the letter A, second index: that of B).
    */

    public static double[] findFrequencies(int[] symbols)
    {
        // TODO: Implement
        int num = 0;
        double[] frequencies = new double[26];
        for (int i = 0; i < symbols.length; i++)
        {
            if (symbols[i] >= 0 && symbols[i] <= 25)
            {
                frequencies[symbols[i]]++;
                num++;
            }
        }
        for (int m = 0; m < frequencies.length; m++)
        {
            frequencies[m] /= num;
        }
        return frequencies;
    }

    /**
    * TODO: Add Method Header
    * Description: scoreFrequencies tells you how well each frequency
    *              matches to what we would expect of in english
    *              (english.txt)
    * Input: takes in getDictionaryFrequencies(for english text file) and
    *        the current frequencies of each character
    * Output: returns a double value of how close the decrypted message is to
    *         english. the lower the score, the closer it is.
    *
    */
    public static double scoreFrequencies(double[] english, double[] currentFreqs) {
        // TODO: Implement
        double value = 0;
        for (int i = 0; i < english.length; i++)
        {
            value += Math.abs(currentFreqs[i] - english[i]);
        }
        return value;
    }
    /**
     * TODO: Add Method Header
     * Description: decrypts a given message without a known key
     *              Tests every key from 0-25
     * Input: message to decode and a file that holds the frequencies in
     *        english of every letter A-Z
     * Output: returns a string message that has been decoded at the
     *         key in which scoreFrequencies thinks is the lowest.
     **/
    public static String crack(String message, String filename)
    {
        double min = 0;
        int pos = 0;
        String cipher = "";
        double[] freq = new double[26];
        for (int i = 0; i < 26; i++)
        {
            cipher = decrypt(message, i);

            freq[i] = scoreFrequencies(getDictionaryFrequencies(filename),
                                     findFrequencies(stringToSymbolArray(cipher)));
        }
        min = freq[0];
        for (int i = 0; i < 26; i++)
        {
            if (freq[i] < min)
            {
                min = freq[i];
                pos = i;
            }
        }
        String word = decrypt(message, pos);
        return word;
    }
    public static void main(String[] args)
    {
        /** TEST CODE
        int[] arr = stringToSymbolArray("CONSULab123 !!!");
        for(int i = 0; i < arr.length; i++)
        {
            System.out.println(arr[i] + " ");
        }

        System.out.println(symbolArrayToString(arr));

       // System.out.println(shift(6, 5));


        System.out.println(unshift(shift(6, 5), 5));
        /**
        System.out.println(encrypt("ET TU, BRUTE?", 6));
        System.out.println(decrypt("KZ ZA, HXAZK?", 6));
        **/



        String type = args[0];
        String filename = args[1];
        int key = 0;
        String file = "";


        if (args[2].length() > 1)
        {
            file += args[2];
        }
        else
        {
            key += (int) (args[2].charAt(0));
        }
        key -= 'A';
        //char key = (char)num;
        In inStream = new In(filename);
        String message = inStream.readAll();

        if (type.equals("encrypt"))
        {
            String word2 = encrypt(message, key);
            System.out.println(word2);
        }
        else if (type.equals("decrypt"))
        {
            String word = decrypt(message, key);
            System.out.println(word);
        }

        else if (type.equals("crack"))
        {
            String word3 = crack(message, file);
            System.out.println(word3);

        }

        /** TEST CASES
        String word = "aAb?cy. iop";
        int[] arr = stringToSymbolArray(encrypt(word, 6));
        for(int k = 0; k < arr.length; k++)
        {
            System.out.println(arr[k]);

        }
        System.out.println();
        double[] arr2 = findFrequencies(arr);
        for(int i = 0; i<arr2.length; i++)
        {
            System.out.println(arr2[i]);
        }
        **/




   }
}
