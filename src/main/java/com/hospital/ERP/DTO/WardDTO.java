package com.hospital.ERP.DTO;

import com.hospital.ERP.Entity.Ward;

public class WardDTO {

    private String wardName;
    private Ward.WardType wardType;
    private int totalBeds;
    private int availableBeds;

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public Ward.WardType getWardType() {
        return wardType;
    }

    public void setWardType(Ward.WardType wardType) {
        this.wardType = wardType;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }
}
