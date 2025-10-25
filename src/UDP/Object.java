package UDP;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class Object {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCCN030";
        String qCode = "p8vNceAR";

        DatagramSocket socket = new DatagramSocket();
        String message = ";" + studentCode + ";" + qCode;
        InetAddress address = InetAddress.getByName(host);
        socket.send(new DatagramPacket(message.getBytes(), message.length(), address, port));

        byte[] buffer = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        byte[] data = receivePacket.getData();
        String requestId = new String(data, 0, 8);
        ByteArrayInputStream bais = new ByteArrayInputStream(data, 8, receivePacket.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Book book = (Book) ois.readObject();
        ois.close();

        book.setTitle(formatTitle(book.getTitle()));
        book.setAuthor(formatAuthor(book.getAuthor()));
        book.setIsbn(formatIsbn(book.getIsbn()));
        book.setPublishDate(formatDate(book.getPublishDate()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);
        oos.flush();

        byte[] bookBytes = baos.toByteArray();
        byte[] sendBytes = new byte[8 + bookBytes.length];
        System.arraycopy(requestId.getBytes(), 0, sendBytes, 0, 8);
        System.arraycopy(bookBytes, 0, sendBytes, 8, bookBytes.length);
        socket.send(new DatagramPacket(sendBytes, sendBytes.length, address, port));

        socket.close();
    }

    private static String formatTitle(String title) {
        String[] words = title.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words)
            sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
        return sb.toString().trim();
    }

    // HỌ, Tên -> viết hoa toàn bộ họ, tên hoa đầu mỗi từ
    private static String formatAuthor(String author) {
        String[] parts = author.trim().split("\\s+|;");
        if (parts.length == 0) return author;
        String first = parts[0].toUpperCase(); // họ
        StringBuilder last = new StringBuilder();
        for (int i = 1; i < parts.length; i++) {
            String word = parts[i].toLowerCase();
            last.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return first + ", " + last.toString().trim();
    }

    // chuẩn hóa ISBN dạng ###-#-##-######-#
    private static String formatIsbn(String isbn) {
        isbn = isbn.replaceAll("\\D", "");
        if (isbn.length() >= 13)
            return String.format("%s-%s-%s-%s-%s",
                    isbn.substring(0, 3),
                    isbn.substring(3, 4),
                    isbn.substring(4, 6),
                    isbn.substring(6, 12),
                    isbn.substring(12, 13));
        return isbn;
    }

    private static String formatDate(String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new SimpleDateFormat("MM/yyyy").format(d);
        } catch (Exception e) {
            return date;
        }
    }
}
