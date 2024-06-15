package hellojpa.teamMember;

import hellojpa.team.Team;
import jakarta.persistence.*;

@Entity
@Table
public class TeamMember {
    @Id @GeneratedValue
    @Column(name="TEAM_MEMBER_ID")
    private Long id;

    @Column(name ="USERNAME")
    private String username;

    //JPA에게 어떤 관계인지 알려줘야함.
    //팀 1 팀멤버 N : 팀이 멤버를 참조할 생각이 없음. 다대일. 연관관계의 주인 : 팀
    @ManyToOne  //어떤관계인지
    @JoinColumn(name = "TEAM_ID") // 단순 조회용 명시 표현 insertable updatable
    private Team team;

//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public  void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
