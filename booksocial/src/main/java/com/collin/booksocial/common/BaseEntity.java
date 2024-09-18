package com.collin.booksocial.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BaseEntity is a base class for JPA entities, providing common fields and audit functionality.
 * The class includes fields for ID, creation date, last modification date, created by,
 * and last modified by. These fields are automatically managed and populated by JPA and
 * Spring Data JPA's auditing features.
 *
 * Annotations used:
 * - @Getter and @Setter: Lombok annotations for generating getter and setter methods.
 * - @SuperBuilder: Lombok annotation for generating a builder pattern.
 * - @AllArgsConstructor: Lombok annotation for generating a constructor with all fields.
 * - @NoArgsConstructor: Lombok annotation for generating a no-args constructor.
 * - @MappedSuperclass: Specifies that this class is a base class for other JPA entities.
 * - @EntityListeners: Specifies that this class should be used with JPA entity listeners, in this case, the AuditingEntityListener.
 *
 * ID: Primary key for the entity, generated automatically.
 * createdDate: Timestamp indicating when the entity was created.
 * lastModifiedDate: Timestamp indicating the last time the entity was modified.
 * createdBy: Identifier for the user or service that created the entity.
 * lastModifiedBy: Identifier for the user or service that last modified the entity.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * ID is the primary key for the entity.
     * The value is generated automatically.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Timestamp indicating when the entity was created.
     * This field is automatically populated by JPA's auditing feature and cannot be updated.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    /**
     * Timestamp indicating the last time the entity was modified.
     *
     * This field is automatically populated by JPA and Spring Data JPA's auditing
     * features whenever the entity is updated. It uses the @LastModifiedDate annotation
     * to mark it as the field that should be updated with the current date and time
     * during an update operation. The @Column annotation indicates that this field
     * is not insertable, meaning it will not be populated during entity creation.
     */
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    /**
     * Identifier for the user or service that created the entity.
     * This field is automatically populated by the JPA auditing feature
     * and is not updatable following the initial creation.
     *
     * Annotations:
     * - @CreatedBy: Specifies that this field should be populated
     *   with the identifier of the entity creator.
     * - @Column(nullable = false, updatable = false): Marks the database
     *   column as non-nullable and non-updatable.
     */
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    /**
     * Identifier for the user or service that last modified the entity.
     *
     * This field is automatically populated by Spring Data JPA's auditing feature
     * and should not be inserted manually. It is primarily used for tracking and
     * auditing purposes.
     *
     * Annotations used:
     * - @LastModifiedBy: Indicates that this field will be populated with the identifier of the user or service that last modified the entity.
     * - @Column(insertable = false): Specifies that this column should not be included in SQL INSERT statements.
     */
    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;
}