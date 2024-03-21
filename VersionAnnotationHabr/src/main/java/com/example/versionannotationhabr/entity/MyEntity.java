package com.example.versionannotationhabr.entity;

import com.example.versionannotationhabr.entity_listener.OperationListenerForMyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(OperationListenerForMyEntity.class)
@Entity
public class MyEntity{
    @Version
    private long version;

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String value;
  
    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                ", version=" + version +
                ", value='" + value + '\'' +
                '}';
    }
}