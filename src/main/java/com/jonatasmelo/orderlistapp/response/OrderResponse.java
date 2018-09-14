package com.jonatasmelo.orderlistapp.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long number;
    private LocalDateTime date;
    private LocalDateTime requiredDate;
    private LocalDateTime shippedDate;
    private List<OrderDetailResponse> detailList;

    public OrderResponse() {
    }

    public OrderResponse(Long number, LocalDateTime date, LocalDateTime requiredDate, LocalDateTime shippedDate) {
        this.number = number;
        this.date = date;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(LocalDateTime requiredDate) {
        this.requiredDate = requiredDate;
    }

    public LocalDateTime getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDateTime shippedDate) {
        this.shippedDate = shippedDate;
    }

    public List<OrderDetailResponse> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderDetailResponse> detailList) {
        this.detailList = detailList;
    }
}
