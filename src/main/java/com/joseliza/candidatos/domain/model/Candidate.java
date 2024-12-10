package com.joseliza.candidatos.domain.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    private Integer id;
    private String name;
    private String email;
    private String gender;
    private Double expectedSalary;
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
    private Integer rowstate = 1;
    private Date lastupdate;
}
