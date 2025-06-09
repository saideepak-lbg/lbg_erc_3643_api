package com.lbg.ethereum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {


    private final Environment environment;

    public Web3jConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Web3j web3j() {
    String infuriaRpcUrl = this.environment.getProperty("LOCAL_RPC_URL");
        return Web3j.build(new HttpService(infuriaRpcUrl));
    }
}
