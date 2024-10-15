package hellojpa.orgMember;

import hellojpa.Locker;
import hellojpa.MemberProduct;
import hellojpa.comn.BaseEntity;
import hellojpa.comn.embedded.Address;
import hellojpa.comn.embedded.Period;
import hellojpa.team.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "USER") //관례상 USER에 속해있는 테이블에 작업해줌
//@SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq")
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName="MEMBER_SEQ",//매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
public class Member extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id; //기본키 생성 전략이 IDENTITY인 경우 예외적으로 영속성 컨텍스트에 들어갈 때 db에 insert쿼리를 전송한다.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "name",nullable = false)
    private String username;

    @OneToOne//EAGERdf
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member") //OneToMany는 기본이 LAZY
    private List<MemberProduct> memberProducts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) //프록시 조회, 값을 사용하는 빈도 수가 많을 시 즉시로딩 설정 고려
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public Locker getLocker() {
        return locker;
    }

    //기간
    @Embedded
    private Period workPeriod = null; //값이 NULL이면 해당 값 모두 NULL
    //주소
    @Embedded
    private Address homeAddress;

    @Embedded
    //속성 재정의
    @AttributeOverrides({
            @AttributeOverride(name = "city"
                    , column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street"
                    , column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode"
                    , column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<MemberProduct> getMemberProducts() {
        return memberProducts;
    }

    public void setMemberProducts(List<MemberProduct> memberProducts) {
        this.memberProducts = memberProducts;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }
}
