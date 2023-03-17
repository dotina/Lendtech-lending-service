package com.lendtech.mslendingservice.configs.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfiguration {

    @Value("${twilio.account_id}")
    private String accountId;
    @Value("${twilio.auth_token}")
    private String authToken;
    @Value("${twilio.trial_number}")
    private String trialNumber;

    public TwilioConfiguration() {
    }

    public TwilioConfiguration(String accountSid, String authToken, String trialNumber) {
        this.accountId = accountSid;
        this.authToken = authToken;
        this.trialNumber = trialNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(String trialNumber) {
        this.trialNumber = trialNumber;
    }
}
