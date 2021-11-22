package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.dto.rest.bonita.*;
import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.dto.rest.response.LoginResponseDTO;
import com.dssd.grupo15.backend.exception.InvalidCredentialsException;
import com.dssd.grupo15.backend.exception.common.GenericException;
import com.dssd.grupo15.backend.model.SociedadAnonima;
import com.dssd.grupo15.backend.model.enums.Role;
import com.dssd.grupo15.backend.model.enums.StatusEnum;
import com.dssd.grupo15.backend.utils.BonitaApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class BonitaApiService {

    @Value("${bonita.login.url}")
    private String LOGIN_URL;
    @Value("${bonita.logout.url}")
    private String LOGOUT_URL;
    @Value("${bonita.user.id.url}")
    private String USER_ID_URL;
    @Value("${bonita.user.role.url}")
    private String USER_ROLE_URL;
    @Value("${bonita.user.membership.url}")
    private String USER_MEMBERSHIP_URL;
    @Value("${bonita.process.definition.info.url}")
    private String PROCESS_DEFINITION_INFO_URL;
    @Value("${bonita.process.init.url}")
    private String INIT_PROCESS_URL;
    @Value("${bonita.process.task.search}")
    private String TASK_SEARCH;
    @Value("${bonita.process.task.update}")
    private String TASK_UPDATE;


    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(BonitaApiService.class);

    public BonitaApiService() {
        this.restTemplate = new RestTemplate();
    }

    public LoginResponseDTO login(CredentialsDTO credentialsDTO) throws InvalidCredentialsException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", credentialsDTO.getUsername());
        map.add("password", credentialsDTO.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> authResponse = restTemplate.postForEntity(LOGIN_URL, request, String.class);
            LoginResponseDTO loginResponseDTO = BonitaApiUtils.getTokenDTOFromCookies(authResponse);
            Role role = this.getRoleInfo(loginResponseDTO, credentialsDTO);
            if (role == null) {
                throw new InvalidCredentialsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Bad credentials!")
                        .build());
            }
            loginResponseDTO.setRole(role.name());
            return loginResponseDTO;
        } catch (HttpClientErrorException | GenericException e) {
            throw new InvalidCredentialsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Bad credentials!")
                    .build());
        }
    }

    public String initBonitaProcess(String processName, List<VariableDTO> variables, String token, String sessionId) throws GenericException {
        ProcessDefinitionInfoDTO processInfo = this.getProcessInfo(processName, sessionId);
        try {
            HttpEntity<InitProcessResponseDTO> res = restTemplate.exchange(
                    INIT_PROCESS_URL,
                    HttpMethod.POST,
                    BonitaApiUtils.getEntityWithHeadersAndBody(token, sessionId, new InitProcessDTO(processInfo.getId(), variables)),
                    new ParameterizedTypeReference<>() {
                    });
            return res.getBody().getId();
        } catch (Exception e) {
            throw new GenericException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Internal Server Error")
                    .build());
        }
    }

    public void updateTask(SociedadAnonima sociedadAnonima,
                           String newStatus,
                           CredentialsDTO credentialsDTO,
                           List<VariableDTO> variables,
                           String token,
                           String sessionId)
            throws GenericException {
        // obtener id de usuario
        String userId = this.getUserIdByUsername(credentialsDTO, token, sessionId);
        // asignar tarea (obtener tarea por proceso, asignar tarea)
        String taskId = this.assignTask(sociedadAnonima.getProcessId(), userId, token, sessionId);

        // completar tarea con variables
        this.completeTask(taskId, token, sessionId, variables);

    }

    private void completeTask(String taskId, String token, String sessionId, List<VariableDTO> variables) {
        UriComponentsBuilder taskUpdateBuilder = UriComponentsBuilder.fromHttpUrl(TASK_UPDATE)
                .path(String.format("/%s", taskId));

        restTemplate.exchange(
                taskUpdateBuilder.toUriString(),
                HttpMethod.PUT,
                BonitaApiUtils.getEntityWithHeadersAndBody(token,
                        sessionId,
                        CompleteTaskDTO.Builder.aCompleteTaskDTO().state("completed").variables(variables).build()),
                new ParameterizedTypeReference<>() {
                });
    }

    private String assignTask(String processId, String userId, String token, String sessionId) {
        // obtener id de tarea por proceso
        String urlTaskSearch = TASK_SEARCH + "?f=caseId=" + processId;
        HttpEntity<List<IdDTO>> taskSearchResponse = restTemplate.exchange(
                urlTaskSearch,
                HttpMethod.GET,
                BonitaApiUtils.getEntityWithHeaders(token, sessionId),
                new ParameterizedTypeReference<>() {
                });

        // hacer el put a la tarea
        UriComponentsBuilder taskUpdateBuilder = UriComponentsBuilder.fromHttpUrl(TASK_UPDATE)
                .path(String.format("/%s", taskSearchResponse.getBody().get(0).getId()));

        restTemplate.exchange(
                taskUpdateBuilder.toUriString(),
                HttpMethod.PUT,
                BonitaApiUtils.getEntityWithHeadersAndBody(token, sessionId, new AssignTaskDTO(Long.parseLong(userId))),
                new ParameterizedTypeReference<>() {
                });

        return taskSearchResponse.getBody().get(0).getId();
    }


    private String getUserIdByUsername(CredentialsDTO credentialsDTO, String token, String sessionId) throws GenericException {
        String urlUserId = USER_ID_URL + "?f=userName=" + credentialsDTO.getUsername();
        try {
            // Get user id by username
            HttpEntity<List<IdDTO>> userIdResponse = restTemplate.exchange(
                    urlUserId,
                    HttpMethod.GET,
                    BonitaApiUtils.getEntityWithHeaders(token, sessionId),
                    new ParameterizedTypeReference<>() {
                    });

            if (userIdResponse.getBody() == null || userIdResponse.getBody().isEmpty()) {
                throw new InvalidCredentialsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Bad credentials!")
                        .build());
            }

            return userIdResponse.getBody().get(0).getId();
        } catch (IndexOutOfBoundsException e) {
            logger.warn(String.format("Failed to retrieve user info for username %s", credentialsDTO.getUsername()));
            throw new GenericException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(String.format("Failed to retrieve user info for username %s", credentialsDTO.getUsername()))
                    .build());
        }
    }

    private Role getRoleInfo(LoginResponseDTO loginResponseDTO, CredentialsDTO credentialsDTO) throws GenericException {
        try {
            // Get user id by username
            String userId = this.getUserIdByUsername(credentialsDTO, loginResponseDTO.getToken(), loginResponseDTO.getSessionId());

            // Get user membership
            String urlMembership = USER_MEMBERSHIP_URL + "?f=user_id=" + userId;
            HttpEntity<List<MembershipDTO>> userMembershipResponse = restTemplate.exchange(
                    urlMembership,
                    HttpMethod.GET,
                    BonitaApiUtils.getEntityWithHeaders(loginResponseDTO.getToken(), loginResponseDTO.getSessionId()),
                    new ParameterizedTypeReference<>() {
                    });

            // Get role by user id
            UriComponentsBuilder userRoleBuilder = UriComponentsBuilder.fromHttpUrl(USER_ROLE_URL)
                    .path(String.format("/%s", userMembershipResponse.getBody().get(0).getRoleId()));

            HttpEntity<RoleIdDTO> userRoleResponse = restTemplate.exchange(
                    userRoleBuilder.toUriString(),
                    HttpMethod.GET,
                    BonitaApiUtils.getEntityWithHeaders(loginResponseDTO.getToken(), loginResponseDTO.getSessionId()),
                    new ParameterizedTypeReference<>() {
                    });

            if (userRoleResponse.getBody() == null) {
                throw new InvalidCredentialsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Bad credentials!")
                        .build());
            }

            return Role.getByRoleId(userRoleResponse.getBody().getId());
        } catch (IndexOutOfBoundsException e) {
            logger.warn(String.format("Failed to retrieve user info for username %s", credentialsDTO.getUsername()));
            throw new GenericException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(String.format("Failed to retrieve user info for username %s", credentialsDTO.getUsername()))
                    .build());
        }
    }

    private ProcessDefinitionInfoDTO getProcessInfo(String processName, String sessionId) throws GenericException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PROCESS_DEFINITION_INFO_URL)
                .queryParam("s", processName);

        try {
            HttpEntity<List<ProcessDefinitionInfoDTO>> res = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    BonitaApiUtils.getEntityWithHeaders("", sessionId),
                    new ParameterizedTypeReference<>() {
                    });
            return res.getBody().get(0);
        } catch (HttpClientErrorException e) {
            if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())) {
                throw new InvalidCredentialsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Bad credentials!")
                        .build());
            }
            throw new GenericException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Internal Server Error")
                    .build());
        } catch (IndexOutOfBoundsException e) {
            logger.warn(String.format("Failed to retrieve info for process %s", processName));
            throw new GenericException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(String.format("Failed to retrieve info for process %s", processName))
                    .build());
        }
    }

    public Map<String, Object> logout(String token, String sessionId) {
        HttpEntity<String> response = restTemplate.exchange(
                LOGOUT_URL,
                HttpMethod.GET,
                BonitaApiUtils.getEntityWithHeaders(token, sessionId),
                new ParameterizedTypeReference<>() {
                });

        return Map.of("ok", "true", "message", "Logged out");
    }
}
