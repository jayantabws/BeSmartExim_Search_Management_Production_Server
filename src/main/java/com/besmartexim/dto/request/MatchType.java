package com.besmartexim.dto.request;

import java.io.Serializable;

public enum MatchType implements Serializable{
	
	C,
	L;
	public String getValue() {
        return this.name();
    }

}
