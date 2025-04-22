package com.project.userapp.kinder.service;

import com.project.userapp.command.KinderVO;
import com.project.userapp.entity.Kinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KinderService {
    List<KinderVO> getKinderList();
    void registKinderAPI();

    Page<Kinder> getKindersByName(String kinderName, int page, int size);
    Page<Kinder> getKindersByWeekendOpen(String kinderWeekendOpen, int page, int size);
    Page<Kinder> getKindersByNightOpen(String kinderNightOpen, int page, int size);
    Page<Kinder> getKindersByAddress(String address, int page, int size);
    Page<Kinder> getAllKinders(int page, int size);

    Page<Kinder> getKindersByNameAndWeekend(String name, String week, int page, int size);
    Page<Kinder> getKindersByNameAndNight(String name, String night, int page, int size);
    Page<Kinder> getKindersByNameAndAddress(String name, String address, int page, int size);
    Page<Kinder> getKindersByWeekendAndAddress(String week, String address, int page, int size);
    Page<Kinder> getKindersByNightAndAddress(String night, String address, int page, int size);
    Page<Kinder> getKindersByNameAndWeekendAndAddress(String name, String week, String address, int page, int size);
    Page<Kinder> getKindersByNameAndNightAndAddress(String name, String night, String address, int page, int size);
}
