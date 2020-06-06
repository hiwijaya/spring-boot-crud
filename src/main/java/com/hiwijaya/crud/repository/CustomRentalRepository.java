package com.hiwijaya.crud.repository;

import com.hiwijaya.crud.entity.RentTransaction;
import com.hiwijaya.crud.util.RentStatus;

/**
 * @author Happy Indra Wijaya
 */
public interface CustomRentalRepository {

    RentTransaction rent(RentTransaction transaction);

    void updateStatus(Long transactionId, RentStatus status);

}
