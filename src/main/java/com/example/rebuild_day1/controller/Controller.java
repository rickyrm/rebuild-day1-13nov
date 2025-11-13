package com.example.rebuild_day1.controller;

import com.example.rebuild_day1.model.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Person")
public class Controller {

    @GetMapping
    public List<Person> getPerson() {
        return List.of(
                new Person("Juan", "Pérez", 30, "Madrid"),
                new Person("Ana", "Gómez", 25, "Sevilla"),
                new Person("Luis", "Martínez", 40, "Valencia")
        );
    }

}
