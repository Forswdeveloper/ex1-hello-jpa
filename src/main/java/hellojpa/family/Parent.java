package hellojpa.family;

import hellojpa.comn.BaseEntity;
import jakarta.persistence.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Parent extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true) //컬렉션에서 빠진 엔티티 삭제
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        //Child가 이미 존재하는지 확인
        if(!childList.contains(child)){
            childList.add(child);
            child.setParent(this);
        } else {
            //존재하면 해당 객체를 찾음.
            List<Child> findChilds = childList.stream().filter(a -> a.getId() == child.getId())
                    .collect(Collectors.toList());

            for (Child findChild : findChilds) {
                findChild.setParent(this);
            }
        }

    }

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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
