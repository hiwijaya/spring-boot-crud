package com.hiwijaya.crud.entity;

import com.hiwijaya.crud.util.RentStatus;
import com.hiwijaya.crud.util.StatusConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rent_transactions")
public class RentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Please select one of customer")
    private Customer customer;

    @Column(name = "rental_date")
    @Temporal(TemporalType.DATE)    // use TIMESTAMP to store date and time
    @NotNull(message = "Rental date is mandatory")
    @FutureOrPresent
    private Date rentalDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Return date is mandatory")
    @Future
    private Date returnDate;

    @Column(name = "total")
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @Convert(converter = StatusConverter.class)
    private RentStatus status;      // 0, 1, 2

    @OneToMany(mappedBy = "rentTransaction", cascade = CascadeType.ALL)
    private List<RentTransactionDetail> details;


    public RentTransaction(Long id){
        this.id = id;
    }


    public void setCustomerOnlyId(Integer customerId){
        this.customer = new Customer(customerId);
    }


    @Override
    public String toString() {
        return "RentTransaction{" +
                "id=" + id +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}
