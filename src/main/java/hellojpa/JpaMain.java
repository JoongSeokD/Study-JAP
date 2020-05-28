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

            String query = "select a.username, 'HELLO', TRUE from Account a " +
                    "where a.type = :userType";
            em.createQuery(query)
                    .setParameter("userType", AccountType.USER)
                    .getResultList();


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
