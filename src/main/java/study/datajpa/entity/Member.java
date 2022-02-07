package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    //jpa 표준 스펙 -> entity생성 시 default 생성자가 하나 있어야 하고 protected로 열어야함
    //proxy 기술 때문에 private로 하면 proxy 기술들이 막힌다.
    protected Member() {
    }

    public Member(String userName) {
        this.userName = userName;
    }

}
