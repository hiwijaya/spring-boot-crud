package com.hiwijaya.crud.service;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.entity.RentTransactionDetail;
import com.hiwijaya.crud.repository.RentalRepository;
import com.hiwijaya.crud.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
@Service
public class RentalService {

    @Autowired
    private RentalRepository repository;

    public BigDecimal rent(RentDto form) throws BookUnavailableException {

        List<Book> bookList = form.getBooks();
        Book[] books = bookList.stream().filter(book -> book.isSelected()).toArray(Book[]::new);

        return rent(form.getCustomer(), books);

    }

    public BigDecimal rent(Customer customer, Book... books) throws BookUnavailableException {

        // check if one of the books already rented or not
        checkIfBooksAvailable(books);

        BigDecimal totalRentPrice = getTotalRentPrice(books);

        RentTransaction transaction = new RentTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(Lib.now());
        transaction.setReturnDate(Lib.nextWeek());
        transaction.setTotal(totalRentPrice);
        transaction.setStatus(RentStatus.RENT);

        List<RentTransactionDetail> details = new ArrayList<>();
        for(Book book : books){
            RentTransactionDetail detail = new RentTransactionDetail();
            detail.setRentTransaction(transaction);
            detail.setBook(book);
            details.add(detail);
        }
        transaction.setDetails(details);

        repository.rent(transaction);

        return totalRentPrice;

    }


    /**
    * form (RentDto) already filter available books
    *
    */
    @Deprecated
    private void checkIfBooksAvailable(Book[] books) throws BookUnavailableException {
        boolean rented = Arrays.stream(books).anyMatch(book -> book.isRented());
        if(rented){
            throw new BookUnavailableException("One of the selected books is already rented.");
        }
    }

    private BigDecimal getTotalRentPrice(Book[] books){
        BigDecimal total = BigDecimal.ZERO;
        total = Arrays.stream(books).map(book -> book.getRentPrice())
                .reduce(total, BigDecimal::add);

        return total;
    }

    public boolean returnBooks(RentTransaction transaction) throws RentOutdatedException {

        if(booksAlreadyReturned(transaction)){
            return true;
        }

        checkTransactionIfOutdated(transaction);

        repository.updateStatus(transaction.getId(), RentStatus.RETURNED);

        return true;
    }

    private boolean booksAlreadyReturned(RentTransaction transaction){
        return transaction.getStatus().equals(RentStatus.RETURNED);
    }

    private void checkTransactionIfOutdated(RentTransaction transaction) throws RentOutdatedException {
        if(transaction.getStatus().equals(RentStatus.RENT) && transaction.getReturnDate().before(Lib.now())){
            // outdated
            repository.updateStatus(transaction.getId(), RentStatus.OUTDATED);
            throw new RentOutdatedException("You have to pay the late charges.");
        }
    }


    public RentTransaction getTransaction(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<RentTransaction> getTransactionByStatus(RentStatus status){
        return repository.findByStatus(status);
    }

    public List<RentTransaction> getAll(){
        return repository.findAll();
    }


}
