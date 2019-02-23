package com.TaskTracking.TaskProject.repository;

import com.TaskTracking.TaskProject.dto.Tasks;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends ReactiveMongoRepository<Tasks,String> {

}
