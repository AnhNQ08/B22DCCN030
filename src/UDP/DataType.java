package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DataType {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2207;

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);

        String studentCode = "B22DCCN030";
        String qCode = "LYI7zqiu";
        String message = ";" + studentCode + ";" + qCode;

        byte[] sendData = message.getBytes();
        InetAddress address = InetAddress.getByName(host);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        socket.send(sendPacket);

        byte[] buffer = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String received = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();

        String[] parts = received.split(";");
        String requestId = parts[0];
        String[] numbers = parts[1].split(",");

        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (String num : numbers) {
            int n = Integer.parseInt(num.trim());
            if (n > max) max = n;
            if (n < min) min = n;
        }

        String response = requestId + ";" + max + "," + min;
        byte[] responseData = response.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, address, port);
        socket.send(responsePacket);

        socket.close();
    }
}
