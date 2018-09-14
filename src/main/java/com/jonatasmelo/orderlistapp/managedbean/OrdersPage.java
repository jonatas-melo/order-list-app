package com.jonatasmelo.orderlistapp.managedbean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jonatasmelo.orderlistapp.response.CustomerResponse;
import com.jonatasmelo.orderlistapp.response.OrderDetailResponse;
import com.jonatasmelo.orderlistapp.response.OrderResponse;
import com.jonatasmelo.orderlistapp.utils.ConfigProperties;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class OrdersPage {
    @ManagedProperty("#{userSession}")
    private UserSession userSession;

    private List<CustomerResponse> customerList;
    private List<CustomerResponse> filteredCustomers;
    private CustomerResponse selectedCustomer;

    private List<OrderResponse> customerOrderList;

    @PostConstruct
    public void init() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(ConfigProperties.getInstance().getApiUrl() + ConfigProperties.ENDPOINT_CUSTOMERS);
            httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
            httpGet.setHeader(ConfigProperties.HEADER_ACCESS_TOKEN, userSession.getAccessToken());

            CloseableHttpResponse response = httpClient.execute(httpGet);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Gson gson = ConfigProperties.getGson();
                Type type = new TypeToken<ArrayList<CustomerResponse>>(){}.getType();
                customerList = gson.fromJson(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (customerList == null) {
            customerList = new ArrayList<>();
        }
    }

    public void fillOrderList() {
        if (selectedCustomer != null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String url = ConfigProperties.getInstance().getApiUrl() + String.format(ConfigProperties.ENDPOINT_CUSTOMERS_ORDERS, selectedCustomer.getId());
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader(HttpHeaders.ACCEPT, "application/json");
                httpGet.setHeader(ConfigProperties.HEADER_ACCESS_TOKEN, userSession.getAccessToken());

                CloseableHttpResponse response = httpClient.execute(httpGet);

                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Gson gson = ConfigProperties.getGson();
                    Type type = new TypeToken<ArrayList<OrderResponse>>(){}.getType();
                    customerOrderList = gson.fromJson(new InputStreamReader(response.getEntity().getContent(), "UTF-8"), type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (customerOrderList == null) {
                customerOrderList = new ArrayList<>();
            }
        }
    }

    public List<CustomerResponse> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerResponse> customerList) {
        this.customerList = customerList;
    }

    public List<CustomerResponse> getFilteredCustomers() {
        return filteredCustomers;
    }

    public void setFilteredCustomers(List<CustomerResponse> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }

    public CustomerResponse getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(CustomerResponse selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public List<OrderResponse> getCustomerOrderList() {
        return customerOrderList;
    }

    public void setCustomerOrderList(List<OrderResponse> customerOrderList) {
        this.customerOrderList = customerOrderList;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }
}
