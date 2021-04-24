package com.clusus.util;

import com.clusus.dto.DealDto;
import com.clusus.dto.ErrorResponse;
import com.clusus.repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class DealValidatorTest {

    DealValidator dealValidator;

    @Mock
    private DealRepository dealRepository;

    @BeforeEach
    void setUp() {
        dealValidator = new DealValidator();
    }

    @Test
    public void name() {
        List<DealDto> dealDtoList = new ArrayList<>();
        dealDtoList.add(new DealDto("SN01", "INR", "NPR", "2021-04-24 10:25:15", ""));
        dealDtoList.add(new DealDto("SN02", "", "NPR", "20-04-2021 10:25:15", "3434"));
        dealDtoList.add(new DealDto("SN03", "INR", "NPR", "2021-04-24 10:25:15", "0"));
        dealDtoList.add(new DealDto("SN04", "ZPI", "PQI", "2021-04-24 10:25:15", "1223"));
        Map<Integer, Object> errorMap = dealValidator.validateDeals(dealDtoList);
        List<ErrorResponse> errorResponses = (List<ErrorResponse>) errorMap.get(0);
        when(dealRepository.findByDealId(anyString())).thenReturn(null);
        assertEquals("Deal with invalid amount", errorResponses.get(2).getErrorMessage());
    }

    private InputStream asStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}
