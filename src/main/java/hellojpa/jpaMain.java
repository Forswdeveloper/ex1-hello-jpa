package hellojpa;

import hellojpa.Item.Movie;
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

            Child childA = new Child();
            Child childB = new Child();

            Parent parent = new Parent();
            parent.addChild(childA);
            parent.addChild(childB);

            //한번에 자동 영속화 필요. CASECADE의 필요성.
            em.persist(parent);

            em.flush();
            em.clear();

            //고아객체 제거
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0); //자식의 생명주기를 관리.

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
