package com.codeuz.service;


import com.codeuz.entity.SmsHistoryEntity;
import com.codeuz.exp.AppBadException;
import com.codeuz.repository.SmsHistoryRepository;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class SmsService {

    @Autowired
    private TokensService tokensService;

    private final SmsHistoryRepository smsHistoryRepository;
    @Value("${sms.url}")
    private String smsUrl;
    @Value("${my.eskiz.uz.email}")
    private String myEskizUzEmail;
    @Value("${my.eskiz.uz.password}")
    private String myEskizUzPassword;

    public SmsService(SmsHistoryRepository smsHistoryRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
    }

    String currentToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTkyMTkxMjEsImlhdCI6MTcxNjYyNzEyMSwicm9sZSI6InRlc3QiLCJzaWduIjoiYWRmODk1MDJhZDAyYjBlNDJjNTgwYTNiYmE3NmMyNGQwNjlhYWRmMTQ5NWY2N2Y1ZmEwNjc5OTBlMTE4YjU4NiIsInN1YiI6Ijc0MDIifQ.JsGEGpML-svM4ZJe0C3v4vp49zoCWcm6W_MJFrmrx4s";


    //Main send method
    public String sendSms(String phone) {
        //String code = RandomUtil.getRandomSmsCode();
        //String message = " Verify code: " + code;
        String code = "This is test from Eskiz";
        String message = "This is test from Eskiz";  //Kontent provayder tomonidan berilgan test uchun sms
        send(phone, message);
        saveSmsHistory(phone, code, message);
        return "Code sent successfully";
    }


    // Sms sending method
    private void send(String phone, String message) {
        String bearer = "Bearer ";
        if (!tokensService.checkSmsToken(currentToken)){
            String newToken = getToken();
            tokensService.saveToken(newToken);
            currentToken = newToken;
        }
        String prPhone = phone;
        if (prPhone.startsWith("+")) {
            prPhone = prPhone.substring(1);
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", prPhone)
                .addFormDataPart("message", message)
                .addFormDataPart("from", "4546")
                .build();

        Request request = new Request.Builder()
                .url(smsUrl + "api/message/sms/send")
                .method("POST", body)
                .header("Authorization", bearer + currentToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    // Sms history save method
    public void saveSmsHistory(String phone, String code, String message){
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phone);
        entity.setMessage(message);
        entity.setCode(code);
        smsHistoryRepository.save(entity);
    }


    // Check sms code
    public void isNotExpiredSms(String phone){
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findByPhone(phone);
        if (optional.isEmpty()){
            throw new AppBadException("Phone not found");
        }
        SmsHistoryEntity entity = optional.get();
        if (entity.getCreatedDate().plusMinutes(3).isBefore(LocalDateTime.now())){
            throw new AppBadException("Confirmation time expired");
        }
    }


    // Check sms limit
    public void checkSmsLimit(String phone){
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(1);
        long count = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone, from, to);
        if (count >= 3){
            throw new AppBadException("Sms limit reached. Please try after some time");
        }
    }


    // Get token method
    private String getToken() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("email", myEskizUzEmail)
                .addFormDataPart("password", myEskizUzPassword)
                .build();
        Request request = new Request.Builder()
                .url(smsUrl + "api/auth/login")
                .method("POST", body)
                .build();
        try {
            Response response;
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException();
            } else {
                JSONObject object = new JSONObject(response.body().string());
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }




}
