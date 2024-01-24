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
    @JoinColumn(name = "TEAM_ID",insertable = false,updatable = false) // 단순 조회용
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

}
