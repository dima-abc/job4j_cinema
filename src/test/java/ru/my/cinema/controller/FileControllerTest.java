package ru.my.cinema.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.my.cinema.model.dto.FileDto;
import ru.my.cinema.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * FileController TEST.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 08.03.2023
 */
public class FileControllerTest {
    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    public void initService() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenRequestGerFileByIdThenReturnResponseEntityStatusOk() {
        int id = 1;
        var fileDto = new FileDto("file", new byte[]{1, 2, 3});
        when(fileService.getFileById(id)).thenReturn(Optional.of(fileDto));
        var expectedResponseEntity = ResponseEntity.ok(fileDto.getContent());

        var actualResponseEntity = fileController.getFileById(id);

        assertThat(actualResponseEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponseEntity);
    }

    @Test
    public void whenRequestGetFileByIdThenResponseEntityNoyFound() {
        when(fileService.getFileById(anyInt())).thenReturn(Optional.empty());
        var expectedResponseEntity = ResponseEntity.notFound().build();

        var actualResponseEntity = fileController.getFileById(anyInt());

        assertThat(actualResponseEntity)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponseEntity);
    }

}