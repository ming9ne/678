package com.sw678.crud.service;

import com.sw678.crud.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class MapService {

    private final BoardRepository boardRepository;


}
