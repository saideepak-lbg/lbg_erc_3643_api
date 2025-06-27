package com.lbg.ethereum.entities.users;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false,unique = true)
    private String userName;

    @Column(nullable = false)
    private String userRole;

    @Column(nullable = false, unique = true)
    private String walletKey;

    @Column(nullable = false)
    private Boolean isFrozen = false;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getWalletKey() {
        return walletKey;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
    }

    public Boolean getFrozen() {
        return isFrozen;
    }

    public void setFrozen(Boolean frozen) {
        isFrozen = frozen;
    }
}