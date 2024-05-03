package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "demo")
@Data
public class DemoEntity {
    @Id
    private Long id;
    private String greeting;
}
