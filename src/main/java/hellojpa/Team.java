package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    //mappedBy -> 변수명
//    @OneToMany(mappedBy = "team")
    @OneToMany
    @JoinColumn(name = "TEAM_ID") // 일대다
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

//    public void addMember(TeamMember teamMember){
//        teamMember.setTeam(this);
//        members.add(teamMember);
//    }
}
