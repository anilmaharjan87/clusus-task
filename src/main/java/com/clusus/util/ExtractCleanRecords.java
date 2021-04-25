package com.clusus.util;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.mapper.DealMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class ExtractCleanRecords {
    public  static List<Deal> getCleanRecord(List<DealDto> csvRecords, Map<Integer, Object> invalidCSVRowMap) {
        List<Deal> cleanRecords = new ArrayList<>();
        for (int i = 0; i < csvRecords.size(); i++) {
            if (!invalidCSVRowMap.containsKey(i)) {
                cleanRecords.add(DealMapper.INSTANCE.toEntity(csvRecords.get(i)));
            }
        }
        return cleanRecords;
    }
}
