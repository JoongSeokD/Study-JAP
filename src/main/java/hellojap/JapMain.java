package hellojap;

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


            // 같은걸 두번 조회하면 1차 캐시에 조회함 쿼리는 한번만 나간다.
            // 영속성 컨텍스트 1차 캐시에서 먼저 찾음
            Member member1 = em.find(Member.class, 101L);
            Member member2 = em.find(Member.class, 101L);

            // 영속 엔티티의 동일성 보장
            System.out.println(member1 == member2);

            // 엔티티 등록시 트랜잭션을 지원하는 쓰기 지연
            Member member3 = new Member(10L, "JPA");
            Member member4 = new Member(12L, "JAP");

            // 1차 캐시와 쓰기 지연 SQL 저장소에 쌓아둔다.
            em.persist(member3);
            em.persist(member4);

            System.out.println("======================");

            //엔티티 수정 변경 감지 (Dirty Checking)
            //변경 감지 기능이 있기 때문에 em.update(member1)을 하지 않아도 된다.
            //커밋 시점에 find할 때 찾아온 Entity 변경을 감지 하여 변경이 있으면
            //Update 쿼리를 생성하여 쓰기지연 SQL 저장소에 쌓는다.
            member1.setName("hi");

            // 변경 감지 메커니즘
            //1. 커밋을 하면 flush()가 일어난다.
            //2. 엔티티와 스냅샷을 비교 (1차 캐시에는 최초로 영속성 컨텍스트에 들어온 엔티티의 정보를 담아둔 스냅샷이 있음)
            //3. 비교 후 변경이 있으면 Update쿼리를 SQL 저장소에 저장
            //4. 쿼리를 데이터 베이스에 반영
            //5. 커밋 실행
            
            // 엔티티를 변경할때는 persist를 호출하지 않는게 좋은 방법이다.
            // 트랜잭션 안에서 set으로 변경하는게 좋음

            // flush() : 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
            // 영속성 컨텍스트를 flush 하는 방법
            //1. em.flush() - 직접 호출
            //2. 트랜잭션 커밋 - 플러시 자동 호출
            //3. JPQL 쿼리 실행 - 플러시 자동 호출

            // flush를 하여도 1차 캐시는 그대로 존재함 1차 캐시를 지우는게 아님
            // 오직 영속성 컨텍스트에 있는 쓰기 지연 SQL 저장소에 있는 쿼리를 데이터베이스에 반영 하는 과정

            //flush 메커니즘
            //1. 영속성 컨텍스트를 비우지 않음
            //2. 영속성 컨텍스트의 변경내용을 DB에 동기화

            // 준영속 상태 : 영속성 컨텍스트가 관리하지 않는상태
            // 준영속 상태로 만드는 방법
            //1. em.detach(entity);
            //2. em.clear();
            //3. em.close();



            
            //tx.commit()시점에 쓰기지연 SQL저장소에서 쿼리가 실행된다.
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();

    }
}
