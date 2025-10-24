package TCP;

import java.io.*;
import java.net.Socket;

public class ObjectStream {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2209;

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            String studentCode = "B22DCCN030";
            String qCode = "XwOgJVB4";
            oos.writeObject(studentCode + ";" + qCode);
            oos.flush();

            Laptop laptop = (Laptop) ois.readObject();

            // --- Sửa tên sản phẩm ---
            String[] words = laptop.getName().split(" ");
            if (words.length >= 2) {
                // Đổi vị trí từ đầu và từ cuối
                String temp = words[0];
                words[0] = words[words.length - 1];
                words[words.length - 1] = temp;
                laptop.setName(String.join(" ", words));
            }

            String reversedQty = new StringBuilder(String.valueOf(laptop.getQuantity())).reverse().toString();
            laptop.setQuantity(Integer.parseInt(reversedQty));

            oos.writeObject(laptop);
            oos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
