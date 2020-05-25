package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Account {

    @Id @GeneratedValue
    @Column(name="ACCOUNT_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    //연관관계 편의 메서드
    public void changeTeam(Team team) {
        this.team = team;
        team.getAccounts().add(this);
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
}
