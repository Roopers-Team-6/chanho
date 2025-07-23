package com.loopers.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserGender {
    M("male"),
    F("female");

    private final String value;

    UserGender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static UserGender fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        for (UserGender userGender : UserGender.values()) {
            if (userGender.value.equalsIgnoreCase(value)) {
                return userGender;
            }
        }

        throw new IllegalArgumentException(value + " is not a valid value for UserGender.");
    }
}
