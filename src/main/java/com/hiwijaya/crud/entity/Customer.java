package com.hiwijaya.crud.entity;

import com.hiwijaya.crud.util.Gender;
import com.hiwijaya.crud.util.GenderConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author Happy Indra Wijaya
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 50, nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "gender", length = 1, nullable = false)
    @Convert(converter = GenderConverter.class)
    @NotNull(message = "Gender is mandatory")
    private Gender gender;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<RentTransaction> rentals;


    public Customer(Integer id, String name, Gender gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }


    public Customer(Integer id){
        this.id = id;
    }

    // obey to Law of Demeter
    public String getGenderSymbol(){
        return gender.getSymbol();
    }


    // WARN: Lombok auto toString() can produce 'LazyInitializationException' when entity have @OneToMany
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }

}
