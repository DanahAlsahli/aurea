package org.example.aurea.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Column(length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 100)
    private String industry;

    @Column(length = 30)
    private String status;
}
