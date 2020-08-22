package br.com.jvjr.person;

import io.javalin.http.Context;
import java.util.List;

public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public void create(Context ctx) {
        Person person = ctx.bodyAsClass(Person.class);
        Person newPerson = personService.create(person);
        ctx.status(200).json(newPerson);
    }
    
    public void getAll(Context ctx) {
        List<Person> persons = personService.getAll();
        ctx.status(200);
        ctx.json(persons);
    }
    
    

}
