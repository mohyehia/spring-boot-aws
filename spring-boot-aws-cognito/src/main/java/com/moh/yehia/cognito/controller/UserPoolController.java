package com.moh.yehia.cognito.controller;

import com.moh.yehia.cognito.model.UserPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUserPoolsRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUserPoolsResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pools")
@Log4j2
@RequiredArgsConstructor
public class UserPoolController {
    private final CognitoIdentityProviderClient cognitoIdentityProviderClient;

    @GetMapping
    public List<UserPool> listUserPools() {
        log.info("UserController :: listUserPools :: start");
        ListUserPoolsResponse listUserPoolsResponse = cognitoIdentityProviderClient.listUserPools(ListUserPoolsRequest.builder()
                .maxResults(5)
                .build());
        List<UserPool> userPools = new ArrayList<>();
        listUserPoolsResponse.userPools()
                .forEach(userPoolDescriptionType -> userPools.add(new UserPool(userPoolDescriptionType.id(),
                        userPoolDescriptionType.name(),
                        userPoolDescriptionType.creationDate().toString(),
                        userPoolDescriptionType.statusAsString())
                ));
        return userPools;
    }
}
