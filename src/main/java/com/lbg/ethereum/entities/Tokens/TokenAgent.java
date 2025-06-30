package com.lbg.ethereum.entities.Tokens;

import com.lbg.ethereum.entities.users.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "token_agent", schema = "ethereum")
public class TokenAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "agent_name", length = 100)
    private String agentName;

    @ManyToOne
    @JoinColumn(name = "agent_user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity agentUser;


    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public UserEntity getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(UserEntity agentUser) {
        this.agentUser = agentUser;
    }
}
