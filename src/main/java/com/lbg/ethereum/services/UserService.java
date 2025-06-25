package com.lbg.ethereum.services;

import com.lbg.ethereum.DTOs.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    CreateOnChainIdResponse createOnChainId(OnChainIdCreationDto onChainIdCreationDto);
    ResponseEntity<RegisterIdentityReponseDto> registerIdentity(RegisterIdentityDto registerIdentityDto);

    ResponseEntity<GetUserClaimsResponseDto> getUserClaims(UserClaimDto userClaimDto);
}
