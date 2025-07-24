package com.besmartexim.dto.request;

import java.io.Serializable;

public enum TreadType implements Serializable{
    IMPORT,
    EXPORT;
    public String getValue() {
        return this.name();
    }
}
