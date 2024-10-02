package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.entity.PhoneNumberOtpRequest;
import com.rush.banking.userservice.exceptions.PhoneNumberNotVerifiedException;
import com.rush.banking.userservice.service.SmsSenderServiceFacade;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Service("SmsSenderServiceFacade")
public class SmsSenderServiceFacadeImpl implements SmsSenderServiceFacade {

    @Override
    public PhoneNumberOtpRequest sendSms(Long phoneNumber, String otp) {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        } catch (GeneralSecurityException e) {
            System.out.println(e.getMessage());
        }

        try {
            StringBuilder sendString = new StringBuilder();
            String username = "john";
            String password = "Xc3ffs";
            String messageType = "SMS:TEXT";
            String httpUrl = "https://127.0.0.1:9508/";
            String recipient = URLEncoder.encode("+91"+ phoneNumber, StandardCharsets.UTF_8);
            String messageData = URLEncoder.encode("TestMessage", StandardCharsets.UTF_8);

            sendString.append(httpUrl).append("api?action=sendmessage").
                    append("&username=").append(username).append("&password=").
                    append(password).append("&recipient=").append(recipient).
                    append("&messagetype=").append(messageType).append("&messagedata=").
                    append(messageData);

            System.out.println("Sending request: " + sendString);

            URL url = new URL(sendString.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = null;
            System.out.println("Http response received: ");
            if (con.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String strCurrentLine;
                while ((strCurrentLine = br.readLine()) != null) {
                    System.out.println(strCurrentLine);
                }
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String strCurrentLine;
                while ((strCurrentLine = br.readLine()) != null) {
                    System.out.println(strCurrentLine);
                }
            }

            PhoneNumberOtpRequest phoneNumberOtpRequest= new PhoneNumberOtpRequest();
                phoneNumberOtpRequest.setPhoneNumber(phoneNumber);
                phoneNumberOtpRequest.setOtp(otp);

            return phoneNumberOtpRequest;

        } catch (Exception ex) {
            throw new PhoneNumberNotVerifiedException();
        }
    }
}
