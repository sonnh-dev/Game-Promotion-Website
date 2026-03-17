package com.dxlab.gamepromotionweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "media", schema = "gpw_db")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Size(max = 50)
    @NotNull
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Size(max = 50)
    @NotNull
    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;

    @NotNull
    @Column(name = "entity_id", nullable = false)
    private Integer entityId;


}