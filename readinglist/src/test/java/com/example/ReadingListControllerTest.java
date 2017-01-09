package com.example;

import com.example.domain.BookRepository;
import com.example.web.ReadingListController;
import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Rainbow on 2017/1/9.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ReadingListController.class)
public class ReadingListControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testHomePage() throws Exception {
        given(this.bookRepository.findByReader("aoyi"))
                .willReturn(ListUtils.EMPTY_LIST);

        this.mvc.perform(get("/aoyi")).andExpect(status().isOk());
    }

}
