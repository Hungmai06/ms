package com.dustopia.ms_project.service.impl;

import com.dustopia.ms_project.exception.InvalidException;
import com.dustopia.ms_project.model.dto.request.ReaderRequest;
import com.dustopia.ms_project.model.dto.response.ReaderResponse;
import com.dustopia.ms_project.model.entity.Reader;
import com.dustopia.ms_project.model.entity.User;
import com.dustopia.ms_project.repository.ReaderRepository;
import com.dustopia.ms_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ReaderRepository userRepository;
    @Override
    public ReaderResponse registerReader(ReaderRequest request) throws InvalidException {
        Optional<Reader> optionalReader = userRepository.findByUsername(request.getUsername());
        if(optionalReader.isPresent()){
            throw new InvalidException("Reader exited");
        }
        Reader reader = Reader.builder()
                .id(UUID.randomUUID().toString())
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .fullName(request.getFullName())
                .password("")
                .build();

        userRepository.save(reader);

        return ReaderResponse.builder()
                .id(reader.getId())
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .fullName(request.getFullName())
                .build();
    }

    @Override
    public ReaderResponse updateReader(String id, ReaderRequest request) throws InvalidException {
        Optional<Reader> optionalReader = userRepository.findById(id);
        if(optionalReader.isEmpty()){
            throw new InvalidException("Reader cannot found");
        }
        Reader reader = optionalReader.get();
        reader.setAddress(request.getAddress());
        reader.setFullName(request.getFullName());
        reader.setEmail(request.getEmail());
        reader.setUsername(request.getUsername());
        reader.setDateOfBirth(request.getDateOfBirth());
        userRepository.save(reader);
        return ReaderResponse.builder()
                .id(reader.getId())
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .fullName(request.getFullName())
                .build();
    }

    @Override
    public void deleteReader(String uuid) throws InvalidException {
        if (!userRepository.existsById(uuid)) {
            throw new InvalidException("Reader with ID " + uuid + " not found.");
        }
        userRepository.deleteById(uuid);
    }


    @Override
    public List<ReaderResponse> getAllReader() {
        List<Reader> readers = userRepository.findAll();
        List<ReaderResponse> readerResponses = new ArrayList<>();
        for(User x: readers){
            readerResponses.add(ReaderResponse.builder()
                    .id(x.getId())
                    .username(x.getUsername())
                    .fullName(x.getFullName())
                    .email(x.getEmail())
                    .address(x.getAddress())
                    .dateOfBirth(x.getDateOfBirth())
                    .fullName(x.getFullName())
                    .build());
        }
        return readerResponses;
    }

    @Override
    public ReaderResponse getReaderById(String id) {
        Optional<Reader> optionalReader = userRepository.findById(id);
        if(optionalReader.isEmpty()){
            throw new InvalidException("Reader not found");
        }
        Reader reader =optionalReader.get();
        return ReaderResponse.builder()
                .fullName(reader.getFullName())
                .address(reader.getAddress())
                .id(reader.getId())
                .dateOfBirth(reader.getDateOfBirth())
                .email(reader.getEmail())
                .username(reader.getUsername())
                .build();
    }
}
