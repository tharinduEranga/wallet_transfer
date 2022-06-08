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
@Builder
@ToString
@Table(name = "transaction_fail_log")
@Entity
public class TransactionFailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
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
