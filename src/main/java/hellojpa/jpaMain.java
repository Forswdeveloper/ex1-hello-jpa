package hellojpa;

import hellojpa.Item.Movie;
import hellojpa.orgMember.Member;
import hellojpa.team.Team;
import hellojpa.teamMember.TeamMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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
            // 양방향 매핑시 연관관계의 주인에 값을 입력해야한다.
            // 객체지향적으로 생각했을 때에는 양쪽 모두 멤버의 값을 설정해주는것을 권장함.
            //flush,clear를 안해주면 값이 양방향으로 설정되지 않기에 조회가 되지 않아서 값을 확인할 수 없음.
            //또 테스트 케이스 작성할 때 값을 확인할 수 없는 상황이 생길 수 있음.
            //양방향 연관관계 매핑해줄 때에는 순수 객체 상태를 고려해서 양쪽에 값을 셋팅해줘야함.
            //TeamMember findMember = em.find(TeamMember.class, teamMember.getId()); // 1차 캐시에 존재.
            //List<TeamMember> members = findMember.getTeam().getMembers(); //flush,clear안할 시 순수 객체 상태의 값을 가지고 있기에 값이 출력되지 않음.
            //테이블관 연관관계가 없기 때문에 객체가 테이블에 맞게 모델링 되어있는 안좋은 상황 협력 관계를 만들 수 없음.
            //쿼리 실행 시점

            Member member = new Member();
            member.setUsername("user");
            member.setCreatedBy("user");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("findMember.getClass() = " + findMember.getClass());
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getUsername() = " + findMember.getUsername());

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

            //영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
//            Member findMember1 = em.find(Member.class, member1.getId());
//            System.out.println("findMember1.getClass() = " + findMember1.getClass());
//
//            Member findMember2 = em.getReference(Member.class, member1.getId());
            //System.out.println("findMember2.getClass() = " + findMember2.getClass());

            //System.out.println("findMember1 == findMember2 : " + (findMember1 == findMember2));

            //영속성 컨텍스트 준영속 및 해지
            Member refMember = em.getReference(Member.class,member1.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass()); //Proxy
//            em.detach(refMember); // em.cloase(refMember); em.clear();
//            refMember.getUsername(); //could not initialize proxy exception
            //프록시 확인
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //인스턴스의 초기화 여부 확인
            System.out.println("refMember.getClass().getName() = " + refMember.getClass().getName()); //프록시 클래스 확인

            Hibernate.initialize(refMember); //강제 초기화 refMember.getUserName() 도 강제 초기화에 해당함.

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
