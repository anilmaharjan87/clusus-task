package com.clusus.service;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.exceptions.DealAlreadyExistException;
import com.clusus.repository.DealRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        if (dealRepository.findByDealId(deal.getDealId()) != null) {
            logger.error("Deal with deal id {} already exists", deal.getDealId());
            throw new DealAlreadyExistException("Deal with deal id " + deal.getDealId() + " already exist");
        }
        Deal savedDeal = dealRepository.save(deal);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(savedDeal, DealDto.class);
    }

    @Override
    public void saveDealList(List<Deal> dealList) {
        if (!dealList.isEmpty()) {
            for (Deal deal : dealList) {
                if (dealRepository.findByDealId(deal.getDealId()) != null) {
                    logger.error("Deal with deal id {} already exists", deal.getDealId());
                    throw new DealAlreadyExistException("Deal with deal id " + deal.getDealId() + " already exist");
                }
                dealRepository.save(deal);
            }
        } else {
            logger.warn("Deal list is empty");
        }
    }
}
