package com.clusus.mapper;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DealMapper {
    DealMapper INSTANCE = Mappers.getMapper(DealMapper.class);

    @Mapping(source = "dealDto.dealTime", target = "dealTime", dateFormat = "yyyy-MM-dd hh:mm:ss")
    Deal toEntity(DealDto dealDto);

    @Mapping(source = "deal.dealTime", target = "dealTime", dateFormat = "yyyy-MM-dd hh:mm:ss")
    DealDto toDto(Deal deal);

    @Mapping(source = "dealDto.dealTime", target = "dealTime", dateFormat = "yyyy-MM-dd hh:mm:ss")
    List<Deal> toEntityList(List<DealDto> dealDtoList);
}
