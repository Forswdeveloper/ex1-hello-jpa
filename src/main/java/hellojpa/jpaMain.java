package hellojpa;

import hellojpa.team.Team;
import hellojpa.teamMember.TeamMember;
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
            teamMember.setTeam(team); // 양방향 매핑시 연관관계의 주인에 값을 입력해야한다.
            em.persist(teamMember);

//            team.getMembers().add(teamMember);
            team.addMember(teamMember);

            // 객체지향적으로 생각했을 때에는 양쪽 모두 멤버의 값을 설정해주는것을 권장함.

            //team.addMember(teamMember);

            //조인쿼리확인
            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<TeamMember> members = findTeam.getMembers();

            System.out.println("------------------");
            System.out.println(members.size());
            for (TeamMember member : members) {
                System.out.println("member = " + member.getUsername());

            }
            System.out.println("------------------");


            //flush,clear를 안해주면 값이 양방향으로 설정되지 않아서 조회가 되지 않아서 값을 확인할 수 없음.
            //또 테스트 케이스 작성할 때 값을 확인할 수 없는 상황이 생길 수 있음.
            //양방향 연관관계 매핑해줄 때에는 순수 객체 상태를 고려해서 양쪽에 값을 셋팅해줘야함.
            //TeamMember findMember = em.find(TeamMember.class, teamMember.getId()); // 1차 캐시에 존재.
            //List<TeamMember> members = findMember.getTeam().getMembers(); //flush,clear안할 시 순수 객체 상태의 값을 가지고 있기에 값이 출력되지 않음.

//            for (TeamMember m : members) {
//                System.out.println("m. = " + m.getUsername());
//            }
            
            //테이블관 연관관계가 없기 때문에 객체가 테이블에 맞게 모델링 되어있는 안좋은 상황 협력 관계를 만들 수 없음.
//            TeamMember findTeamMember = em.find(TeamMember.class, teamMember.getId());
//            Team findTeam = findTeamMember.getTeam();
//            System.out.println("findTeam.getId() = " + findTeam.getId());

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
