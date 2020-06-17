package com.hiwijaya.crud.entity;

import com.hiwijaya.crud.util.BooleanConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)     // default
    private Integer id;

    @Column(name = "title", length = 100, nullable = false)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(name = "author", length = 100, nullable = false)
    @NotBlank(message = "Author is mandatory")
    private String author;

    @Column(name = "rent_price")
    @PositiveOrZero(message = "Cannot be minus")
    private BigDecimal rentPrice = BigDecimal.ZERO;     // dealing with 'default' value of relational mapping

    @Column(name = "rented", length = 1)
    @Convert(converter = BooleanConverter.class)
    private boolean rented = false;     // Y/N

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<RentTransactionDetail> transactionDetails;

    @Transient  // not persisted
    private boolean selected = false;   // used for form DTO


    public Book(Integer id){
        this.id = id;
    }

    public Book(Integer id, String title, String author, BigDecimal rentPrice, boolean rented) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.rentPrice = rentPrice;
        this.rented = rented;
    }

    public void setRentedString(String rented){
        this.rented = "Y".equals(rented);
    }

    public String isRentedString(){
        return rented ? "Y" : "N";
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", rentPrice=" + rentPrice +
                ", rented=" + rented +
                ", selected=" + selected +
                '}';
    }
}
