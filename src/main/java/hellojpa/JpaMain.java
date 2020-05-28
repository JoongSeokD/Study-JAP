package hellojpa;

import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
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

            Account account = new Account();
            account.setAge(10);
            account.setUsername("teamA");
            account.changeTeam(team);

            em.persist(account);



            em.flush();
            em.clear();

//            String query = "select  a from Account a left join a.team t "; 레프트 조인
//            String query = "select  a from Account a join a.team t "; 이너 조인

//            String query = "select  a from Account a, Team t where a.username = t.name "; // 세타 조인
            String query = "select  a from Account a left join Team t on t.name = a.username"; // 연관관계 없는 외부 조인
            List<Account> resultList = em.createQuery(query, Account.class)

                    .getResultList();
            System.out.println("resultList.size() = " + resultList.size());
            for (Account account1 : resultList) {
                System.out.println("account1 = " + account1);
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
