package com.pavan.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.model.FixedDepositBO;

@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDepositBO, Long> {
	@Query("select count(c) from FixedDepositBO c where c.status = ?1")
	public int countByStatus(String Status);
}
