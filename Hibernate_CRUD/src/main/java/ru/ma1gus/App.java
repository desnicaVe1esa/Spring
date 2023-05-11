package ru.ma1gus;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.ma1gus.models.Actor;
import ru.ma1gus.models.Item;
import ru.ma1gus.models.Movie;
import ru.ma1gus.models.Passport;
import ru.ma1gus.models.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class App {
    public static void main( String[] args ) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class).addAnnotatedClass(Passport.class);

//        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class)
//                .addAnnotatedClass(Movie.class);


        SessionFactory sessionFactory = configuration.buildSessionFactory();
        // try с ресурсами
        try (sessionFactory) {
            Session session = sessionFactory.openSession();

            session.beginTransaction();

//        Person getPerson = session.get(Person.class, 1);

//        Person person = new Person("Парень", 29);
//        Person person1 = new Person("Программист", 30);
//        Person person2 = new Person("Красава", 31);

//        session.save(person);
//        session.save(person1);
//        session.save(person2);

//        getPerson.setName("Парень");

//        System.out.println(person.getId());

//        session.delete(getPerson);

//        List<Person> people = session.createQuery("from Person where age < 30").getResultList();
//        for (Person person : people) {
//            System.out.println(person);
//        }
//
//        session.createQuery("update Person set name='Кайфарик' where age < 30")
//                .executeUpdate();

/////////////////////////////////
////////////// Работа со связями
////////////// OneToMany
/////////////////////////////////
//        Person person = session.get(Person.class, 1);
//        System.out.println(person);
//        Person newPerson = new Person("Четкий тим лид", 40);
//        Person person1 = session.get(Person.class, 2);
//        Person person2 = session.get(Person.class, 3);
//
//        List<Item> items = person.getItem();
//        System.out.println(items);
//
//        // SQL
//        for (Item item : items) {
//            session.remove(item);
//        }
//
//        // Не порождает SQL, но необходимо для того, чтобы в кэше все было верно
//        person.getItem().clear();
//
//        Item newItem = new Item("Квартира", person);
//        Item item = session.get(Item.class, 6);
//
//        person.getItem().add(newItem);
//        newPerson.setItem(new ArrayList<>(Collections.singletonList(newItem)));
//        item.getPerson().getItem().remove(item);
//        // SQL
//        item.setPerson(person2);
//
//        person2.getItem().add(item);
//
//        session.save(newItem);
//        session.save(newPerson);
//        // SQL
//        session.remove(person1);
//        // Правильное состояние Hibernate кэша
//        person1.getItem().forEach(i -> i.setPerson(null));

/////////////////////////////
////////////// Каскадирование
/////////////////////////////
//        Person person = new Person("Test cascading", 30);
//
            // Item item = new Item("Test cascading item", person);
//        person.addItem(new Item("Test cascading item 1"));
//        person.addItem(new Item("Test cascading item 2"));
//        person.addItem(new Item("Test cascading item 3"));
//
            // person.setItems(new ArrayList<>(Collections.singletonList(item)));
//
            // session.persist(person);
//        session.save(person);

///////////////////////
////////////// OneToOne
///////////////////////
            // Person person = new Person("OneToOne person", 29);
            // Passport passport = new Passport(person, 123456);
            // Passport passport = new Passport(123456);

            // person.setPassport(passport);

            // session.save(person);

//        Person person = session.get(Person.class, 13);
//        System.out.println(person.getPassport().getPassportNumber());
//
//        Passport passport = session.get(Passport.class, 14);
//        System.out.println(passport.getPerson().getName());
//
//        Person person1 = session.get(Person.class, 13);
//        person.getPassport().setPassportNumber(654321);
//
//        session.remove(person1);

/////////////////////////
////////////// ManyToMany
/////////////////////////
//            Movie movie = new Movie("Зеленый слоник", 1999);
//            Actor actor1 = new Actor("Владимир Епифанцев", 50);
//            Actor actor2 = new Actor("Сергей Пахомов", 55);

            // Arrays.asList()
//            movie.setActors(new ArrayList<>(List.of(actor1, actor2)));
//
//            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
//            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie)));
//
//            session.save(movie);
//
//            session.save(actor1);
//            session.save(actor2);

//            Movie movie = new Movie("5 бутылок водки", 2002);
//            Actor actor = session.get(Actor.class, 2);
//
//            movie.setActors(new ArrayList<>(Collections.singletonList(actor)));
//
//            actor.getMovies().add(movie);
//
//            session.save(movie);
//
//            session.save(actor);

//            Actor actor = session.get(Actor.class, 2);
//            System.out.println(actor.getMovies());
//
//            Movie movieToRemove = actor.getMovies().get(0);
//
//            actor.getMovies().remove(0);
//            movieToRemove.getActors().remove(actor);

////////////////////////
////////////// FetchType
////////////////////////

            Person person = session.get(Person.class, 1);
            System.out.println("Получили человека");

            // Получим связанные сущности (Lazy)
            System.out.println(person.getItems());

            Item item = session.get(Item.class, 10);
            System.out.println("Получили товар");

            System.out.println(item.getPerson());

            Person person1 = session.get(Person.class, 1);
            System.out.println("Получили человека из таблицы");
            System.out.println(person1);

            Hibernate.initialize(person1.getItems()); // Подгружаем связанные ленивые сущности

            System.out.println(person1.getItems());

            session.getTransaction().commit();
            System.out.println("Сессия закончилась");
            // Открываем сессию и транзакцию еще раз
            session = sessionFactory.openSession();
            session.beginTransaction();
            System.out.println("Внутри второй транзакции");

            person1 = (Person) session.merge(person1);
            Hibernate.initialize(person1.getItems());
            // Пример HQL запроса вместо Hibernate.initialize(person1.getItems());
            session.createQuery("select i from Item i where i.person.id=:personId", Item.class)
                    .setParameter("personId", person1.getId()).getResultList();
            session.getTransaction().commit();

            System.out.println("Вне второй сессии");

            // При Eager товары можно получать вне сессии, т.к. они уже были загружены
            System.out.println("Вне сессии");
            System.out.println(person1.getItems());
//            sessionFactory.close(); // при try с ресурсами сессия закрывается сама
        }
    }
}
