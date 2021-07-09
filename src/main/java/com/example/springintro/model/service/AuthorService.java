package com.example.springintro.model.service;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.repository.AuthorRepository;
import com.example.springintro.model.service.impl.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

@Service
class AuthorServiceImpl implements AuthorService {
    private  static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";
    private AuthorRepository authorRepository;


    public void AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count()>0){
            return;

        }

         Files.readAllLines(Path.of(AUTHORS_FILE_PATH))
                 .forEach(row->{
                     String[]fullName = row.split("\\s+");
                     Author author = new Author(fullName[0], fullName[1]);
                     authorRepository.save(author);
                 });

    }

    @Override
    public Author getRandomAuthor() {
        long randomId= ThreadLocalRandom.current().nextLong(1,authorRepository.count()+1);
        return authorRepository.findById(randomId)
                .orElse(null);
    }
}
