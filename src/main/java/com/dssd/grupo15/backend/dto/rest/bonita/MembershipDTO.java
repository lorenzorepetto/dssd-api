package com.dssd.grupo15.backend.dto.rest.bonita;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MembershipDTO {
    @JsonProperty("role_id")
    private String roleId;

    public MembershipDTO() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
