package com.elandt.lil.student_client.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.elandt.lil.student_client.domain.Student;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudentClient {

    private final WebClient webClient;

    public Student getStudent(Long id) {
        return webClient
                .get()
                .uri("/students/{id}", id)
                .retrieve()
                .bodyToMono(Student.class)
                .block();
    }
}
