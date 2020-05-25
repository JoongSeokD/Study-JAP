package hellojap;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JapMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Account acoount = new Account();
            acoount.setUsername("account1");
            acoount.setTeam(team);
            em.persist(acoount);

            em.flush();
            em.clear();

            Account findAccount = em.find(Account.class, acoount.getId());

            //읽기만 가능 update, insert 불가
            List<Account> accounts = findAccount.getTeam().getAccounts();

            for (Account account : accounts) {
                System.out.println(acoount.getUsername());
                Account find = em.find(Account.class, acoount.getId());
                find.setUsername("account2");
            }
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();

    }
}
