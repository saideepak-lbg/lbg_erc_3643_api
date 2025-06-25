package com.lbg.ethereum.entities.transactions;

import com.lbg.ethereum.enums.TransactionType;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.web3j.protocol.core.methods.response.Log;

import java.util.List;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "transaction_hash", length = 100)
    private String transactionHash;

    @Column(name = "transaction_index")
    private Integer transactionIndex;

    @Column(name = "block_hash", length = 100)
    private String blockHash;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "cumulative_gas_used")
    private Long cumulativeGasUsed;

    @Column(name = "gas_used")
    private Long gasUsed;

    @Column(name = "contract_address", length = 100)
    private String contractAddress;

    @Column(name = "root", length = 100)
    private String root;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "from_address", length = 100)
    private String fromAddress;

    @Column(name = "to_address", length = 100)
    private String toAddress;


    @Column(name = "logs", columnDefinition = "text")
    private String logs; // Store JSON as String, or use a JSON type handler if available

    @Column(name = "logs_bloom", columnDefinition = "text")
    private String logsBloom;

    @Column(name = "revert_reason", columnDefinition = "text")
    private String revertReason;

    @Column(name = "status_ok")
    private Boolean statusOk;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 20)
    private TransactionType transactionType;

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Integer getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(Integer transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Long getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public void setCumulativeGasUsed(Long cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public Long getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(Long gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    public String getRevertReason() {
        return revertReason;
    }

    public void setRevertReason(String revertReason) {
        this.revertReason = revertReason;
    }

    public Boolean getStatusOk() {
        return statusOk;
    }

    public void setStatusOk(Boolean statusOk) {
        this.statusOk = statusOk;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}