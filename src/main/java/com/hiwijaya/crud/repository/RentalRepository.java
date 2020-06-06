package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.RentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Happy Indra Wijaya
 */
public interface RentalRepository extends JpaRepository<RentTransaction, Long>, CustomRentalRepository {

}
