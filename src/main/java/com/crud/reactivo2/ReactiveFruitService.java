package com.crud.reactivo2;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ReactiveFruitService {

    @Inject
    ReactiveMongoClient mongoClient;

    public Uni<List<Fruit>> list() {
        return getCollection().find()
                .map(doc -> {
                    Fruit fruit = new Fruit();
                    fruit.setName(doc.getString("name"));
                    fruit.setDescription(doc.getString("description"));
                    return fruit;
                }).collectItems().asList();
    }

    public Uni<Void> add(Fruit fruit) {
        Document document = new Document()
                .append("name", fruit.getName())
                .append("description", fruit.getDescription());
        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }

    public Uni<Void>delete(){
        return getCollection().drop();

    }

//    public Uni<Fruit>obtenerPorId(String id){
//        return getCollection()
//    }

    private ReactiveMongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("fruit").getCollection("fruit");
    }
}
