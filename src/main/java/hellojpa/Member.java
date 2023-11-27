package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Member")
//@Table(name = "USER") //관례상 USER에 속해있는 테이블에 작업해줌
public class Member {

    @Id
    private Long Id;

    @Column(unique = true,length = 10)
    private String name;

    @Column(nullable = true)
    private int age;

    public Member() {}

    public Member(Long id, String name) {
        Id = id;
        this.name = name;
    }

    public Member(Long id, String name, int age) {
        Id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
