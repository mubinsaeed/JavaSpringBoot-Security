package com.demo.student;


import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api")
public class ManagementContoller {
    private static final List<Student> students = Arrays.asList(
            new Student(1,"Ali"),
            new Student(2,"Shah"),
            new Student(3,"Ahmed")
    );
    @GetMapping
    public List<Student> getAllStudent(){
        System.out.println("Getting all student");
        return students;
    }
    @GetMapping(path = "{id}")
    public void getSingleStudent(@PathVariable("id") Integer id){
        System.out.println("The Student id is "+id);
    }

    @DeleteMapping(path =  "{id}")
    public void deleteStudent(@PathVariable("id") int id){
        System.out.println("Deleting the student with the id "+id);
    }

    @PutMapping(path = "{id}")
    public  void UpdateStudent(@RequestBody Student student , @PathVariable("id") int id){
        System.out.println("The student is updated");
    }

    @PostMapping
    public void addStudent(@RequestBody Student student){
        System.out.println("Adding the student record "+ student);
    }
}
