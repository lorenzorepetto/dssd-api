package com.dssd.grupo15.backend.dto.rest.bonita;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssignTaskDTO {
    @JsonProperty("assigned_id")
    private Long assignedId;

    public AssignTaskDTO() {
    }

    public AssignTaskDTO(Long assignedId) {
        this.assignedId = assignedId;
    }

    public Long getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(Long assignId) {
        this.assignedId = assignedId;
    }
}
