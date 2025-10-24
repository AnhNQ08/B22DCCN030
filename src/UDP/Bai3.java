package UDP;

import java.net.*;

public class Bai3 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2208;

        String msg = ";B22DCCN030;dOTilgbG";
        socket.send(new DatagramPacket(msg.getBytes(), msg.length(), address, port));

        byte[] buf = new byte[4096];
        DatagramPacket pk = new DatagramPacket(buf, buf.length);
        socket.receive(pk);

        String data = new String(pk.getData(), 0, pk.getLength());
        String[] parts = data.split(";", 3);
        String reqId = parts[0];
        String str1 = parts[1];
        String str2 = parts[2];

        StringBuilder out = new StringBuilder();
        for (char c : str1.toCharArray())
            if (!str2.contains(String.valueOf(c)))
                out.append(c);

        String result = reqId + ";" + out;
        socket.send(new DatagramPacket(result.getBytes(), result.length(), address, port));
        socket.close();
    }
}
