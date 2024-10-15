package hellojpa.orgMember;

import hellojpa.Locker;
import hellojpa.MemberProduct;
import hellojpa.comn.BaseEntity;
import hellojpa.comn.embedded.Address;
import hellojpa.comn.embedded.AddressEntity;
import hellojpa.comn.embedded.Period;
import hellojpa.team.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    //값 타입 컬렉션
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID")) // name : 컬렉션 테이블명
    @Column(name = "FOOD_NAME") //예외적으로 String 단일 값을 가지는 구조로 원하는 컬럼명을 지정하여 생성 가능.
    private Set<String> favoriteFoods = new HashSet<>();

    //@OrderColumn(name = "address_history_order") //순서 값이 들어감으로 구별이 가능하지만, 순서 누락 시 값이 NULL로 설정될 위험이 있음. 복잡한 사용이 예상되면 사용하면 안됨.
//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>(); // Address의 객체를 사용.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>(); // 값 타입보다 유리.

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

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

//    public List<Address> getAddressHistory() {
//        return addressHistory;
//    }
//
//    public void setAddressHistory(List<Address> addressHistory) {
//        this.addressHistory = addressHistory;
//    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
