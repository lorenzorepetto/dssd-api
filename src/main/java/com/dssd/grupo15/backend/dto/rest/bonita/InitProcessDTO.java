package com.dssd.grupo15.backend.dto.rest.bonita;

import java.util.List;

public class InitProcessDTO {
    private String processDefinitionId;
    private List<VariableDTO> variables;

    public InitProcessDTO() {
    }

    public InitProcessDTO(String processDefinitionId, List<VariableDTO> variables) {
        this.processDefinitionId = processDefinitionId;
        this.variables = variables;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public List<VariableDTO> getVariables() {
        return variables;
    }

    public void setVariables(List<VariableDTO> variables) {
        this.variables = variables;
    }
}
