package com.demo.student;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class Studentcontroller {
    private static final List<Student> students = Arrays.asList(
            new Student(1,"Ali"),
            new Student(2,"Shah"),
            new Student(3,"Ahmed")
    );
    @GetMapping(path = "{studentId}")

    public Student getStudent(@PathVariable("studentId") Integer sId){
        return students.stream().filter(student -> sId.equals(student.getStudentId())).findFirst().orElseThrow(
                ()->new IllegalStateException("Student " + sId + " does not exist")
        );
    }

}
