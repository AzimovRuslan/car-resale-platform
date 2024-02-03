package com.example.platform.aspect.utility;

import com.example.platform.aspect.Constant;
import com.example.platform.aspect.exception.NoSuchRecordException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class RecordGetter {

    public static <T> T getRecordFromTable(Long id, JpaRepository<T, Long> repository) {

        Optional<T> recordFromTable = repository.findById(id);

        return recordFromTable.orElseThrow(() -> new NoSuchRecordException(Constant.RECORD_NOT_FOUND + id));
    }
}
