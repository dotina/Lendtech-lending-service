package com.lendtech.mslendingservice.service;

import com.lendtech.mslendingservice.configs.sms.TwilioConfiguration;
import com.lendtech.mslendingservice.models.pojo.SmsRequest;
import com.lendtech.mslendingservice.utilities.LogManager;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lendtech.mslendingservice.utilities.GlobalVariables.*;
import static com.lendtech.mslendingservice.utilities.Utilities.generateTrackingID;
import static com.lendtech.mslendingservice.utilities.Utilities.parseToJsonString;

@Service
public class SmsService {
    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public SmsService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void sendSms(SmsRequest smsRequest) {
        try{

            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);

            creator.create();
            LogManager.info(generateTrackingID(), TRANSACTION_TYPE, "sendingSMSViaTwilio", "",
                    smsRequest.getPhoneNumber(), "InSerVice", TARGET_SYSTEM_DB, RESPONSE_SUCCESSFUL,
                    RESPONSE_CODE_200, RESPONSE_SUCCESS, "", parseToJsonString(smsRequest),
                    "", "");
        }catch(Exception e){
            LogManager.error(generateTrackingID(), TRANSACTION_TYPE, "sendingSMSViaTwilioError", "", smsRequest.getPhoneNumber()
                    , SOURCE, "TWILIO-SMSC", RESPONSE_FAILED,
                    RESPONSE_CODE_500, RESPONSE_FAILED, e.getMessage(), parseToJsonString(smsRequest), e.getLocalizedMessage(), "");
        }
    }
}
