package com.TaskTracking.TaskProject.controllerTests;

import com.TaskTracking.TaskProject.dto.Tasks;
import com.TaskTracking.TaskProject.repository.TasksRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TasksControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    TasksRepository tasksRepository;

    @Test
    public void getAllTasks(){
        webTestClient.get()
                .uri("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Tasks.class);

    }

    @Test
    public void createTasks(){
        Date createdAt = new Date();
        List newtasks = new ArrayList();
        newtasks.add("brushed my hair");
        newtasks.add("ate cereal");
        Tasks newtask = new Tasks("id","today",createdAt,newtasks);

        webTestClient.post()
                .uri("/tasks/save")
                .body(BodyInserters.fromObject(newtask))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Tasks.class);

    }

    @Test
    public void findTaskById(){
        Date createdAt = new Date();
        List newtasks = new ArrayList();
        newtasks.add("brushed my hair");
        newtasks.add("ate cereal");
        Tasks tasks = tasksRepository.save(new Tasks("id","today",createdAt,newtasks)).block();
        webTestClient.get()
                .uri("/tasks/{id}", Collections.singletonMap("id",tasks.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull());
    }
}
