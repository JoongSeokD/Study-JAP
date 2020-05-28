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
            account.setUsername("관리자");
            account.setAge(10);
            account.changeTeam(team);
            em.persist(account);

            Account account2 = new Account();
            account2.setUsername("관리자2");
            account2.setAge(10);
            account2.changeTeam(team);

            em.persist(account2);

            em.flush();
            em.clear();

//            String query = "select concat( 'a',  'b') from Account as a";
//            String query = "select substring( a.username, 2, 3) from Account as a";
//            String query = "select locate('de', 'abcdefg') from Account as a";
//            String query = "select size( t.accounts) from Team as t"; // JPA 용도
            String query = "select group_concat (a.username) from Account as a";

            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
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
