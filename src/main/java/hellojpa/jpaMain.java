package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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
//            Member member = new Member();
//            member.setId(3L);
//            member.setName("HelloB");
            //em.persist(member);
//            Member member = em.find(Member.class, 1L);
//            System.out.println("findMember.id = "+member.getId()+"");
//            System.out.println("findMember.name = "+member.getName()+"");
//            member.setName("HelloJPA");
            //persist 안해도 됌 트랜잭션 종료 전 변경 사항 확인 후 반영하여 커밋함.

            //JPQL 소개
            //원하는 데이터들을 최적화해서 가져오고, 통계성 쿼리도 사용할 수 있도록 도와줌.
            //JPA에서 쿼리를 만들 때 테이블 대상으로 만들지 않는다. 멤버 객채를 대상으로 함.
            //select m -> 객체 대상
            //엄청난 장점이 존재. 테이블을 대상으로 쿼리를 작성하면 테이블에 종속적인 쿼리가 되어버리는데 객체를 중심으로 작성하면 객체 중심적 쿼리가 됨.
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            //jpa에서는 트랜잭션안에서 작업을 해야함.
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }
}
