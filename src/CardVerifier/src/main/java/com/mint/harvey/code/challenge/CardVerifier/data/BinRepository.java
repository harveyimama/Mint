package com.mint.harvey.code.challenge.CardVerifier.data;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BinRepository extends JpaRepository<BinEntity,Long>{

	BinEntity findByBin(String bin);

	@Query(value = "SELECT * FROM Bin_Entity where id >= :start order by id desc limit :limit", nativeQuery = true)

	List<BinEntity> findAllOffestLimit(int start, int limit);

	


}
