package repository;

import com.project.userapp.entity.Kinder;
import com.project.userapp.command.KinderVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KinderRepository extends JpaRepository<Kinder,Long>
        , QuerydslPredicateExecutor<Kinder>/*쿼리dsl에서 몇가지 함수를 제공*/ {

    // 1. 이름으로 조회 ( 검색값)
    Page<Kinder> findByKinderNameContaining(String kinderName, Pageable pageable);

    // 2. kinderWeekendOpen값으로 조회 (휴일 오픈)
    Page<Kinder> findByKinderWeekendOpen(String kinderWeekendOpen, Pageable pageable);

    // 3. kinderNightOpen 값으로 조회 (야간오픈)
    Page<Kinder> findByKinderNightOpen(String kinderNightOpen, Pageable pageable);

    // 4. kinderAddress에 특정 문자열 포함 조회 (지역구)
    Page<Kinder> findByKinderAddressContaining(String address, Pageable pageable);

    // 5. 전체 데이터를 페이징으로 조회 (전체데이터)
    Page<Kinder> findAll(Pageable pageable);
}
