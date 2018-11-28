package com.pavan.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.model.ChitBO;

@Repository
public interface ChitRepository extends JpaRepository<ChitBO, Long> {
	@Query("select sum(c.actualAmount) from ChitBO c")
	public int getTotalActualAmount();

	@Query("select sum(c.paidAmount) from ChitBO c")
	public int getTotalPaidAmount();
}
