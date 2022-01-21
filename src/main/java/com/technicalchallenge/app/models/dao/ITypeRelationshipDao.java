package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.TypeRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ITypeRelationshipDao extends JpaRepository<TypeRelationship, Long>, JpaSpecificationExecutor<TypeRelationship> {
    TypeRelationship findByName(String name);
}
