package com.moh.yehia.cognito.controller;

import com.moh.yehia.cognito.config.CognitoProperties;
import com.moh.yehia.cognito.model.AuthResponse;
import com.moh.yehia.cognito.model.UserLoginRequest;
import com.moh.yehia.cognito.model.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/users")
public class UserController {
    private final CognitoIdentityProviderClient cognitoIdentityProviderClient;
    private final CognitoProperties cognitoProperties;

    @PostMapping("/signup")
    public String signup(@RequestBody UserSignupRequest userSignupRequest) {
        log.info("UserController :: signup :: start");
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .userAttributes(populateUserAttributes(userSignupRequest))
                .username(userSignupRequest.email())
                .password(userSignupRequest.password())
                .clientId(cognitoProperties.getClientId())
                .build();

        SignUpResponse signUpResponse = cognitoIdentityProviderClient.signUp(signUpRequest);
        log.info("signUpResponse =>{}", signUpResponse.toString());

        return "User signup successfully!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("UserController :: login :: start");
        Map<String, String> authParameters = new HashMap<>();
        authParameters.put("USERNAME", userLoginRequest.email());
        authParameters.put("PASSWORD", userLoginRequest.password());

        InitiateAuthRequest initiateAuthRequest = InitiateAuthRequest.builder()
                .clientId(cognitoProperties.getClientId())
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .authParameters(authParameters)
                .build();

        InitiateAuthResponse initiateAuthResponse = cognitoIdentityProviderClient.initiateAuth(initiateAuthRequest);
        log.info("initiateAuthResponse =>{}", initiateAuthResponse);
        AuthenticationResultType authenticationResultType = initiateAuthResponse.authenticationResult();
        return new AuthResponse(authenticationResultType.accessToken(), authenticationResultType.expiresIn(), authenticationResultType.tokenType(), authenticationResultType.refreshToken(), authenticationResultType.idToken());
    }

    private List<AttributeType> populateUserAttributes(UserSignupRequest userSignupRequest) {
        AttributeType emailAttributeType = AttributeType.builder()
                .name("email")
                .value(userSignupRequest.email())
                .build();
        AttributeType phoneAttributeType = AttributeType.builder()
                .name("phone_number")
                .value(userSignupRequest.phone())
                .build();
        AttributeType nameAttributeType = AttributeType.builder()
                .name("name")
                .value(userSignupRequest.name())
                .build();
        AttributeType preferredUsernameAttributeType = AttributeType.builder()
                .name("preferred_username")
                .value(userSignupRequest.username())
                .build();
        return Arrays.asList(emailAttributeType, phoneAttributeType, nameAttributeType, preferredUsernameAttributeType);
    }
}
