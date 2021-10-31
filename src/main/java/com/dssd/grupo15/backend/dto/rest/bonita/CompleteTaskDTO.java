package com.dssd.grupo15.backend.dto.rest.bonita;

import java.util.List;

public class CompleteTaskDTO {
    private String state;
    private List<VariableDTO> variables;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<VariableDTO> getVariables() {
        return variables;
    }

    public void setVariables(List<VariableDTO> variables) {
        this.variables = variables;
    }

    public static final class Builder {
        private CompleteTaskDTO completeTaskDTO;

        private Builder() {
            completeTaskDTO = new CompleteTaskDTO();
        }

        public static Builder aCompleteTaskDTO() {
            return new Builder();
        }

        public Builder state(String state) {
            completeTaskDTO.setState(state);
            return this;
        }

        public Builder variables(List<VariableDTO> variables) {
            completeTaskDTO.setVariables(variables);
            return this;
        }

        public CompleteTaskDTO build() {
            return completeTaskDTO;
        }
    }
}
