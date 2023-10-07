package it.pkg.repository;

import it.pkg.dto.DemoDto;

import java.util.List;

public interface DemoRepository {
    DemoDto find(String id);
}
