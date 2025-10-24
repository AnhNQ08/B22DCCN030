package RMI;

import java.rmi.Naming;

public class OrderClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN030", qCode = "PBGj52zi";
        ObjectService service = (ObjectService) Naming.lookup("rmi://203.162.10.109/RMIObjectService");
        Order order = (Order) service.requestObject(studentCode, qCode);

        String type = order.getShippingType().substring(0, 2).toUpperCase();
        String customer = order.getCustomerCode();
        String last3 = customer.substring(customer.length() - 3);
        String[] date = order.getOrderDate().split("-");
        String ddMM = date[2] + date[1];
        order.setOrderCode(type + last3 + ddMM);

        service.submitObject(studentCode, qCode, order);
    }
}
