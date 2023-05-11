package ru.ma1gus.Spring_REST_practice.exceptions;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String message) {
        super(message);
    }
}
