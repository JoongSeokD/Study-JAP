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

            for (int i = 0; i < 100; i++) {
            Account account = new Account();
            account.setUsername("account" + i);
            account.setAge(i);
            em.persist(account);

            }

            em.flush();
            em.clear();

            List<Account> resultList = em.createQuery("select  a from Account a order by a.age desc", Account.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
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
