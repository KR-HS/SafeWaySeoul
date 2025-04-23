package com.project.userapp.repository;

import com.project.userapp.entity.Kinder;
import com.project.userapp.command.KinderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


public interface KinderRepository extends JpaRepository<Kinder,Long>
        , QuerydslPredicateExecutor<Kinder>/*쿼리dsl에서 몇가지 함수를 제공*/ {

    // 0.전체 데이터를 페이징으로 조회 (전체데이터)
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k",
            countQuery = "SELECT COUNT(k) FROM Kinder k")
    Page<Kinder> findAll(Pageable pageable);

    // 1.이름으로 조회 ( 검색값)
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderName LIKE %:kinderName%",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderName LIKE %:kinderName%")
    Page<Kinder> findByKinderNameContaining(String kinderName, Pageable pageable);

    // 2.kinderWeekendOpen값으로 조회 (휴일 오픈)
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderWeekendOpen = :kinderWeekendOpen",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderWeekendOpen = :kinderWeekendOpen")
    Page<Kinder> findByKinderWeekendOpen(String kinderWeekendOpen, Pageable pageable);

    // 3.kinderNightOpen 값으로 조회 (야간오픈)
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderNightOpen = :kinderNightOpen",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderNightOpen = :kinderNightOpen")
    Page<Kinder> findByKinderNightOpen(String kinderNightOpen, Pageable pageable);

    // 4. kinderAddress에 특정 문자열 포함 조회 (지역구)
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderAddress LIKE %:address%",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderAddress LIKE %:address%")
    Page<Kinder> findByKinderAddressContaining(String address, Pageable pageable);

    // 5. 검색값 + 주말
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderName LIKE %:kinderName% AND k.kinderWeekendOpen = :kinderWeekendOpen",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderName LIKE %:kinderName% AND k.kinderWeekendOpen = :kinderWeekendOpen")
    Page<Kinder> findByKinderNameContainingAndKinderWeekendOpen(String kinderName, String kinderWeekendOpen, Pageable pageable);

    // 6. 검색값 + 야간
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderName LIKE %:kinderName% AND k.kinderNightOpen = :kinderNightOpen",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderName LIKE %:kinderName% AND k.kinderNightOpen = :kinderNightOpen")
    Page<Kinder> findByKinderNameContainingAndKinderNightOpen(String kinderName, String kinderNightOpen, Pageable pageable);

    // 7. 검색값 + 지역구
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderName LIKE %:kinderName% AND k.kinderAddress LIKE %:address%",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderName LIKE %:kinderName% AND k.kinderAddress LIKE %:address%")
    Page<Kinder> findByKinderNameContainingAndKinderAddressContaining(String kinderName, String address, Pageable pageable);

    // 8. 주말 + 지역구
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderWeekendOpen = :kinderWeekendOpen AND k.kinderAddress LIKE %:address%",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderWeekendOpen = :kinderWeekendOpen AND k.kinderAddress LIKE %:address%")
    Page<Kinder> findByKinderWeekendOpenAndKinderAddressContaining(String kinderWeekendOpen, String address, Pageable pageable);

    // 9. 야간 + 지역구
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderNightOpen = :kinderNightOpen AND k.kinderAddress LIKE %:address%",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderNightOpen = :kinderNightOpen AND k.kinderAddress LIKE %:address%")
    Page<Kinder> findByKinderNightOpenAndKinderAddressContaining(String kinderNightOpen, String address, Pageable pageable);

    // 10. 검색값 + 주말 + 지역구
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderName LIKE %:kinderName% AND k.kinderWeekendOpen = :kinderWeekendOpen AND k.kinderAddress LIKE %:address%",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderName LIKE %:kinderName% AND k.kinderWeekendOpen = :kinderWeekendOpen AND k.kinderAddress LIKE %:address%")
    Page<Kinder> findByKinderNameContainingAndKinderWeekendOpenAndKinderAddressContaining(String kinderName, String kinderWeekendOpen, String address, Pageable pageable);

    // 11. 검색값 + 야간 + 지역구
    @Query(value = "SELECT k FROM Kinder k LEFT JOIN FETCH Location l ON l.kinder = k WHERE k.kinderName LIKE %:kinderName% AND k.kinderNightOpen = :kinderNightOpen AND k.kinderAddress LIKE %:address%",
            countQuery = "SELECT COUNT(k) FROM Kinder k WHERE k.kinderName LIKE %:kinderName% AND k.kinderNightOpen = :kinderNightOpen AND k.kinderAddress LIKE %:address%")
    Page<Kinder> findByKinderNameContainingAndKinderNightOpenAndKinderAddressContaining(String kinderName, String kinderNightOpen, String address, Pageable pageable);

}
