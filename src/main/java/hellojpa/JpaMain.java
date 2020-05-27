package hellojpa;

import jpabook.jpashop.domain.*;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            account.setHomeAddress(new Address("homeCity", "street", "10000"));

            account.getFavoriteFoods().add("치킨");
            account.getFavoriteFoods().add("족발");
            account.getFavoriteFoods().add("피자");

            account.getAddressHistory().add(new Address("old1", "street", "10000"));
            account.getAddressHistory().add(new Address("old2", "street", "10000"));

            em.persist(account);

            em.flush();
            em.clear();

            System.out.println("================= START ================");
            Account findAccount = em.find(Account.class, account.getId());

            List<Address> addressHistory = findAccount.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address.getCity() = " + address.getCity());
            }
            Set<String> favoriteFoods = findAccount.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            // 치킨 -> 한식
            findAccount.getFavoriteFoods().remove("치킨");
            findAccount.getFavoriteFoods().add("한식");

            findAccount.getAddressHistory().remove(new Address("old1", "street", "10000"));
            findAccount.getAddressHistory().add(new Address("newCity", "street", "10000"));


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
