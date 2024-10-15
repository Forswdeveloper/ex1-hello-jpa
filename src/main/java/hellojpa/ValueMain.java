package hellojpa;

import hellojpa.comn.embedded.Address;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        System.out.println(" a == b" + (a==b));

        Address address1 = new Address("Seoul", "Gwankakku", "08832");
        Address address2 = new Address("Seoul", "Gwankakku", "08832");
        System.out.println("address1 == address2 : " + (address1 == address2));
        System.out.println("address1.equals(address2) : " + (address1.equals(address2)));  //ƒalse -> 객체비교라서 재정의가 필요함.
    }
}
