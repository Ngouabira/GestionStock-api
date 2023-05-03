package com.gildas.gestionstock.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
public class AppConfiguration {

    private String appname;
    private String email;
    private String frontLink;
    private String jwtSecret;
    private String jwtExpirationMs;

    public AppConfiguration() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFrontLink() {
        return frontLink;
    }

    public void setFrontLink(String frontLink) {
        this.frontLink = frontLink;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public void setJwtExpirationMs(String jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
}
