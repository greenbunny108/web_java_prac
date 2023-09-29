package ru.cs.msu.web_java_prac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = { "/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/allPersons" )
    public String allPersons() {
        return "persons";
    }

    @RequestMapping(value = "/tree_generation")
    public String generateTree() {
        return "tree_generation";
    }
}
