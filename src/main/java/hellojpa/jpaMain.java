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
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5)
//                    .setMaxResults(8)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.name = " + member.getName());
//            }
            //비영속 상태
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");

            //영속
//            System.out.println("=== Before ===");
//            em.persist(member);
//            System.out.println("=== After ===");
//            === Before ===
//            === After ===
//                                Hibernate:
//                /* insert for
//                    hellojpa.Member */insert
//                                into
//                        Member (name,Id)
//                        values
//                                (?,?)
            //영속 상태가 된다고해서 바로 쿼리가 실행되지 않음

            //1차 캐쉬에서 조회
//            Member findMember1 = em.find(Member.class, 101L);  //첫번째 조회시에는 쿼리가 실행
//            Member findMember2 = em.find(Member.class, 101L);  //두번째 조회시에는 1차캐쉬에서 조회
//
//            System.out.println("findMember.id = "+findMember1.getId());
//            System.out.println("findMember.name = "+findMember1.getName());
//            //영속 엔티티의 동일성 보장
//            System.out.println("result = " + (findMember1 == findMember2));

            //트랜잭션 커밋 시점에 영속성 컨텍스트에 있는것들이 쿼리가 실행됨.

            // jpa에서는 트랜잭션안에서 작업을 해야함.
            //엔티티 등록 : 트랜잭션을 지원하는 쓰기 지연
//            Member member1 = new Member(150L,"A");
//            Member member2 = new Member(160L,"B");
//
//            em.persist(member1);
//            em.persist(member2);
//            System.out.println("=================");

            //엔티티 수정 (변경 감지) flush() -> 엔티티 스냅샷과 비교 -> update 생성 -> flush() -> commit
            // flush() : 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영
//            Member member = em.find(Member.class,150L);
//            member.setName("ZZZZZ");
//            System.out.println("=================");
            Member member = new Member(200L,"member200",10);
            em.persist(member);
            em.flush();

            System.out.print("==================");

            //쿼리 실행 시점
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
