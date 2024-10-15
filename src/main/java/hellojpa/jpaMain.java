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

            Member member = new Member();
            member.setUsername("Hello");
            member.setHomeAddress(new Address("Seoul","Gwankakku","08832"));
            member.setWorkPeriod(new Period());

            em.persist(member);

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
