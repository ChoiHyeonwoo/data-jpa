package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
//jpa 표준 스펙 -> entity생성 시 default 생성자가 하나 있어야 하고 protected로 열어야함
//proxy 기술 때문에 private로 하면 proxy 기술들이 막힌다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//연관관계 필드는 toString 하지말것
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userName;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 실무에서는 성능 최적화를 위해 LAZY로 세팅할 것.
    @JoinColumn(name="team_id")
    private Team team;

    public Member(String userName) {
        this.userName = userName;
    }

    public Member(String userName, int age, Team team) {
        this.userName = userName;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    // 연관관계 변경에 대한 처리.
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

}
