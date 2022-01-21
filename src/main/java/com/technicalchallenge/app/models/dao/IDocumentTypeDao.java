package com.technicalchallenge.app.models.dao;

import com.technicalchallenge.app.models.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IDocumentTypeDao extends JpaRepository<DocumentType, Long>, JpaSpecificationExecutor<DocumentType> {
    DocumentType findDocumentTypeByCode(String code);
    Optional<DocumentType> findDocumentTypeByDocumentTypeId(Long documentTypeId);
}