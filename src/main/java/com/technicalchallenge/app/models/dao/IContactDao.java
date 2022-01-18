package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IContactDao extends JpaRepository<Contacts, Long>, JpaSpecificationExecutor<Contacts> {
    List<Contacts> findContactsByContact_id(Long customerId);
}
