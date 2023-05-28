package com.aet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aet.entity.Order;

public interface OrderMongoRepository extends MongoRepository<Order, Long> {

}
