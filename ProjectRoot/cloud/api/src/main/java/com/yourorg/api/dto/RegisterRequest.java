package com.yourorg.api.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String nrTelefon;
    private String tipUtilizator;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }   

    public String getNrTelefon() {
        return nrTelefon;
    }

    public String getTipUtilizator() {
        return tipUtilizator;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public void setTipUtilizator(String tipUtilizator) {

    } 
    
}