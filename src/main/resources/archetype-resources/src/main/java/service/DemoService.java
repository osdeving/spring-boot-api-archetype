#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;


import ${package}.dto.DemoDto;

import java.util.List;

public interface DemoService {
    DemoDto find(String id);
}
