package com.example.websocketserver.mapper;

import com.example.websocketserver.dto.LocationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jmx.export.annotation.ManagedOperation;

@Mapper
public interface LocationMapper {
    void insertLocation(LocationDTO location);
}
