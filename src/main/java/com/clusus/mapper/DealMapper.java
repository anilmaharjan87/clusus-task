package com.clusus.mapper;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DealMapper {
    DealMapper INSTANCE = Mappers.getMapper(DealMapper.class);

    @Mapping(source = "dealDto.dealTime", target = "dealTime", dateFormat = "yyyy-MM-dd hh:mm:ss")
    Deal toEntity(DealDto dealDto);
}
