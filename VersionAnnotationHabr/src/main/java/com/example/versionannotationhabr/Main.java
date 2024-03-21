package com.example.versionannotationhabr;

import com.example.versionannotationhabr.entity.MyEntity;
import jakarta.persistence.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// В этом классе создаем несколько потоков и смотрим, что будет происходить.
public class Main {
// Создаем фабрику, т.к. создание EntityManagerFactory дело дорогое, обычно делается это один раз.
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ru.easyjava.data.jpa.hibernate");

    public static void main(String[] args) {
// Создаем 10 потоков(можно и больше, но в таком случае будет сложно разобраться).
        ExecutorService es = Executors.newFixedThreadPool(10);
        try {
// Метод persistFill() нужен для авто-заполнения таблицы.
           persistFill();
            for(int i=0; i<10; i++){
                int finalI = i;
                es.execute(() -> {
// Лучше сначала запустить без метода updateEntity(finalI) так, чтоб java создала сущность в базе и заполнила ее. Но так как java - очень умная, она сама запоминает последний сгенерированный id, даже если вы решили полностью очистить таблицу, id новой строки будет таким, как будто вы не чистили базу данных(может возникнуть ситуация, в которой вы запускаете метод persistFill(), а id в бд у вас начинаются с 500).
                    updateEntity(finalI);
                });
            }
            es.shutdown();
            try {
                es.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            entityManagerFactory.close();
        }
    }

// Метод для получения объекта из базы и изменения его.
    private static void updateEntity(int index) {
// Создаем EntityManager для того, чтобы можно было вызывать методы, управления жизненным циклом сущности.
        EntityManager em = entityManagerFactory.createEntityManager();

        MyEntity myEntity = null;
        try {
            em.getTransaction().begin();
// Получаем объект из базы данных по индексу 1.
            myEntity = em.find(MyEntity.class, 1);
// Вызываем этот sout, чтобы определить каким по очереди был "вытянут" объект.
            System.out.println("load = "+index);
// Эту строчку мы и будем изменять (а именно LockModeType.*).
            em.lock(myEntity, LockModeType.OPTIMISTIC);
// Изменяем поле Value, таким образом, чтобы понимать транзакция из какого потока изменила его.
            myEntity.setValue("WoW_" + index);
            em.getTransaction().commit();
            em.close();
            System.out.println("--Greeter updated : " + myEntity +" __--__ "+ index);
        }catch(RollbackException ex){
            System.out.println("ГРУСТЬ, ПЕЧАЛЬ=" + myEntity);
        }
    }

    public static void persistFill() {
        MyEntity myEntity  = new MyEntity();
        myEntity.setValue("JPA");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(myEntity);
        em.getTransaction().commit();
        em.close();
    }
}