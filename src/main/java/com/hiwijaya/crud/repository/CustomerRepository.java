package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Happy Indra Wijaya
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
