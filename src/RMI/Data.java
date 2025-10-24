package RMI;

import java.rmi.Naming;
import java.util.Arrays;

public class Data {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN030", qCode = "8lGyKTQF";
        DataService s = (DataService) Naming.lookup("rmi://203.162.10.109/RMIDataService");
        int[] a = Arrays.stream(((String) s.requestData(studentCode, qCode)).split(","))
                .map(String::trim).mapToInt(Integer::parseInt).toArray();
        int i = a.length - 2;
        while (i >= 0 && a[i] > a[i + 1]) i--;
        if (i >= 0) {int j = a.length - 1; while (a[j] < a[i]) j--; int t = a[i]; a[i] = a[j]; a[j] = t;}
        for (int l = i + 1, r = a.length - 1; l < r; l++, r--) {int t = a[l]; a[l] = a[r]; a[r] = t;}
        s.submitData(studentCode, qCode, Arrays.toString(a).replaceAll("[\\[\\] ]", ""));
    }
}
