package com.technicalchallenge.app.response;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Null;

@Entity
@Data
@Accessors(chain=true)
public class StatisticsResponse {


    @Id
    private Long countWoman;
    @Null
    private Long countMen;
    @Null
    private Long argentinePercentage;

    public StatisticsResponse() {
    }

    public StatisticsResponse(
            Long countWoman,
            Long countMen,
            Long argentinePercentage
    ) {
        this.countWoman = countWoman;
        this.countMen = countMen;
        this.argentinePercentage = argentinePercentage;

    }

    public Long getCountWoman() {
        return countWoman;
    }

    public void setCountWoman(Long countWoman) {
        this.countWoman = countWoman;
    }

    public Long getCountMen() {
        return countMen;
    }

    public void setCountMen(Long countMen) {
        this.countMen = countMen;
    }

    public Long getArgentinePercentage() {
        return argentinePercentage;
    }

    public void setArgentinePercentage(Long argentinePercentage) {
        this.argentinePercentage = argentinePercentage;
    }
}
