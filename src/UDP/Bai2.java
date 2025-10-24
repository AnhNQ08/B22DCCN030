package UDP;

import java.io.*;
import java.net.*;
import UDP.Book;

public class Bai2 {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCCN030";
        String qCode = "p8vNceAR";

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);

        // (a) Gửi yêu cầu
        String message = ";" + studentCode + ";" + qCode;
        InetAddress address = InetAddress.getByName(host);
        socket.send(new DatagramPacket(message.getBytes(), message.length(), address, port));

        // (b) Nhận phản hồi
        byte[] buffer = new byte[8192];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        byte[] data = packet.getData();
        String requestId = new String(data, 0, 8);
        ByteArrayInputStream bais = new ByteArrayInputStream(data, 8, packet.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Book book = (Book) ois.readObject();

        // (c) Chuẩn hóa dữ liệu
        book.setTitle(formatTitle(book.getTitle()));
        book.setAuthor(formatAuthor(book.getAuthor()));
        book.setIsbn(formatIsbn(book.getIsbn()));
        book.setPublishDate(formatDate(book.getPublishDate()));

        // (d) Gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);
        oos.flush();

        byte[] sendData = baos.toByteArray();
        socket.send(new DatagramPacket(sendData, sendData.length, address, port));

        socket.close();
    }

    // Viết hoa chữ cái đầu mỗi từ
    private static String formatTitle(String title) {
        if (title == null || title.isEmpty()) return title;
        String[] words = title.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
                    .append(w.substring(1))
                    .append(" ");
        }
        return sb.toString().trim();
    }

    // Chuyển "Nguyen Van A" -> "NGUYEN, Van A"
    private static String formatAuthor(String author) {
        if (author == null || author.isEmpty()) return author;
        String[] parts = author.trim().split("\\s+");
        if (parts.length == 1) return parts[0].toUpperCase();

        String lastName = parts[0].toUpperCase();
        StringBuilder rest = new StringBuilder();
        for (int i = 1; i < parts.length; i++) {
            rest.append(parts[i]).append(" ");
        }
        return lastName + ", " + rest.toString().trim();
    }

    // Chuyển "2432583293949" -> "243-2-58-329394-9"
    private static String formatIsbn(String isbn) {
        if (isbn == null || isbn.length() < 13) return isbn;
        return isbn.substring(0,3) + "-" + isbn.substring(3,4) + "-" +
                isbn.substring(4,6) + "-" + isbn.substring(6,12) + "-" + isbn.substring(12);
    }

    // yyyy-mm-dd -> mm/yyyy
    private static String formatDate(String date) {
        if (date == null || !date.contains("-")) return date;
        String[] parts = date.split("-");
        if (parts.length < 3) return date;
        return parts[1] + "/" + parts[0];
    }
}
