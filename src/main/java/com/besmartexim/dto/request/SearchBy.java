package com.besmartexim.dto.request;

import java.io.Serializable;

public enum SearchBy implements Serializable{
    HS_CODE,
    HS_CODE_2,
    PRODUCT,
    IMPORTER,
    EXPORTER;
    public String getValue() {
        return this.name();
    }
}
