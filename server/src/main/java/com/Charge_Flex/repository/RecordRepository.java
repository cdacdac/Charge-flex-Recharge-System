package com.Charge_Flex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Charge_Flex.entity.Records;

@Repository
public interface RecordRepository extends JpaRepository<Records,Long>{

}
