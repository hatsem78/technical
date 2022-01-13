package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICountryDao extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {
}