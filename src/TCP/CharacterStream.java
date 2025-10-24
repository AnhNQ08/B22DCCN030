package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class CharacterStream {
    public static void main(String[] args) throws IOException {
        String serverHost = "203.162.10.109";
        int serverPort = 2208;
        String studentCode = "B22DCCN030";
        String qCode = "mA1YMemc";

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(serverHost, serverPort), 5000);
        socket.setSoTimeout(5000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String message = studentCode + ";" + qCode;
        writer.write(message);
        writer.newLine();
        writer.flush();

        String data = reader.readLine();
        if (data == null) {
            socket.close();
            return;
        }

        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : data.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> e : freq.entrySet()) {
            if (e.getValue() > 1) result.append(e.getKey()).append(":").append(e.getValue()).append(",");
        }

        writer.write(result.toString());
        writer.newLine();
        writer.flush();
        socket.close();
    }
}
