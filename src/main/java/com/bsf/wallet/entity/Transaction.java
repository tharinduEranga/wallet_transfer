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
@Table(name = "transaction")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "cr_new_balance")
    private BigDecimal crNewBalance;

    @Column(name = "dr_new_balance")
    private BigDecimal drNewBalance;

    @Column(name = "reference")
    private String reference;

    @Lob
    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cr_account_id", referencedColumnName = "id")
    private Account crAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dr_account_id", referencedColumnName = "id")
    private Account drAccount;

    public Transaction() {
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

    public BigDecimal getCrNewBalance() {
        return crNewBalance;
    }

    public void setCrNewBalance(BigDecimal crNewBalance) {
        this.crNewBalance = crNewBalance;
    }

    public BigDecimal getDrNewBalance() {
        return drNewBalance;
    }

    public void setDrNewBalance(BigDecimal drNewBalance) {
        this.drNewBalance = drNewBalance;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getCrAccount() {
        return crAccount;
    }

    public void setCrAccount(Account crAccount) {
        this.crAccount = crAccount;
    }

    public Account getDrAccount() {
        return drAccount;
    }

    public void setDrAccount(Account drAccount) {
        this.drAccount = drAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
