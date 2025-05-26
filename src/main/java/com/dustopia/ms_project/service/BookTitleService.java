package com.dustopia.ms_project.service;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BookTitleRequest;
import com.dustopia.ms_project.model.dto.response.BookResponse;
import com.dustopia.ms_project.model.entity.Librarian;

import java.util.List;
import java.util.UUID;

public interface BookTitleService {
    BookResponse createBookTitle(BookTitleRequest bookTitleRequest);
    BookResponse updateBookTitle(String Id,BookTitleRequest bookTitleRequest) throws InvalidException;
    List<BookResponse> getAllBookTitle();
}
