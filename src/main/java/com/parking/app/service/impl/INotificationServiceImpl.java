/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.service.impl;

import com.parking.app.service.INotificationService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Service;

/**
 * @author Administrador
 */
@Service
public class INotificationServiceImpl implements INotificationService {

    public static final String REST_API_KEY = "YWMyZThiY2EtMjQxZi00NDI3LWI5MjktZTVjZTNmZmI5NmEz";
    public static final String APP_ID = "a8bfffde-6941-4d3c-a34b-f79015b49b07";
    public static final String LEN = "en";
    public static final String URL = "https://onesignal.com/api/v1/notifications";

    @Override
    public void sendMessageToAllUsers(String title, String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean sendMessageToUser(String title, String message, String userId) {

        Boolean r = false;

        try {
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", REST_API_KEY);
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    + "\"app_id\": \"" + APP_ID + "\","
                    + "\"include_player_ids\": [\"" + userId + "\"],"
                    + "\"contents\": {\"en\": \"" + message + "\"},"
                    + "\"headings\": {\"en\": \"" + title + "\"}"
                    + "}";

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();

            if (httpResponse == 200) {
                r = true;
            }

        } catch (IOException t) {
            r = false;
        }

        return r;

    }

    private Map<String, String> message(String message) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(LEN, message);
        return map;
    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        } else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }
}
