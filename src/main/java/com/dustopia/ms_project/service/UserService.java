package com.dustopia.ms_project.service;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.ReaderRequest;
import com.dustopia.ms_project.model.dto.response.ReaderResponse;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ReaderResponse registerReader(ReaderRequest request) throws InvalidException;

    ReaderResponse updateReader(String id , ReaderRequest request) throws InvalidException;

    void deleteReader(String uuid) throws InvalidException;

    List<ReaderResponse> getAllReader();

    ReaderResponse getReaderById(String id);
}
