package com.technicalchallenge.app.models.services;

import com.technicalchallenge.app.controller.CustumerRestController;
import com.technicalchallenge.app.exceptionscustom.CustomersCustomException;
import com.technicalchallenge.app.models.dao.ICustomersRelationshipDao;
import com.technicalchallenge.app.models.entity.CustomersRelationship;
import com.technicalchallenge.app.response.ResponseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomersRelationshipServiceImpl implements ICustomersRelationship {

    private static final Logger logger = LoggerFactory.getLogger(CustumerRestController.class);

    @Autowired
    private ICustomersRelationshipDao customersRelationshipDao;

    @Override
    public List<CustomersRelationship> findAll() {
        return null;
    }

    @Override
    public CustomersRelationship save(CustomersRelationship customersRelationship) {
        return customersRelationshipDao.save(customersRelationship);
    }

    @Override
    public CustomersRelationship update(CustomersRelationship customers) {
        return customersRelationshipDao.save(customers);
    }

    @Override
    public ResponseEntity<ResponseRequest> delete(long customersRelationship) {
        try {
            customersRelationshipDao.deleteById(customersRelationship);
        } catch (Exception e) {
            logger.error("customersRelationshipDao delete - Exception {}", e.getMessage());
            throw new CustomersCustomException("customersRelationship-Delete - no exist customersRelationship id:: " + customersRelationship);
        }
        return null;
    }

    @Override
    public CustomersRelationship find(Long customers) {
        return null;
    }
}
