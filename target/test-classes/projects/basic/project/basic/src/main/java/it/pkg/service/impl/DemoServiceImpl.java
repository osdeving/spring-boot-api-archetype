package it.pkg.service.impl;

import it.pkg.dto.DemoDto;
import it.pkg.repository.DemoRepository;
import it.pkg.service.DemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoServiceImpl implements DemoService {
    private final DemoRepository demoRepository;
    @Override
    public DemoDto find(String id) {
        return demoRepository.find(id);
    }
}
