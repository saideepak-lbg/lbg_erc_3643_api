package com.lbg.ethereum.Controllers.Token;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import java.io.IOException;

@RestController()
@RequestMapping("/token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @Autowired
    Web3j web3j;

    @PostMapping("/create-onchain-identity")
    public CreateOnChainIdResponse createOnChainIdResponse(@RequestBody OnChainIdCreationDto onChainIdCreationDto){
       return tokenService.createOnChainId(onChainIdCreationDto);
    }

    @PostMapping("/register-identity")
    public ResponseEntity<RegisterIdentityReponseDto> registerIdentity(@RequestBody RegisterIdentityDto registerIdentityDto) {
        // This method is a placeholder. Implement the logic to register an identity.
        return tokenService.registerIdentity(registerIdentityDto);
    }

    @PostMapping("/get-user-claims")
    public ResponseEntity<GetUserClaimsResponseDto> getUserClaims(@RequestBody UserClaimDto userClaimDto) {
        // This method is a placeholder. Implement the logic to retrieve user claims.
        return tokenService.getUserClaims(userClaimDto);
    }

    @PostMapping("/add-claim")
    public ResponseEntity<AddClaimResponseDto> addClaim(@RequestBody AddClaimDto addClaimDto) {
        // This method is a placeholder. Implement the logic to add a claim.
        return tokenService.addClaim(addClaimDto);
    }

    @GetMapping("/check-testnet-connection")
    public String checkTestnetConnection() throws IOException {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        if(web3ClientVersion.hasError()) {
            return "Error connecting to testnet: " + web3ClientVersion.getError().getMessage();
        }

        return "Testnet connection is successful!"+web3ClientVersion.getWeb3ClientVersion();
    }

    @PostMapping("/add-claim-topic")
    public ResponseEntity<AddClaimTopicReponseDto> addClaimTopic(@RequestBody AddClaimTopicDto addClaimTopicDto) {

        return tokenService.addClaimTopic(addClaimTopicDto);
    }

    @PostMapping("/remove-claim-topic")
    public ResponseEntity<RemoveClaimTopicResponseDto> removeClaimTopic(@RequestBody RemoveClaimTopicDto removeClaimTopicDto) {
        return tokenService.removeClaimTopic(removeClaimTopicDto);
    }

    @PostMapping("/get-claim-topics")
    public ResponseEntity<GetClaimTopicsResponseDto> getClaimTopics(@RequestBody GetClaimTopicsDto getClaimTopicsDto) {

        return tokenService.getClaimTopics(getClaimTopicsDto);
    }

    @PostMapping("/add-trusted-issuer-claim-topic")
    public ResponseEntity<AddTrustedIssuerClaimTopicResponseDto> addTrustedIssuerClaimTopic(@RequestBody AddTrustedIssuerClaimTopicsDto addTrustedIssuerClaimTopicDto) {
        return tokenService.addTrustedIssuerClaimTopic(addTrustedIssuerClaimTopicDto);
    }

    @PostMapping("/update-trusted-issuer-claim-topic")
    public ResponseEntity<UpdateTrustedIssuerClaimTopicResponseDto> updateTrustedIssuerClaimTopic(@RequestBody UpdateTrustedIssuerClaimTopicsDto updateTrustedIssuerClaimTopicsDto) {
        return tokenService.updateTrustedIssuerClaimTopic(updateTrustedIssuerClaimTopicsDto);
    }

    @PostMapping("/get-user-tokens")
    public ResponseEntity<GetUserTokenResponseDto> getUserTokenBalance(@RequestBody GetUserTokensDto getUserTokensDto) {
        return tokenService.getUserTokenBalance(getUserTokensDto);
    }

    @PostMapping("/approve-user-tokens-for-transfer")
    public ResponseEntity<ApproveUserTokensForTransferResponse> approveUserTokensForTransfer(@RequestBody ApproveUserTokensForTransferDto approveUserTokensForTransferDto) {
        return tokenService.approveUserTokensForTransfer(approveUserTokensForTransferDto);
    }

    @PostMapping("/transfer-approved-tokens")
    public ResponseEntity<TransferApprovedTokensResponseDto> transferApprovedTokens(@RequestBody TransferApprovedTokensDto transferApprovedTokensDto) {
        return tokenService.transferApprovedTokens(transferApprovedTokensDto);
    }

    @PostMapping("/transfer-tokens")
    public ResponseEntity<TransferTokensReponseDto> transferTokens(@RequestBody TransferTokensDto transferTokensDto) {
        return tokenService.transferTokens(transferTokensDto);
    }
    @PostMapping("/freeze-token")
    public ResponseEntity<FreezeTokenResponseDto> freezeToken(@RequestBody FreezeTokensDto freezeTokensDto) {
        return tokenService.freezeToken(freezeTokensDto);
    }
    @PostMapping("/unfreeze-tokens")
    public ResponseEntity<UnFreezeTokenResponseDto> unFreezeToken(@RequestBody UnFreezeTokensDto unFreezeTokensDto) {
        return tokenService.unFreezeToken(unFreezeTokensDto);
    }

    @PostMapping("/pause-token")
    public ResponseEntity<PauseTokenResponseDto> pauseToken(@RequestBody PauseTokenDto pauseTokenDto) {
        return tokenService.pauseToken(pauseTokenDto);
    }

    @PostMapping("/unpause-token")
    public ResponseEntity<UnPauseTokenResponseDto> unPauseToken(@RequestBody UnPauseTokenDto unPauseTokenDto) {
        return tokenService.unPauseToken(unPauseTokenDto);
    }

    @PostMapping("/recover-account")
    public ResponseEntity<RecoverAccountResponseDto> recoverAccount(@RequestBody RecoverAccountDto recoverAccountDto) {
        return tokenService.recoverAccount(recoverAccountDto);
    }

    @PostMapping("/burn-tokens")
    public ResponseEntity<BurnTokensResponseDto> burnTokens(@RequestBody BurnTokensDto burnTokensDto) {
        return tokenService.burnTokens(burnTokensDto);
    }

    @PostMapping("/get-token-details")
    public ResponseEntity<TokenDetailsResponseDto> getTokenDetails(@RequestBody TokenDetailsRequestDto tokenDetailsRequestDto) {
        return tokenService.getTokenDetails(tokenDetailsRequestDto);
    }

    @PostMapping("/mint-tokens")
    public ResponseEntity<MintTokenResponseDto> mintTokens(@RequestBody MintTokensDto mintTokensDto) {
        return tokenService.mintTokens(mintTokensDto);
    }

    @PostMapping("/freeze-account")
    public ResponseEntity<FreezeAccountResponseDto> freezeAccount(@RequestBody FreezeAccountDto freezeAccountDto) {
        return tokenService.freezeAccount(freezeAccountDto);
    }

    @PostMapping("/unfreeze-account")
    public ResponseEntity<UnfreezeAccountResponseDto> unFreezeAccount(@RequestBody UnFreezeAccountDto unFreezeAccountDto) {
        // This method is a placeholder. Implement the logic to unfreeze an account.
        return tokenService.unFreezeAccount(unFreezeAccountDto);
    }

    @PostMapping("/add-key")
    public ResponseEntity<AddKeyResponseDto> addKey(@RequestBody AddkeyDto addkeyDto) {

        return tokenService.addKey(addkeyDto);
    }

}
