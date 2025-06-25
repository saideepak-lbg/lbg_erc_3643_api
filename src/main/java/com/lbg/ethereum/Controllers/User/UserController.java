package com.lbg.ethereum.Controllers.User;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create-onchain-identity")
    public CreateOnChainIdResponse createOnChainIdResponse(@RequestBody OnChainIdCreationDto onChainIdCreationDto){
        return userService.createOnChainId(onChainIdCreationDto);
    }

    @PostMapping("/register-identity")
    public ResponseEntity<RegisterIdentityReponseDto> registerIdentity(@RequestBody RegisterIdentityDto registerIdentityDto) {
        return userService.registerIdentity(registerIdentityDto);
    }

    @PostMapping("/get-user-claims")
    public ResponseEntity<GetUserClaimsResponseDto> getUserClaims(@RequestBody UserClaimDto userClaimDto) {
        return userService.getUserClaims(userClaimDto);
    }
}
