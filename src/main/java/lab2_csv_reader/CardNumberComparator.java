package lab2_csv_reader;

import java.util.Comparator;

public class CardNumberComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
        String[] a1 = o1.split("/");
        String[] a2 = o2.split("/");

        Integer[] n1 = new Integer[a1.length];
        Integer[] n2 = new Integer[a2.length];

        for (int i = 0; i < a1.length; i++) {
            n1[i] = Integer.valueOf(a1[i]);
            n2[i] = Integer.valueOf(a2[i]);
        }

        int cmp1 = n1[2].compareTo(n2[2]);
        int cmp2 = n1[1].compareTo(n2[1]);
        int cmp3 = n1[0].compareTo(n2[0]);

        if (cmp1 != 0) {
            return cmp1;
        }

        if (cmp1 == 0) {
            if (cmp2 != 0) {
                return cmp2;
            }

            if (cmp3 != 0) {
                return cmp3;
            }
        }
        return 0;
    }
}
