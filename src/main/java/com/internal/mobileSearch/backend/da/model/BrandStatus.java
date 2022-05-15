package com.internal.mobileSearch.backend.da.model;

public enum BrandStatus {
    EXISTING(1),
    ACTIVE(2),
    INACTIVE(0);

    private int brandStatus;

    BrandStatus(int status) {
        this.brandStatus = status;
    }

    public int getStatus() {
        return brandStatus;
    }
}
