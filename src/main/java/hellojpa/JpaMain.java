package hellojpa;

import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);
            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Account account = new Account();
            account.setUsername("관리자");
            account.setAge(10);
            account.changeTeam(team);
            em.persist(account);

            Account account2 = new Account();
            account2.setUsername("관리자2");
            account2.setAge(20);
            account2.changeTeam(team);
            em.persist(account2);

            Account account3 = new Account();
            account3.setUsername("관리자3");
            account3.setAge(30);
            account3.changeTeam(team2);
            em.persist(account3);

            em.flush();
            em.clear();

            String query = "select a from Account a join fetch a.team";

            List<Account> resultList = em.createQuery(query, Account.class)
                    .getResultList();
            for (Account account1 : resultList) {
                System.out.println("account = " + account1.getUsername());
                System.out.println("account1 = " + account1.getTeam().getName());
            }


            String query2 = "select distinct t from Team t join fetch t.accounts";

            List<Team> resultList2 = em.createQuery(query2, Team.class)
                    .getResultList();

            for (Team team1 : resultList2) {
                System.out.println("team1 = " + team1.getName() + " | " + team1.getAccounts().size());
                for (Account account1 : team1.getAccounts()){
                    System.out.println("account = " + account1);
                }
            }

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }


        emf.close();

    }
}
