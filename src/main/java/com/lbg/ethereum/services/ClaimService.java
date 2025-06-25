package com.lbg.ethereum.services;

import com.lbg.ethereum.DTOs.*;
import org.springframework.http.ResponseEntity;

public interface ClaimService {

    ResponseEntity<AddClaimResponseDto> addClaim(AddClaimDto addClaimDto);
    ResponseEntity<AddClaimTopicReponseDto> addClaimTopic(AddClaimTopicDto addClaimTopicDto);
    ResponseEntity<RemoveClaimTopicResponseDto> removeClaimTopic(RemoveClaimTopicDto removeClaimTopicDto);
    ResponseEntity<GetClaimTopicsResponseDto> getClaimTopics(GetClaimTopicsDto getClaimTopicsDto);
    ResponseEntity<AddTrustedIssuerClaimTopicResponseDto> addTrustedIssuerClaimTopic(AddTrustedIssuerClaimTopicsDto addTrustedIssuerClaimTopicDto);
    ResponseEntity<UpdateTrustedIssuerClaimTopicResponseDto> updateTrustedIssuerClaimTopic(UpdateTrustedIssuerClaimTopicsDto updateTrustedIssuerClaimTopicsDto);


}
