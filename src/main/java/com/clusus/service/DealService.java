package com.clusus.service;


import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;

import java.util.List;

public interface DealService {
    DealDto save(Deal deal);

    List<Deal> saveDealList(List<Deal> dealList);
}
