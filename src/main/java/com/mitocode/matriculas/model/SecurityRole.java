package com.mitocode.matriculas.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SecurityRole")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SecurityRole {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String name;
}
