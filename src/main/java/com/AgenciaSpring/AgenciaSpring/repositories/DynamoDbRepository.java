package com.AgenciaSpring.AgenciaSpring.repositories;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DynamoDbRepository<T, ID> {
    protected final DynamoDbTable<T> table;
    protected final Class<T> clazz;

    public DynamoDbRepository(DynamoDbEnhancedClient enhancedClient, String tableName, Class<T> clazz) {
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(clazz));
        this.clazz = clazz;
    }

    public void initTable() {
        try {
            table.createTable();
        } catch (Exception e) {
            // La tabla ya existe, no hacemos nada
        }
    }

    public T save(T entity) {
        table.putItem(entity);
        return entity;
    }

    public List<T> saveAll(Iterable<T> entities) {
        List<T> result = new ArrayList<>();
        for (T entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    public Optional<T> findById(ID id) {
        try {
            T keyObject = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("setId", id.getClass()).invoke(keyObject, id);
            T result = table.getItem(keyObject);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        try {
            PageIterable<T> pages = table.scan();
            pages.items().forEach(list::add);
        } catch (Exception e) {
            // Retorna lista vacía si hay algún problema al leer
        }
        return list;
    }

    public void deleteById(ID id) {
        try {
            T keyObject = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("setId", id.getClass()).invoke(keyObject, id);
            table.deleteItem(keyObject);
        } catch (Exception e) {
            // Error al borrar
        }
    }

    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    public void delete(T entity) {
        try {
            table.deleteItem(entity);
        } catch (Exception e) {
            // Error al borrar
        }
    }

    public void deleteAll() {
        for (T item : findAll()) {
            delete(item);
        }
    }

    public long count() {
        return findAll().size();
    }
}
