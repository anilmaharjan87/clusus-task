package com.clusus.repository;

import com.clusus.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
    Deal findByDealId(String dealId);
}
