package study.datajpa.dto;

import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    //cf) dto에 entity 의존은 가능,, entity에 dto의존은 X
    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUserName();
    }
}
