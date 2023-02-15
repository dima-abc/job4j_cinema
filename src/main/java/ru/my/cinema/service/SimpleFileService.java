package ru.my.cinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.my.cinema.model.dto.FileDto;
import ru.my.cinema.repository.FileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/**
 * 3. Мидл
 * 3.2. Web
 * 3.2.9. Контрольные вопросы
 * 2. Сервис - Кинотеатр [#504869 #293473]
 * SimpleFileService реализация слоя бизнес логики управления File
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.02.2023
 */
@Service
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;

    private final String storageDirectory;

    public SimpleFileService(FileRepository fileRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    /**
     * Поиск файла по ID и возвращает DTO для отображения в виде.
     * @param fileId
     * @return
     */
    @Override
    public Optional<FileDto> getFileById(int fileId) {
        var fileOptional = fileRepository.getFindById(fileId);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    /**
     * Создание директории с полным путем подкаталогов к ней, если ее еще нет.
     *
     * @param path directory name
     */
    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName.
     * Так создается уникальный путь для нового файла.
     * UUID это просто рандомная строка определенного формата;
     *
     * @param sourceName File name
     * @return random source.
     */
    private String getNewFilePath(String sourceName) {
        return storageDirectory + File.separator + UUID.randomUUID() + sourceName;
    }

    /**
     * Записывает массив байт в файл
     *
     * @param path    File name
     * @param content Array byte
     */
    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Чтение файла в массив байт.
     *
     * @param path File name
     * @return Array byte
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление файла из каталога файловой системы.
     *
     * @param path File name
     */
    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
