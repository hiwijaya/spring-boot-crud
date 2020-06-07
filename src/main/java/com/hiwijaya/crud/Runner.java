package com.hiwijaya.crud;

import com.hiwijaya.crud.entity.Book;
import com.hiwijaya.crud.entity.Customer;
import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.service.BookService;
import com.hiwijaya.crud.service.CustomerService;
import com.hiwijaya.crud.service.RentalService;
import com.hiwijaya.crud.util.BookUnavailableException;
import com.hiwijaya.crud.util.Gender;
import com.hiwijaya.crud.util.RentOutdatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Happy Indra Wijaya
 */
@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RentalService rentalService;


    private void createCustomers(){
        Customer customer1 = new Customer(null, "Liam Abraham Wijaya", Gender.MALE);
        Customer customer2 = new Customer(null, "Emma Watson", Gender.FEMALE);
        Customer customer3 = new Customer(null, "John Wick", Gender.MALE);

        customerService.save(customer1, customer2, customer3);

        customerService.getAll().forEach(System.out::println);

    }

    private void createBooks(){
        Book book1 = new Book(null,
                "The Fellowship of The Ring",
                "J. R. R. Tolkien",
                new BigDecimal(50000),
                false);

        Book book2 = new Book(null,
                "The Two Tower",
                "J. R. R. Tolkien",
                new BigDecimal(50000),
                false);

        Book book3 = new Book(null,
                "Return of The King",
                "J. R. R. Tolkien",
                new BigDecimal(60000),
                false);

        Book book4 = new Book(null,
                "The Hunger Games",
                "Suzanne Collins",
                new BigDecimal(30000),
                false);

        Book book5 = new Book(null,
                "Catching Fire",
                "Suzanne Collins",
                new BigDecimal(30000),
                false);

        Book book6 = new Book(null,
                "Mockingjay",
                "Suzanne Collins",
                new BigDecimal(45000),
                false);

        bookService.save(book1, book2, book3, book4, book5, book6);

        bookService.getAll().forEach(System.out::println);

    }

    private void rent(){
        List<Customer> customers = customerService.getAll();
        List<Book> books = bookService.getAll();

        Customer customer = customers.get(0);

        Book book1 = books.get(0);
        Book book2 = books.get(1);
        Book book3 = books.get(2);

        BigDecimal bill = BigDecimal.ZERO;
        try {
            bill = rentalService.rent(customer, book1, book2, book3);
        } catch (BookUnavailableException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Bill: " + bill.toPlainString());

        rentalService.getAll().forEach(System.out::println);

    }

    private void returnBooks(){
        List<RentTransaction> transactions = rentalService.getAll();
        transactions.forEach(System.out::println);

        RentTransaction transaction = transactions.get(0);

        try {
            boolean succeed = rentalService.returnBooks(transaction);
            System.out.println("Succeed returning all book: " + succeed);
        } catch (RentOutdatedException e) {
            System.out.println(e.getMessage());
        }
    }

    // java -jar spring-boot-app.jar nonOptionArg1 nonOptionArg2 --optionalArg1=val1 --optionalArg2=val2
    private void checkArgs(ApplicationArguments args){

        final String[] sourceArgs = args.getSourceArgs();
        final List<String> nonOptionArgs = args.getNonOptionArgs();
        final Set<String> optionNames = args.getOptionNames();

        System.out.println("SOURCE ARGS -----------");
        Arrays.stream(sourceArgs).forEach(System.out::println);

        System.out.println("NON OPTION ARGS -----------");
        nonOptionArgs.forEach(System.out::println);

        System.out.println("OPTION ARGS -------------");
        optionNames.forEach(System.out::println);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        checkArgs(args);

        createCustomers();
        createBooks();
        rent();
        returnBooks();

    }

}
