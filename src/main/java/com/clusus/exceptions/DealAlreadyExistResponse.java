package com.clusus.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DealAlreadyExistResponse {
    private String dealId;

    public DealAlreadyExistResponse(String dealId) {
        this.dealId = dealId;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }
}
