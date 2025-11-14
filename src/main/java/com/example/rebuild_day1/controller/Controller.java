package com.example.rebuild_day1.controller;

import com.example.rebuild_day1.model.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Person")
public class Controller {

    @GetMapping
    public List<Person> getPerson() {
        return List.of(
                new Person("1","Juan", "Pérez", 30, "Madrid"),
                new Person("2","Ana", "Gómez", 25, "Sevilla"),
                new Person("3","Luis", "Martínez", 40, "Valencia"),
                new Person("4","Marta", "López", 35, "Barcelona")
        );
    }

    @GetMapping("/{id}")
        public ResponseEntity<Map<String, String>>
        getPersonById(@PathVariable Integer id) {
            if (id <=0){
                Map<String, String> errorResponse =
                        Map.of("error", "ID no puede ser menor o igual a 0");
                return
                        ResponseEntity.badRequest().body(errorResponse);
            }
                return
                        ResponseEntity.ok(Map.of("id", id.toString()));
        }
    }


