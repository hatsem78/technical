package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IDocumentTypeDao extends JpaRepository<DocumentType, Long>, JpaSpecificationExecutor<DocumentType> {
}