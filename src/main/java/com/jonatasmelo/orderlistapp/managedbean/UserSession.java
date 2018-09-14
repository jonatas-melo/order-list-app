package com.jonatasmelo.orderlistapp.managedbean;

import com.google.gson.Gson;
import com.jonatasmelo.orderlistapp.request.LoginRequest;
import com.jonatasmelo.orderlistapp.response.UserResponse;
import com.jonatasmelo.orderlistapp.utils.ConfigProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@ManagedBean
@SessionScoped
public class UserSession {

    private UserResponse loggedUserResponse = null;

    public boolean login(String username, String password) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            LoginRequest request = new LoginRequest(username, password);

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String json = new Gson().toJson(request);
                HttpPost httpPost = new HttpPost(ConfigProperties.getInstance().getApiUrl() + ConfigProperties.ENDPOINT_LOGIN);
                httpPost.setEntity(new StringEntity(json));
                httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                CloseableHttpResponse response = httpClient.execute(httpPost);

                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Gson gson = ConfigProperties.getGson();
                    UserResponse userResponse = gson.fromJson(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), UserResponse.class);
                    if (userResponse != null && StringUtils.isNotBlank(userResponse.getToken())) {
                        loggedUserResponse = userResponse;
                        return true;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public boolean isLoggedIn() {
        return (loggedUserResponse != null
                && StringUtils.isNotBlank(loggedUserResponse.getToken())
                && LocalDateTime.now().isBefore(loggedUserResponse.getTokenExpirationDate()));
    }

    public void invalidate() {
        loggedUserResponse = null;
    }

    public String getAccessToken() {
        if (loggedUserResponse != null) {
            return StringUtils.defaultString(loggedUserResponse.getToken());
        }
        return "";
    }
}
