#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import ${package}.dto.DemoDto;

import java.util.List;

public interface DemoRepository {
    DemoDto find(String id);
}
