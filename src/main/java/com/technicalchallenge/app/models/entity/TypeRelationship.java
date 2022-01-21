package com.technicalchallenge.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="TypeRelationship")
public class TypeRelationship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long typeRelationshipId;

    @NotBlank
    private String name;

    public TypeRelationship() {
    }

    public TypeRelationship(Long typeRelationshipId, String name) {
        this.typeRelationshipId = typeRelationshipId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTypeRelationshipId() {
        return typeRelationshipId;
    }

    public void setTypeRelationshipId(Long typeRelationshipId) {
        this.typeRelationshipId = typeRelationshipId;
    }
}
