package com.clusus.util;

import com.clusus.dto.DealDto;
import com.clusus.dto.ErrorResponse;
import com.clusus.entity.Deal;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DealValidatorTest {
    @Autowired
    DealValidator dealValidator;
    @Autowired
    private ApplicationContext applicationContext;
    @MockBean
    private DealRepository dealRepository;

    @BeforeEach
    void setUp() {
        applicationContext.getBeansOfType(CrudRepository.class).values().forEach(CrudRepository::deleteAll);
    }

    @Test
    public void testDataValidationWithDifferentInvalidData() {
        List<DealDto> dealDtoList = new ArrayList<>();
        dealDtoList.add(new DealDto("", "INR", "NPR", "2021-04-24 10:25:15", ""));
        dealDtoList.add(new DealDto("SN02", "", "NPR", "20-04-2021 10:25:15", "3434"));
        dealDtoList.add(new DealDto("SN03", "INR", "NPR", "2021-04-24", "0"));
        dealDtoList.add(new DealDto("SN04", "Z12I", "PQI3", "2021-04-24 10:25:15", "1223"));
        when(dealRepository.findByDealId("SN02")).thenReturn(new Deal(1L, "SN02", "test", "test", new Timestamp(System.currentTimeMillis()), new BigDecimal(123)));
        Map<Integer, Object> errorMap = dealValidator.validateDeals(dealDtoList);
        assertEquals("Deal with invalid deal id", ((List<ErrorResponse>) errorMap.get(0)).get(0).getErrorMessage());
        assertEquals("Deal with invalid amount", ((List<ErrorResponse>) errorMap.get(0)).get(1).getErrorMessage());
        assertEquals("Deal with invalid amount", ((List<ErrorResponse>) errorMap.get(0)).get(1).getErrorMessage());
        assertEquals("Deal with given deal id  already exists", ((List<ErrorResponse>) errorMap.get(1)).get(0).getErrorMessage());
        assertEquals("Deal with invalid from ISO currency code", ((List<ErrorResponse>) errorMap.get(1)).get(1).getErrorMessage());
        assertEquals("Deal with invalid date format", ((List<ErrorResponse>) errorMap.get(2)).get(0).getErrorMessage());
        assertEquals("Deal with  amount less than or zero", ((List<ErrorResponse>) errorMap.get(2)).get(1).getErrorMessage());
        assertEquals("Deal with invalid from ISO currency code", ((List<ErrorResponse>) errorMap.get(3)).get(0).getErrorMessage());
        assertEquals("Deal with invalid to ISO currency code", ((List<ErrorResponse>) errorMap.get(3)).get(1).getErrorMessage());
    }
}
