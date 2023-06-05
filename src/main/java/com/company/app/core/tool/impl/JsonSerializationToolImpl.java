package com.company.app.core.tool.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public class JsonSerializationToolImpl<T> implements JsonSerializationTool<T> {

    @SneakyThrows
    @Override
    public void save(List<T> list, File file) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, list.toArray());
    }

    @SneakyThrows
    @Override
    public String asString(List<T> list) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(list);
    }

    @SneakyThrows
    @Override
    public String asString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    @SneakyThrows
    @Override
    public List<T> load(File file, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, getCollectionType(type, mapper));
    }

    @SneakyThrows
    @Override
    public List<T> load(String string, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(string, getCollectionType(type, mapper));
    }

    @SneakyThrows
    @Override
    public List<T> load(InputStream inputStream, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, getCollectionType(type, mapper));
    }

    @SneakyThrows
    @Override
    public List<T> load(Resource resource, Class<T> type) {
        List<T> list;
        try (InputStream inputStream = resource.getInputStream()) {
            list = this.load(inputStream, type);
        }
        return list;
    }

    @Override
    public T loadOne(File file, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    @Override
    public T loadOne(String string, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(string, type);
    }

    @Override
    public T loadOne(InputStream inputStream, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T loadOne(Resource resource, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    private CollectionType getCollectionType(Class<T> type, ObjectMapper mapper) {
        return mapper.getTypeFactory().constructCollectionType(List.class, type);
    }
}
