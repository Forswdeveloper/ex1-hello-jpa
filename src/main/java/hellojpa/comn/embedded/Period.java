package hellojpa.comn.embedded;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Period {
    private LocalDateTime startDate;
    private java.time.LocalDateTime endDate;

    //기본 생성자 필수
    public Period() {

    }

//    public boolean isWork() {
//
//    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
