#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import ${package}.dto.DemoDto;
import ${package}.repository.DemoRepository;
import ${package}.service.DemoService;
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
