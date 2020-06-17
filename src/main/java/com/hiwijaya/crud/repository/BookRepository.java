package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthor(String author);

    List<Book> findByRentedFalse();

}
