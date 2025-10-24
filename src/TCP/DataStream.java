package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DataStream {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2207;

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String studentCode = "B22DCCN030";
            String qCode = "kGAWvLNs";
            dos.writeUTF(studentCode + ";" + qCode);
            dos.flush();

            int a = dis.readInt();
            int b = dis.readInt();

            int sum = a + b;
            int product = a * b;

            dos.writeInt(sum);
            dos.writeInt(product);
            dos.flush();

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
