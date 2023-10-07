#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository.impl;

import ${package}.config.ApplicationProperties;
import ${package}.dto.DemoDto;
import ${package}.repository.DemoRepository;
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
