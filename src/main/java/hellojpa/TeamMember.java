package hellojpa;

import jakarta.persistence.*;

@Entity
@Table
public class TeamMember {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name ="USERNAME")
    private String username;

    //JPA에게 어떤 관계인지 알려줘야함.
    //팀 1 팀멤버 N
    @ManyToOne  //어떤관계인지
    @JoinColumn(name = "TEAM_ID") //매핑컬럼
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
//    @Column(name = "TEAM_ID")
//    private Long teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }
}
