package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class ByteStream {
    public static void main(String[] args) throws IOException {
        String serverHost = "203.162.10.109";
        int serverPort = 2206;
        String studentCode = "B22DCCN030";
        String qCode = "S9bWF55q";

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(serverHost, serverPort), 5000);
        socket.setSoTimeout(5000);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        String message = studentCode + ";" + qCode;
        out.write(message.getBytes());
        out.flush();

        byte[] buffer = new byte[2048];
        int bytesRead = in.read(buffer);
        if (bytesRead == -1) {
            socket.close();
            return;
        }

        String data = new String(buffer, 0, bytesRead).trim();
        int[] numbers = Arrays.stream(data.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        List<Integer> longest = findLIS(numbers);

        String seq = longest.toString().replaceAll("[\\[\\] ]", "");
        String result = seq + ";" + longest.size();

        out.write(result.getBytes());
        out.flush();
        socket.close();
    }

    private static List<Integer> findLIS(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        int[] prev = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);
        int maxLen = 1, lastIdx = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIdx = i;
            }
        }

        List<Integer> lis = new ArrayList<>();
        while (lastIdx != -1) {
            lis.add(arr[lastIdx]);
            lastIdx = prev[lastIdx];
        }
        Collections.reverse(lis);
        return lis;
    }
}
