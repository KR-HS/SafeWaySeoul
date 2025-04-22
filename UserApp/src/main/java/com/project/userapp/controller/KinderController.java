package com.project.userapp.controller;

import com.project.userapp.entity.Kinder;
import com.project.userapp.kinder.service.KinderService;
import com.project.userapp.util.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kinder")
public class KinderController {

    @Autowired
    private KinderService kinderService;

    @GetMapping("/find")
    public PageDTO<Kinder> getKinderList(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(name="name",required = false) String name,
            @RequestParam(name="week",required = false) String week,
            @RequestParam(name="night",required = false) String night,
            @RequestParam(name="address",required = false) String address
    )
    {
        Page<Kinder> getPage=null;

        page=page-1;
        // 1. name만 있음
        if (name != null && week == null && night == null && address == null) {
             getPage = kinderService.getKindersByName(name, page, size);
        } // 2. week만있음
        else if (name == null && week != null && night == null && address == null) {
            getPage = kinderService.getKindersByWeekendOpen(week, page, size);
        } // 3. night만 있음
        else if (name == null && week == null && night != null && address == null) {
            getPage =  kinderService.getKindersByNightOpen(night, page, size);
        } // 4. address만 있음
        else if (name == null && week == null && night == null && address != null) {
            getPage =  kinderService.getKindersByAddress(address, page, size);
        } // 5. name+week
        else if (name != null && week != null && night == null && address == null) {
            getPage =  kinderService.getKindersByNameAndWeekend(name, week, page, size);
        } // 6. name + night
        else if (name != null && week == null && night != null && address == null) {
            getPage =  kinderService.getKindersByNameAndNight(name, night, page, size);
        } // 7. name + address
        else if (name != null && week == null && night == null && address != null) {
            getPage =  kinderService.getKindersByNameAndAddress(name, address, page, size);
        } // 8. week + address
        else if (name == null && week != null && night == null && address != null) {
            getPage =  kinderService.getKindersByWeekendAndAddress(week,address,page,size);
        } // 9. night + address
        else if (name == null && week == null && night != null && address != null) {
            getPage =  kinderService.getKindersByNightAndAddress(night,address,page,size);
        } // 10. name + week + address
        else if (name != null && week != null && night == null && address != null) {
            getPage =  kinderService.getKindersByNameAndWeekendAndAddress(name ,week,address,page,size);
        } // 11. name + night + address
        else if (name != null && week == null && night != null && address != null) {
            getPage =  kinderService.getKindersByNameAndNightAndAddress(name ,night,address,page,size);
        } // 12. 전체 조회
        else{
            getPage =  kinderService.getAllKinders(page, size);
        }
        
        PageDTO<Kinder> pageDTO =new PageDTO<>(getPage);
        return pageDTO;
        // 조건 안걸려있을떄(모든 리스트)
    }

}
