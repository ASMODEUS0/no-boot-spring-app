package org.aston.model.entity.base;

import java.io.Serializable;

public interface  EntityBase<T extends Serializable> {

    T getId();
}
