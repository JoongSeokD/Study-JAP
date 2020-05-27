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

            Account account = new Account();
            account.setUsername("account1");
            account.setAge(10);
            em.persist(account);
            // 반환 타입이 명확할 때
            TypedQuery<Account> query1 = em.createQuery("select a from Account a ", Account.class);
            Account singleResult = em.createQuery("select a from Account a  where a.username = :username", Account.class)
                    .setParameter("username", "account1")
                    .getSingleResult();
            List<Account> resultList = query1.getResultList();
            TypedQuery<String> query2 = em.createQuery("select a.username from Account a ", String.class);
            // 반환 타입이 명확하지 않을 때
            Query query3 = em.createQuery("select a.username, a.age from Account a ");


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
