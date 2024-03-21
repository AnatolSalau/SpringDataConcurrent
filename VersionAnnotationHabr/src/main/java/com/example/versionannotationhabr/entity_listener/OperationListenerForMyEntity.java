package com.example.versionannotationhabr.entity_listener;

import com.example.versionannotationhabr.entity.MyEntity;
import jakarta.persistence.*;

public class OperationListenerForMyEntity {
    @PostLoad
    public void postLoad(MyEntity obj) {
        System.out.println("Loaded operation: " + obj);
    }

    @PrePersist
    public void prePersist(MyEntity obj) {
        System.out.println("Pre-Persistiting operation: " + obj);
    }

    @PostPersist
    public void postPersist(MyEntity obj) {
        System.out.println("Post-Persist operation: " + obj);
    }

    @PreRemove
    public void preRemove(MyEntity obj) {
        System.out.println("Pre-Removing operation: " + obj);
    }

    @PostRemove
    public void postRemove(MyEntity obj) {
        System.out.println("Post-Remove operation: " + obj);
    }

    @PreUpdate
    public void preUpdate(MyEntity obj) {
        System.out.println("Pre-Updating operation: " + obj);
    }

    @PostUpdate
    public void postUpdate(MyEntity obj) {
        System.out.println("Post-Update operation: " + obj);
    }
}