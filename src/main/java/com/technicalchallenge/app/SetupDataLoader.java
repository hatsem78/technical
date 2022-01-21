package com.technicalchallenge.app;

import com.technicalchallenge.app.models.dao.ICountryDao;
import com.technicalchallenge.app.models.dao.IDocumentTypeDao;
import com.technicalchallenge.app.models.dao.ITypeRelationshipDao;
import com.technicalchallenge.app.models.entity.Country;
import com.technicalchallenge.app.models.entity.DocumentType;
import com.technicalchallenge.app.models.entity.TypeRelationship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Configuration
class LoadDatabase {

    @Autowired
    CommandLineRunner initDatabase(ICountryDao countryDao, ITypeRelationshipDao typeRelationshipDao, IDocumentTypeDao documentTypeDao) {
        final Country ar = createCountryIfNotFound(countryDao, 1L, "Argentina", "ar");
        final Country uy = createCountryIfNotFound(countryDao,2L, "Uruguay", "uy");
        final Country py = createCountryIfNotFound(countryDao,3L, "Paraguay", "py");
        final Country bo = createCountryIfNotFound(countryDao,4L, "Bolivia", "bo");
        final Country br = createCountryIfNotFound(countryDao,5L, "Brasil", "br");
        final Country co = createCountryIfNotFound(countryDao,6L, "Colombia", "co");
        final Country cr = createCountryIfNotFound(countryDao,7L, "Costa Rica", "cr");
        final Country hn = createCountryIfNotFound(countryDao,8L, "Honduras", "hn");

        final TypeRelationship father = typeRelationshipIfNotFound(typeRelationshipDao,1L, "Father");
        final TypeRelationship mother = typeRelationshipIfNotFound(typeRelationshipDao,2L, "Mother");
        final TypeRelationship siter = typeRelationshipIfNotFound(typeRelationshipDao,3L, "Sister");
        final TypeRelationship brother = typeRelationshipIfNotFound(typeRelationshipDao,4L, "Brother");
        final TypeRelationship uncle = typeRelationshipIfNotFound(typeRelationshipDao,5L, "Uncle");


        final DocumentType dni = documentTypeIfNotFound(documentTypeDao,1L, "Documento de identidad", "dni");
        final DocumentType pas = documentTypeIfNotFound(documentTypeDao,2L, "Pasaporte", "pas");
        final DocumentType ci = documentTypeIfNotFound(documentTypeDao,3L, "Cedula de identidad", "ci");

        return null;
    }


    @Transactional
    Country createCountryIfNotFound(ICountryDao countryDao, final long id, final String description, final String code) {
        Country country = countryDao.findByDescription(description);
        if (country == null) {
            country = new Country(id, description, code);
            countryDao.save(country);
        }
        return country;
    }

    @Transactional
    TypeRelationship typeRelationshipIfNotFound(ITypeRelationshipDao typeRelationshipDao, final long id, final String name) {
        TypeRelationship relationship = typeRelationshipDao.findByName(name);
        if (relationship == null) {
            relationship = new TypeRelationship(id, name);
            typeRelationshipDao.save(relationship);
        }
        return relationship;
    }

    @Transactional
    DocumentType documentTypeIfNotFound(IDocumentTypeDao documentTypeDao, final long id, final String name, String code) {
        DocumentType relationship = documentTypeDao.findDocumentTypeByCode(name);
        if (relationship == null) {
            relationship = new DocumentType(id, name, code);
            documentTypeDao.save(relationship);
        }
        return relationship;
    }
}