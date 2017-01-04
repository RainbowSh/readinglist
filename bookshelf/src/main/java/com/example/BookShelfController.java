package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Rainbow on 2017/1/4.
 */
@Controller
@RequestMapping("/")
public class BookShelfController {

    private BookShelfRepository bookShelfRepository;

    @Autowired
    public BookShelfController(BookShelfRepository bookShelfRepository) {
        this.bookShelfRepository = bookShelfRepository;
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> bookShelfList = bookShelfRepository.findByReader(reader);
        if (bookShelfList != null) {
            model.addAttribute("books", bookShelfList);
        }

        return "bookShelfList";
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        bookShelfRepository.save(book);

        return "redirect:/{reader}";
    }
}
