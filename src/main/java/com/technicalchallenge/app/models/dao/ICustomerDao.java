package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.Customers;
import com.technicalchallenge.app.response.StatisticsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ICustomerDao extends JpaRepository<Customers, Long> {

    @Query(value="SELECT  new com.technicalchallenge.app.response.StatisticsResponse(" +
            " sum(case when a.gender = 'M' then 1 else 0 end)," +
            " sum(case when a.gender = 'f' then 1 else 0 end)," +
            " round(sum(case when a.country = 1 then 1 else 0 end) / count(a.id)  * 100, 2) ) " +
            " from Customers a join Country b on(a.country =b.id)"
    )
    StatisticsResponse getStadistic();


}
