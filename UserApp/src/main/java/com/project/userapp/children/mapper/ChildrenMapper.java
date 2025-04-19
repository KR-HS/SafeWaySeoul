package com.project.userapp.children.mapper;

import com.project.userapp.command.ChildrenVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChildrenMapper {
    int registChild(ChildrenVO vo);
    void registKinderRelation(int childKey);
    int deleteChild(Integer childKey);
    List<ChildrenVO> myChildren(Integer parentKey);

}
