package com.dustopia.ms_project.service.impl;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BookTitleRequest;
import com.dustopia.ms_project.model.dto.response.BookResponse;
import com.dustopia.ms_project.model.entity.BookTitle;
import com.dustopia.ms_project.repository.BookRepository;
import com.dustopia.ms_project.repository.BookTitleRepository;
import com.dustopia.ms_project.service.BookTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookTitleServiceImpl implements BookTitleService {
    private final BookTitleRepository bookTitleRepository;
    private final BookRepository bookRepository;
    @Override
    public BookResponse createBookTitle(BookTitleRequest bookTitleRequest) {
        BookTitle bookTitle = BookTitle.builder()
                .id(UUID.randomUUID().toString())
                .author(bookTitleRequest.getAuthor())
                .name(bookTitleRequest.getName())
                .category(bookTitleRequest.getCategory())
                .description(bookTitleRequest.getDescription())
                .publicYear(bookTitleRequest.getPublicYear())
                .publisher(bookTitleRequest.getPublisher())
                .build();
        bookTitleRepository.save(bookTitle);
        return BookResponse.builder()
                .Id(bookTitle.getId())
                .author(bookTitleRequest.getAuthor())
                .name(bookTitleRequest.getName())
                .category(bookTitleRequest.getCategory())
                .description(bookTitleRequest.getDescription())
                .publicYear(bookTitleRequest.getPublicYear())
                .publisher(bookTitleRequest.getPublisher())
                .build();
    }

    @Override
    public BookResponse updateBookTitle(String Id , BookTitleRequest bookTitleRequest) throws InvalidException {
        Optional<BookTitle> optionalBookTitle = bookTitleRepository.findById(Id);
        if(optionalBookTitle.isEmpty()){
            throw new InvalidException("Book Title not existed");
        }
        BookTitle bookTitle = optionalBookTitle.get();
        bookTitle.setAuthor(bookTitleRequest.getAuthor());
        bookTitle.setCategory(bookTitleRequest.getCategory());
        bookTitle.setName(bookTitleRequest.getName());
        bookTitle.setDescription(bookTitleRequest.getDescription());
        bookTitle.setPublicYear(bookTitleRequest.getPublicYear());
        bookTitle.setPublisher(bookTitle.getPublisher());
        bookTitleRepository.save(bookTitle);
        return BookResponse.builder()
                .Id(bookTitle.getId())
                .author(bookTitleRequest.getAuthor())
                .name(bookTitleRequest.getName())
                .category(bookTitleRequest.getCategory())
                .description(bookTitleRequest.getDescription())
                .publicYear(bookTitleRequest.getPublicYear())
                .publisher(bookTitleRequest.getPublisher())
                .build();
    }

    @Override
    public List<BookResponse> getAllBookTitle() {
        List<BookTitle> bookTitles = bookTitleRepository.findAll();
        List<BookResponse> bookResponses = new ArrayList<>();
        for(BookTitle x: bookTitles){
            bookResponses.add(BookResponse.builder()
                    .Id(x.getId())
                    .author(x.getAuthor())
                    .name(x.getName())
                    .category(x.getCategory())
                    .description(x.getDescription())
                    .publicYear(x.getPublicYear())
                    .publisher(x.getPublisher())
                    .build());
        }
        return bookResponses;
    }

}
