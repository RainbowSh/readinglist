package com.example;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Rainbow on 2017/1/4.
 */
public interface BookShelfRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
}
