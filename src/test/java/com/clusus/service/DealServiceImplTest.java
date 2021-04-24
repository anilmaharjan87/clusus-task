package com.clusus.service;

import com.clusus.dto.DealDto;
import com.clusus.entity.Deal;
import com.clusus.exceptions.DealAlreadyExistException;
import com.clusus.repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DealServiceImplTest {
    @Autowired
    DealServiceImpl dealService;
    @MockBean
    DealRepository dealRepository;
    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext.getBeansOfType(CrudRepository.class).values().forEach(CrudRepository::deleteAll);
    }

    @Test
    void testSaveMethodWithNewDeal() {
        Date date = getDate("2021-04-26 10:11:15");
        Deal deal = new Deal(1L, "SN99", "NPR", "INR", date, new BigDecimal(1234));
        when(dealRepository.save(isA(Deal.class))).thenReturn(deal);
        DealDto dealDto = new DealDto("SN99", "NPR", "INR", "2021-04-26 10:11:15", "1234");
        assertEquals(dealDto, dealService.save(new Deal()));
    }

    @Test
    void testIfRecordAlreadyExist() {
        Deal deal = new Deal(1L, "SN02", "test", "test", new Timestamp(System.currentTimeMillis()), new BigDecimal(123));
        when(dealRepository.findByDealId("SN02")).thenReturn(deal);
        assertThrows(DealAlreadyExistException.class, () -> dealService.save(deal));
    }

    private Date getDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
