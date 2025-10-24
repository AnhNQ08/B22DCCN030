package RMI;

import java.rmi.Naming;

public class Byte {
    public static void main(String[] args) {
        try {
            String studentCode = "B22DCCN030";
            String qCode = "1gUXWIGR";
            String key = "PTIT";

            ByteService service = (ByteService) Naming.lookup("rmi://203.162.10.109/RMIByteService");

            byte[] data = service.requestData(studentCode, qCode);

            byte[] keyBytes = key.getBytes();
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (data[i] ^ keyBytes[i % keyBytes.length]);
            }

            service.submitData(studentCode, qCode, data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
