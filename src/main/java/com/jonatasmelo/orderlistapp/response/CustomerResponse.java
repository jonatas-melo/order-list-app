package com.jonatasmelo.orderlistapp.response;

import java.math.BigDecimal;
import java.util.Objects;

public class CustomerResponse {
    private Long id;
    private String name;
    private String phone;
    private BigDecimal creditLimit;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String name, String phone, BigDecimal creditLimit) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.creditLimit = creditLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerResponse that = (CustomerResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
