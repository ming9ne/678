package com.sw678.crud.service;

import com.sw678.crud.model.dto.BoardDto;
import com.sw678.crud.model.entity.Board;
import com.sw678.crud.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class MapService {

    private final BoardRepository boardRepository;


}
