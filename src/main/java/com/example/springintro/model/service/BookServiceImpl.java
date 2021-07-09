package com.example.springintro.model.service;

import com.example.springintro.model.entity.*;
import com.example.springintro.model.repository.BookRepository;
import com.example.springintro.model.service.impl.AuthorService;
import com.example.springintro.model.service.impl.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private static final String BOOKS_FILE_PATH="src/main/resources/files/books.txt";
    private final AuthorService authorService;
    private CategoryService categoryService ;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count()>0){
            return;
        }
        Files.readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row->{
                    String[]bookInfo =row.split("\\s+");
                    Book book =createNewBookFromInfo(bookInfo);
                    bookRepository.save(book);
                });

    }

    private Book createNewBookFromInfo(String[] bookInfo) {
        EditionType editionType=EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price=new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction=AgeRestriction.values()[Integer.parseInt(bookInfo[4])];
        String title= Arrays.stream(bookInfo).skip(5).collect(Collectors.joining(" "));
        Author author =authorService.getRandomAuthor();
        Set<Category>categories=categoryService.getRandomCategories() ;
        return new Book(editionType,releaseDate,copies,price,ageRestriction,title,author,categories);
    }
}
