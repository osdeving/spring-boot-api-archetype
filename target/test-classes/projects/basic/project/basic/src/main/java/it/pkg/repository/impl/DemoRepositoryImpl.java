package it.pkg.repository.impl;

import it.pkg.config.ApplicationProperties;
import it.pkg.dto.DemoDto;
import it.pkg.repository.DemoRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DemoRepositoryImpl implements DemoRepository {
    private final ApplicationProperties properties;

    @Override
    public DemoDto find(String id) {
        return DemoDto.builder()
                    .name("Jonh")
                    .age(23L)
                .build();
    }
}
