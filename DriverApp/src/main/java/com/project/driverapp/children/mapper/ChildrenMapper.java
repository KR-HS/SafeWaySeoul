package com.project.driverapp.children.mapper;

import com.project.driverapp.command.ChildrenVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChildrenMapper {
    List<ChildrenVO> findByKinder(String postCode, String address);

}
