package com.dustopia.ms_project.service;

import com.dustopia.ms_project.model.dto.request.BorrowRequest;
import com.dustopia.ms_project.model.dto.request.ReturnRequest;
import com.dustopia.ms_project.model.dto.response.BookBorrowResponse;
import com.dustopia.ms_project.model.dto.response.BorrowedBookResponse;
import com.dustopia.ms_project.model.dto.response.ReturnResponse;

import java.util.List;
import java.util.UUID;

public interface BorrowBookService {
    BookBorrowResponse borrowBooks(BorrowRequest req);
    ReturnResponse returnBooks(ReturnRequest req);
    List<BorrowedBookResponse> getCurrentBorrowedByReader(String userId);
}
