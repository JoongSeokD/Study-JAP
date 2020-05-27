package hellojpa;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Movie;
import jpabook.jpashop.domain.Team;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            em.persist(team);

            Account account = new Account();
            account.setUsername("account1");
            account.setTeam(team);
            em.persist(account);


            em.flush();
            em.clear();

            // fetchType.EAGER를 사용하면 n+1만큼 쿼리가 나간다. EAGER는 실무에서 사용 X
            //@ManyToOne, OneToOne은 기본이 즉시 로딩 -> LAZY로 설정 해야함
            List<Account> accounts = em.createQuery("select a from Account a", Account.class)
                    .getResultList();

//            Account findAccount = em.find(Account.class, account.getId());

//            System.out.println("findAccount = " + findAccount.getTeam().getClass());
            System.out.println("===============");
            // 지연로딩 LAZY를 사용해서 프록시로 조회
            // 실제 team을 사용하는 시점에 초기화(DB조회)
//            findAccount.getTeam().getName();
            System.out.println("===============");

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
