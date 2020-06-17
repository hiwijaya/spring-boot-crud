package com.hiwijaya.crud.util;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {

    private Customer customer;
    private List<Book> books;

}
