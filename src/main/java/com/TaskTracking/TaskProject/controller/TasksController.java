package com.TaskTracking.TaskProject.controller;

import com.TaskTracking.TaskProject.dto.Tasks;
import com.TaskTracking.TaskProject.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/tasks")
public class TasksController {

    @Autowired
    private TasksRepository tasksRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Flux<Tasks> getAllTasks(){
        return tasksRepository.findAll();
    }

    @RequestMapping(value = "/save", produces = "application/json", method = RequestMethod.POST)
    public Mono<Tasks> createTask(@Validated @RequestBody Tasks tasks){
        return tasksRepository.save(tasks);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Mono<ResponseEntity<Tasks>> findSingleTask(@PathVariable String id){
        return tasksRepository.findById(id)
                .map(savedTask -> ResponseEntity.ok(savedTask))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
