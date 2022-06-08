package com.bsf.wallet.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "transaction")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal amount;

    @Column(name = "cr_new_balance", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal crNewBalance;

    @Column(name = "dr_new_balance", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
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
