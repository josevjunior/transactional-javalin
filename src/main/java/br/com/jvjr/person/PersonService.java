package br.com.jvjr.person;

import br.com.jvjr.connection.ConnectionHolder;
import br.com.jvjr.exception.TransactionalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonService {

    public Person create(Person person) {
         
        try {
            Connection connection = getConnection();
            
            
            String sql = "INSERT INTO person (name) VALUES (?)";
            
            PreparedStatement stam = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stam.setObject(1, person.getName());
            
            stam.executeUpdate();
            
            ResultSet rs = stam.getGeneratedKeys();
            
            Person createdPerson = null;
            
            if(rs.next()) {
                createdPerson = new Person(
                        rs.getLong(1), 
                        person.getName()
                );
            }
            
            return createdPerson;
            
        } catch (SQLException e) {
            throw new TransactionalException(e);
        }
    }

    public List<Person> getAll() {
        
        List<Person> persons = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement stam = connection.createStatement();
            ResultSet rs = stam.executeQuery("SELECT * FROM person");
            while(rs.next()) {
                Person person = new Person(
                        rs.getLong("id"), 
                        rs.getString("name")
                );
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new TransactionalException(e);
        }
        
        return persons;

    }

    private Connection getConnection() {
        return ConnectionHolder.get();
    }

}
