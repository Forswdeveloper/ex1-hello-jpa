package hellojpa;

import hellojpa.Item.Movie;
import hellojpa.comn.embedded.Address;
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

public class jpaMain {
    public static void main(String[] args) {

        //Application 로딩 시점에 딱 하나만 존재해야함.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        //트랜잭션의 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Address address = new Address("Seoul", "Gwankakku", "08832");
            Member member1 = new Member();
            member1.setUsername("Hello1");
            member1.setHomeAddress(address);
            em.persist(member1);

            //같은 임베디드 타입 참조 중일 때 값 변경 시도
            //Setter 생성하지 않음으로 불변하도록 설정.
            //member1.getHomeAddress().setCity("New Seoul"); // -> update 쿼리 두번 실행됨.  공유해서 쓰고 싶으면 Entity를 사용해서 써야함.
            //값을 변경하고 싶을 때는 새로 객체를 만들어야 함.
            Address newAddress = new Address("New Seoul", address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("Hello2");
            member2.setHomeAddress(newAddress);
            em.persist(member2);

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
