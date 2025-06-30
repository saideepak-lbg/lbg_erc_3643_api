package com.lbg.ethereum.services;

import com.lbg.ethereum.DTOs.*;

import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface TokenService {

    ResponseEntity<GetUserTokenResponseDto> getUserTokenBalance(GetUserTokensDto getUserTokensDto);
    ResponseEntity<ApproveUserTokensForTransferResponse> approveUserTokensForTransfer(ApproveUserTokensForTransferDto approveUserTokensForTransferDto);
    ResponseEntity<TransferTokensReponseDto> transferTokens(TransferTokensDto transferTokensDto);
    ResponseEntity<FreezeTokenResponseDto>  freezeToken(FreezeTokensDto freezeTokensDto);
    ResponseEntity<UnFreezeTokenResponseDto> unFreezeToken(UnFreezeTokensDto unFreezeTokensDto);
    ResponseEntity<PauseTokenResponseDto> pauseToken(PauseTokenDto pauseTokensDto);
    ResponseEntity<UnPauseTokenResponseDto> unPauseToken(UnPauseTokenDto unPauseTokenDto);
    ResponseEntity<BurnTokensResponseDto> burnTokens(BurnTokensDto burnTokensDto);
    ResponseEntity<TransferApprovedTokensResponseDto> transferApprovedTokens(TransferApprovedTokensDto transferApprovedTokensDto);
    ResponseEntity<TokenDetailsResponseDto> getTokenDetails(TokenDetailsRequestDto tokenDetailsRequestDto);
    ResponseEntity<MintTokenResponseDto> mintTokens(MintTokensDto mintTokensDto);

    ResponseEntity<RecoverAccountResponseDto> recoverAccount(RecoverAccountDto recoverAccountDto);
    ResponseEntity<FreezeAccountResponseDto>   freezeAccount(FreezeAccountDto freezeAccountDto);
    ResponseEntity<UnfreezeAccountResponseDto> unFreezeAccount(UnFreezeAccountDto unFreezeAccountDto);

    ResponseEntity<AddKeyResponseDto> addKey(AddkeyDto addkeyDto);
}
