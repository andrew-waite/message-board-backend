package me.andrew.providers;

public interface DatabaseProvider {
    Object save(Object object);
    Object getById(Class<?> clazz, int id);
}
