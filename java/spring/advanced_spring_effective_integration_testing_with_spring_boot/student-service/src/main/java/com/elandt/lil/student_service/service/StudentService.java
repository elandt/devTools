package com.elandt.lil.student_service.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.elandt.lil.student_service.domain.Student;
import com.elandt.lil.student_service.exception.StudentNotFoundException;
import com.elandt.lil.student_service.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Cacheable("students")
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("No Student found with id: " + id));
    }
}
