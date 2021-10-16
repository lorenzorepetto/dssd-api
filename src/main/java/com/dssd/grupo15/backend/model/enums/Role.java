package com.dssd.grupo15.backend.model.enums;

public enum Role {
    APODERADO("1"),
    MESA_ENTRADAS("2"),
    LEGALES("3");

    private final String roleId;

    Role(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public static Role getByRoleId(String roleId) {
        for (Role r : values()) {
            if (r.getRoleId().equals(roleId)) {
                return r;
            }
        }
        return null;
    }
}
