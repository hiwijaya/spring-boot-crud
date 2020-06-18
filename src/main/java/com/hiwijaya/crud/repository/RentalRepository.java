package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.util.RentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Happy Indra Wijaya
 */
public interface RentalRepository extends JpaRepository<RentTransaction, Long>, CustomRentalRepository {

    List<RentTransaction> findByStatus(RentStatus status);

}
