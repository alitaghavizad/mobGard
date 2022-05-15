package com.internal.mobileSearch.backend.da.model;

public enum MobileStatus {
    EXISTING(1),
    ACTIVE(2),
    INACTIVE(0);

    private int mobileStatus;

    MobileStatus(int status) {
        this.mobileStatus = status;
    }

    public int getStatus() {
        return mobileStatus;
    }
}
