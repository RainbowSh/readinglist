package com.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Rainbow on 2017/1/5.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByReader(String reader);
}
