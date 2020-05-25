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
            //연관관계 편의 메서드
            acoount.changeTeam(team);
            em.persist(acoount);

            // Team Account 둘다 값을 세팅 해주는게 좋음
            // 객체지향적으로 생각했을때 양쪽에 값이 들어가는게 맞음
            // 테스트 케이스 작성시에도 양쪽에 값을 세팅해줘야 한다.
            // 결론 : 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자.. 양방향 연관관계를 맺을때는 양쪽에 다 값을 세팅해주는게 맞다.
            //연관관계 편의 메서드를 만들자
//            team.getAccounts().add(acoount);

            em.flush();
            em.clear();

            Team team1 = em.find(Team.class, team.getId()); // 1차 캐시
            List<Account> accounts = team1.getAccounts();

            for (Account account : accounts) {
                System.out.println(account.getUsername());
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
