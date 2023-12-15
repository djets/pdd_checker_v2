package ru.djets.webclient.dao.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class VersionedAbstractEntity<Id extends Serializable> extends AbstractEntity<Id> {

    @Version
    @Column(name = "object_version", nullable = false)
    Long version;
}
