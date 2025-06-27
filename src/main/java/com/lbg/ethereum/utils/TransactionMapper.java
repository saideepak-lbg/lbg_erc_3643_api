package com.lbg.ethereum.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbg.ethereum.entities.transactions.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class TransactionMapper {
    private TransactionMapper(){

    }

    public static Transaction GenerateTransactionFromTransactionReceipt(TransactionReceipt txReceipt) throws JsonProcessingException {
        Transaction transaction = new Transaction();
        transaction.setBlockHash(txReceipt.getBlockHash());
        transaction.setBlockNumber(txReceipt.getBlockNumber().longValue());
        transaction.setContractAddress(txReceipt.getContractAddress());
        transaction.setFromAddress(txReceipt.getFrom());
        transaction.setLogs(txReceipt.getLogs().toString());
        transaction.setTransactionIndex(txReceipt.getTransactionIndex().intValue());
        transaction.setTransactionHash(txReceipt.getTransactionHash());
        transaction.setRoot(txReceipt.getRoot());
        transaction.setGasUsed(txReceipt.getGasUsed().longValue());
        transaction.setStatus(txReceipt.getStatus());
        transaction.setLogsBloom(txReceipt.getLogsBloom());
        transaction.setRevertReason(txReceipt.getRevertReason());
        transaction.setStatusOk(txReceipt.isStatusOK());
        transaction.setToAddress(txReceipt.getTo());
        transaction.setCumulativeGasUsed(txReceipt.getCumulativeGasUsed().longValue());
        return transaction;
    }

}
