package com.clusus.service;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.exceptions.DealAlreadyExistException;
import com.clusus.mapper.DealMapper;
import com.clusus.repository.DealRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealServiceImpl implements DealService {
    private static final Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);
    private final DealRepository dealRepository;

    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public DealDto save(Deal deal) {
        checkIfDealAlreadyExist(deal);
        Deal savedDeal = dealRepository.save(deal);
        DealDto dealDto = DealMapper.INSTANCE.toDto(savedDeal);
        return dealDto;
    }

    @Override
    public List<Deal> saveDealList(List<Deal> dealList) {
        List<Deal> saveDeals = new ArrayList<>();
        if (!dealList.isEmpty()) {
            for (Deal deal : dealList) {
                checkIfDealAlreadyExist(deal);
                Deal savedDeal = dealRepository.save(deal);
                saveDeals.add(savedDeal);
            }
        } else {
            logger.warn("Deal list is empty");
        }
        return saveDeals;
    }

    private void checkIfDealAlreadyExist(Deal deal) {
        if (dealRepository.findByDealId(deal.getDealId()) != null) {
            logger.error("Deal with deal id {} already exists", deal.getDealId());
            throw new DealAlreadyExistException("Deal with deal id " + deal.getDealId() + " already exist");
        }
    }
}
