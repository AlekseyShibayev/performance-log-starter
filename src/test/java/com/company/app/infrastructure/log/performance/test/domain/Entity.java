package com.company.app.infrastructure.log.performance.test.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Accessors(chain = true)
@javax.persistence.Entity
@Table(name = "entity")
public class Entity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

}
