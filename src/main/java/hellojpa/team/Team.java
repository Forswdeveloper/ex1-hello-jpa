package hellojpa.team;

import hellojpa.teamMember.TeamMember;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    //mappedBy -> 양방향 연관관계 조회용 명시
    @OneToMany(mappedBy = "team")
    private List<TeamMember> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public void addMember(TeamMember teamMember){
        teamMember.setTeam(this);
        members.add(teamMember);
    }
}
