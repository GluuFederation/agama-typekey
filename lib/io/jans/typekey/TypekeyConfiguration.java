package io.jans.typekey;

import java.util.List;
import java.util.Map;

public class TypekeyConfiguration {
    private String keystoreName;
    private String keystorePassword;

    private String clientId;
    private String clientSecret;

    private String authHost;
    private String scanHost;
    private String orgId;

    public String getKeystoreName() {
        return keystoreName;
    }

    public void setKeystoreName(String keystore) {
        this.keystoreName = keystore;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String password) {
        this.keystorePassword = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String id) {
        this.clientId = id;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String secret) {
        this.clientSecret = secret;
    }

    public String getAuthHost() {
        return authHost;
    }

    public void setAuthHost(String host) {
        this.authHost = host;
    }

    public String getScanHost() {
        return scanHost;
    }

    public void setScanHost(String host) {
        this.scanHost = host;
    }
    
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String id) {
        this.orgId = id;
    }

}
