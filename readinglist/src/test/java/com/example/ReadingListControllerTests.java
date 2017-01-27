package com.example;

import com.example.domain.Book;
import com.example.domain.BookRepository;
import com.example.web.ReadingListController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Rainbow on 2017/1/9.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ReadingListController.class)
public class ReadingListControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testHomePage() throws Exception {
        given(bookRepository.findByReader("aoyi"))
                .willReturn(Collections.emptyList());

        this.mvc.perform(get("/aoyi"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    public void testPostBook() throws Exception {

        this.mvc.perform(post("/aoyi")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "DoubanBook Title")
                .param("author", "DoubanBook Author")
                .param("isbn", "1234567890")
                .param("description", "Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/aoyi"));

        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setReader("aoyi");
        expectedBook.setTitle("DoubanBook Title");
        expectedBook.setAuthor("DoubanBook Author");
        expectedBook.setIsbn("1234567890");
        expectedBook.setDescription("Description");

        given(bookRepository.findByReader("aoyi"))
                .willReturn(new ArrayList<Book>() {{
                    add(expectedBook);
                }});

       this.mvc.perform(get("/aoyi"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));

    }
}
