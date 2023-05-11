package ru.ma1gus.practice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ma1gus.practice.models.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
//    private static int PEOPLE_COUNT;

    // JDBC
//    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "root";
//
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Динамический список для приколюх без БД
//    private List<Person> people;
//
//    {
//        people = new ArrayList<>();
//        people.add(new Person(++PEOPLE_COUNT, "Kek", 14, "kek@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Lol", 18, "lol@ya.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Fuf", 22, "fuf@gmail.com"));
//        people.add(new Person(++PEOPLE_COUNT, "Foo", 26, "foo@yahoo.com"));
//    }

    public List<Person> index() {

        // JDBC
//        List<Person> people = new ArrayList<>();
//        try {
//            Statement statement = connection.createStatement();
//            String SQL = "select * from spring_practice.person";
//            ResultSet resultSet = statement.executeQuery(SQL);
//            while (resultSet.next()) {
//                Person person = new Person();
//
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setEmail(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//
//                people.add(person);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return people;

        // JDBC Template
        return jdbcTemplate.query("select * from spring_practice.person", new PersonMapper());
    }


    public Person show(int id) {
        // Для динамического списка
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);

        // JDBC
//        Person person = null;
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("select * from spring_practice.person where id=?");
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            resultSet.next();
//
//            person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return person;

        // JDBC Template. Через BeanPropertyRowMapper
        return jdbcTemplate.query("select * from spring_practice.person where id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public Person show(String email) {
        return jdbcTemplate.query("select * from spring_practice.person where email=?",
                        new Object[]{email},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public void save(Person person) {
        // Для динамического списка
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

        // JDBC
//        try {
//            Statement statement = connection.createStatement();
//            String SQL = "insert into spring_practice.person values("+ 1 + ",'" + person.getName() +
//            "'," + person.getAge() + ",'" + person.getEmail() + "')";
//            statement.executeQuery(SQL);

//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("insert into spring_practice.person values(1, ?, ?, ?)");
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setInt(2, person.getAge());
//            preparedStatement.setString(3, person.getEmail());
//
//            preparedStatement.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        // JDBC Template
        jdbcTemplate.update("insert into spring_practice.person(name, age, email, address) values(?, ?, ?, ?)",
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress());
    }

    public void update(int id, Person updatePerson) {
        // JDBC
//        Person personToBeUpdated = show(id);
//
//        personToBeUpdated.setName(updatePerson.getName());
//        personToBeUpdated.setAge(updatePerson.getAge());
//        personToBeUpdated.setEmail(updatePerson.getEmail());
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("update spring_practice.person set name=?, age=?, email=? where id=?");
//
//            preparedStatement.setString(1, updatePerson.getName());
//            preparedStatement.setInt(2, updatePerson.getAge());
//            preparedStatement.setString(3, updatePerson.getEmail());
//            preparedStatement.setInt(4, id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // JDBC Template
        jdbcTemplate.update("update spring_practice.person set name=?, age=?, email=?, address=? where id=?",
                updatePerson.getName(),
                updatePerson.getAge(),
                updatePerson.getEmail(),
                updatePerson.getAddress(),
                id);
    }

    public void delete(int id) {
        // Для динамического списка
//        people.removeIf(person -> person.getId() == id);

        // JDBC
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("delete from spring_practice.person where id=?");
//            preparedStatement.setInt(1, id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // JDBC Template
        jdbcTemplate.update("delete from spring_practice.person where id=?", id);
    }

    /////////////////////////////////////////////////////
    //// Тестирование производительности пакетной вставки
    /////////////////////////////////////////////////////

    public void testMultiplyUpdate() {
        List<Person> people = createOneThousandPerson();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("insert into spring_practice.person values (?, ?, ?, ?)",
                    person.getId(),
                    person.getName(),
                    person.getAge(),
                    person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Test time: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = createOneThousandPerson();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("insert into spring_practice.person values (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
        long after = System.currentTimeMillis();
        System.out.println("Test time: " + (after - before));
    }

    public List<Person> createOneThousandPerson() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, (int) (Math.random() * (50 - 20) + 20), "test" + i + "pochta.ru"));
        }
        return people;
    }
}
