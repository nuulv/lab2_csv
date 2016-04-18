package lab2_csv_reader;

import java.util.Arrays;
import java.util.Scanner;

public class CardNumber implements Comparable<CardNumber>{
    private Integer[] n = new Integer[3];


    public CardNumber(String cardNumber){
        if (cardNumber != null){
            Scanner scanner = new Scanner(cardNumber);
            scanner.useDelimiter("/");
            for (int i = 0; i < 3; i++ ){
                if (scanner.hasNextInt()) {
                    n[i] = scanner.nextInt();
                }
            }
        }
    }


    @Override
    public int compareTo(CardNumber o) {
        int cmp1 = n[2].compareTo(o.n[2]);
        int cmp2 = n[1].compareTo(o.n[1]);
        int cmp3 = n[0].compareTo(o.n[0]);

        if (cmp1 != 0) {
            return cmp1;
        }

        if (cmp2 != 0) {
            return cmp2;
        }

        if (cmp3 != 0) {
            return cmp3;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "n=" + Arrays.toString(n);
    }
}
