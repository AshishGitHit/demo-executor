package com.example.executor.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User_tbl")
public class UserNew {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    private String gender;
}
