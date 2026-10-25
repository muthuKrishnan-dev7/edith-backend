package com.backend.Products.Catalog.Attributes.AttributeValues;

import com.backend.Products.Catalog.Attributes.AttributeType.AttributeTypeEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "attribute_values")
public class AttributeValuesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_type_id", nullable = false)
    private AttributeTypeEntity attributeType; // Table relation from AttributeType

    @Column(nullable = false, length = 50)
    private String label;          // "M", "1kg", "Black"

    private Integer sortOrder;     // controls S,M,L,XL display order ---
    // label="S",  sortOrder=1
    //label="M",  sortOrder=2
    //label="L",  sortOrder=3
}
