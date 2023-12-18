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
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            TeamMember teamMember = new TeamMember();
            teamMember.setUsername("teamMember1");
            teamMember.setTeam(team);
            em.persist(teamMember);

            //조인쿼리확인
            em.flush();
            em.clear();

            //테이블관 연관관계가 없기 때문에 객체가 테이블에 맞게 모델링 되어있는 안좋은 상황 협력 관계를 만들 수 없음.
            TeamMember findTeamMember = em.find(TeamMember.class, teamMember.getId());
            Team findTeam = findTeamMember.getTeam();
            System.out.println("findTeam.getId() = " + findTeam.getId());
            


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
