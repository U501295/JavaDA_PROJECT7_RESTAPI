package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rule_name")
@Getter
@Setter
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_name_id")
    private Long ruleNameId;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false, name = "name")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Column(nullable = false, name = "description")
    private String description;

    @NotBlank(message = "Json is mandatory")
    @Column(nullable = false, name = "json")
    private String json;

    @NotBlank(message = "Template is mandatory")
    @Column(nullable = false, name = "template")
    private String template;

    @NotBlank(message = "Sql_str is mandatory")
    @Column(nullable = false, name = "sql_str")
    private String sqlStr;

    @NotBlank(message = "Sql_part is mandatory")
    @Column(nullable = false, name = "sql_part")
    private String sqlPart;


    public RuleName() {

    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
