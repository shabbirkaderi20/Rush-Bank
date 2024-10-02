package com.rush.banking.userservice.entity;

public enum Authority {

    ADMINISTRATOR("administrator"),
    MASTER_CLIENT("master_client"),
    CLIENT("client");

    final String authority;

    Authority(String authority) {
        this.authority= authority;
    }
}
