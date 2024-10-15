package hellojpa;

import hellojpa.Item.Movie;
import hellojpa.comn.embedded.Address;
import hellojpa.comn.embedded.AddressEntity;
import hellojpa.comn.embedded.Period;
import hellojpa.family.Child;
import hellojpa.family.Parent;
import hellojpa.orgMember.Member;
import hellojpa.team.Team;
import hellojpa.teamMember.TeamMember;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class jpaMain {
    public static void main(String[] args) {

        //Application 로딩 시점에 딱 하나만 존재해야함.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        //트랜잭션의 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("NewCity","NewSteet","100000"));

            member.getFavoriteFoods().add("삼겹살");
            member.getFavoriteFoods().add("오리불고기");
            member.getFavoriteFoods().add("김밥");

//            member.getAddressHistory().add(new Address("oldcity1","oldstreet1","100001"));
//            member.getAddressHistory().add(new Address("oldcity2","oldstreet2","100002"));
            member.getAddressHistory().add(new AddressEntity("oldcity1","oldstreet1","100001"));
            member.getAddressHistory().add(new AddressEntity("oldcity2","oldstreet2","100002"));
            em.persist(member); //영속성 컨텍스트에 할당되면 컬렉도 라이프 사이클에 같이 포함됨. -> 값 타입이기 때문

            em.flush();
            em.clear();

            System.out.println("-----------------------------------");
            // 조회
            Member findMember = em.find(Member.class, member.getId());

            //기본적으로 지연로딩이라서 호출해야 조회해옴.
//            List<Address> addressHistory = findMember.getAddressHistory();
//            List<AddressEntity> addressHistory = findMember.getAddressHistory();
////            for (Address address : addressHistory) {
////                System.out.println("address = " + address.getCity());
////            }
//            for (AddressEntity address : addressHistory) {
//                System.out.println("address = " + address.getAddress().getCity());
//            }
//            Set<String> favoriteFoods = findMember.getFavoriteFoods();
//            for (String favoriteFood : favoriteFoods) {
//                System.out.println("favoriteFood = " + favoriteFood);
//            }
//
//            //수정   set 사용 안됨 불변 유지
//            Address homeAddress = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("New City2",homeAddress.getStreet(),homeAddress.getZipcode()));
//
//            findMember.getFavoriteFoods().remove("김밥"); //String은 불변객체 따라서 변경할 수 없고 삭제 후 다시 새로 넣어줘야함
//            findMember.getFavoriteFoods().add("만두");
//
//            //모든 값 삭제 후 Insert 실행.
////            findMember.getAddressHistory().remove(new Address("oldcity1","oldstreet1","100001")); // equals 사용 시점.
////            findMember.getAddressHistory().add(new Address("NewCity3","Newstreet3","100001")); // equals 사용 시점.
//            findMember.getAddressHistory().remove(new AddressEntity("oldcity1","oldstreet1","100001")); // equals 사용 시점.
//            findMember.getAddressHistory().add(new AddressEntity("NewCity3","Newstreet3","100001")); // equals 사용 시점.

            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(Member m1,Member m2) {
        System.out.println("m1 is Member? : " + (m1 instanceof Member));
        System.out.println("m2 is Member? : " + (m2 instanceof Member));
    }

}
