package com.mitocode.matriculas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SecurityUser")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SecurityUser {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String username;

    @Field
    private String password;

    @Field
    private boolean status;

    @Field
    private List<SecurityRole> securityRoles;
}
