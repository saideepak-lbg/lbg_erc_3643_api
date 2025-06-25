package com.lbg.ethereum.Controllers.claim;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.services.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/add-claim")
    public ResponseEntity<AddClaimResponseDto> addClaim(@RequestBody AddClaimDto addClaimDto) {
        return claimService.addClaim(addClaimDto);
    }

    @PostMapping("/add-claim-topic")
    public ResponseEntity<AddClaimTopicReponseDto> addClaimTopic(@RequestBody AddClaimTopicDto addClaimTopicDto) {

        return claimService.addClaimTopic(addClaimTopicDto);
    }

    @PostMapping("/remove-claim-topic")
    public ResponseEntity<RemoveClaimTopicResponseDto> removeClaimTopic(@RequestBody RemoveClaimTopicDto removeClaimTopicDto) {
        return claimService.removeClaimTopic(removeClaimTopicDto);
    }

    @PostMapping("/get-claim-topics")
    public ResponseEntity<GetClaimTopicsResponseDto> getClaimTopics(@RequestBody GetClaimTopicsDto getClaimTopicsDto) {

        return claimService.getClaimTopics(getClaimTopicsDto);
    }

    @PostMapping("/add-trusted-issuer-claim-topic")
    public ResponseEntity<AddTrustedIssuerClaimTopicResponseDto> addTrustedIssuerClaimTopic(@RequestBody AddTrustedIssuerClaimTopicsDto addTrustedIssuerClaimTopicDto) {
        return claimService.addTrustedIssuerClaimTopic(addTrustedIssuerClaimTopicDto);
    }

    @PostMapping("/update-trusted-issuer-claim-topic")
    public ResponseEntity<UpdateTrustedIssuerClaimTopicResponseDto> updateTrustedIssuerClaimTopic(@RequestBody UpdateTrustedIssuerClaimTopicsDto updateTrustedIssuerClaimTopicsDto) {
        return claimService.updateTrustedIssuerClaimTopic(updateTrustedIssuerClaimTopicsDto);
    }

}
