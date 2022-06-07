package com.bsf.wallet.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Table(name = "transaction_fail_log")
@Entity
public class TransactionFailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "error")
    private String error;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "cr_account_id")
    private Long crAccountId;

    @Column(name = "dr_account_id")
    private Long drAccountId;

    public TransactionFailLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCrAccountId() {
        return crAccountId;
    }

    public void setCrAccountId(Long crAccountId) {
        this.crAccountId = crAccountId;
    }

    public Long getDrAccountId() {
        return drAccountId;
    }

    public void setDrAccountId(Long drAccountId) {
        this.drAccountId = drAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionFailLog that = (TransactionFailLog) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
