package com.dustopia.ms_project.service;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.BookTitleRequest;
import com.dustopia.ms_project.model.dto.response.BookResponse;

import java.util.UUID;

public interface BookTitleService {
    BookResponse createBookTitle(BookTitleRequest bookTitleRequest);
    BookResponse updateBookTitle(String Id,BookTitleRequest bookTitleRequest) throws InvalidException;

}
