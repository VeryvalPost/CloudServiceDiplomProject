package netology.cloudserverdiplom.service;

import netology.cloudserverdiplom.entity.File;
import netology.cloudserverdiplom.error.FileError;
import netology.cloudserverdiplom.error.TokenError;
import netology.cloudserverdiplom.model.FileData;
import netology.cloudserverdiplom.repository.FileRepo;
import netology.cloudserverdiplom.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.*;


public class FileServiceTest {

    @Mock
    private FileRepo fileRepo;
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    public void setup() {
        this.fileRepo = mock(FileRepo.class);
        this.userRepo = mock(UserRepo.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFile() throws IOException {
        String authToken = "testAuthToken";
        String filename = "testFile.txt";
        byte[] fileBytes = "Test file content".getBytes();

        Assertions.assertDoesNotThrow(() ->fileService.uploadFile(authToken, filename, fileBytes));
        Assertions.assertThrows(TokenError.class, () -> fileService.uploadFile(null, filename, fileBytes));
    }

    @Test
    public void testDeleteFile() {
        String authToken = "testAuthToken";
        String filename = "testFile.txt";

        when(fileRepo.existsByAuthTokenAndFilename(authToken, filename)).thenReturn(true);
        fileService.deleteFile(authToken, filename);
        verify(fileRepo, times(1)).deleteByAuthTokenAndFilename(authToken, filename);

        when(fileRepo.existsByAuthTokenAndFilename(authToken, filename)).thenReturn(false);
        Assertions.assertThrows(FileError.class, () -> fileService.deleteFile(authToken, filename));
    }


    @Test
    public void testGetFile() {
        String authToken = "testAuthToken";
        String filename = "testFile.txt";
        byte[] fileBytes = "Test file content".getBytes();

        File fileEntity = new File();
        fileEntity.setFileData(fileBytes);

        when(fileRepo.existsByAuthTokenAndFilename(authToken, filename)).thenReturn(true);
        when(fileRepo.findByAuthTokenAndFilename(authToken, filename)).thenReturn(fileEntity.getFileData());

        byte[] result = fileService.getFile(authToken, filename);
        Assertions.assertArrayEquals(fileBytes, result);

        when(fileRepo.existsByAuthTokenAndFilename(authToken, filename)).thenReturn(false);
        Assertions.assertThrows(FileError.class, () -> fileService.getFile(authToken, filename));
    }

    @Test
    public void testUpdateFile() throws FileNotFoundException {
        String authToken = "testAuthToken";
        String filename = "testFile.txt";
        String newName = "newFileName.txt";

        File fileEntity = new File();
        fileEntity.setFilename(filename);

        when(fileRepo.findFileByAuthTokenAndFilename(authToken, filename)).thenReturn(fileEntity);
        fileService.updateFile(authToken, filename, newName);
        verify(fileRepo, times(1)).saveAndFlush(fileEntity);
        Assertions.assertEquals(newName, fileEntity.getFilename());
    }

    @Test
    public void testGetAllFiles() {
        String authToken = "testAuthToken";
        int limit = 10;

        List<File> files = new ArrayList<>();
        File file1 = new File();
        file1.setAuthToken(authToken);
        file1.setFilename("file1.txt");
        file1.setFileData("File 1 content".getBytes());
        files.add(file1);

        File file2 = new File();
        file2.setAuthToken(authToken);
        file2.setFilename("file2.txt");
        file2.setFileData("File 2 content".getBytes());
        files.add(file2);

        when(fileRepo.findByAuthToken(authToken, PageRequest.of(0, limit))).thenReturn(files);

        List<FileData> result = fileService.getAllFiles(authToken, limit);

        Assertions.assertEquals(files.size(), result.size());
        Assertions.assertEquals(files.get(0).getFilename(), result.get(0).getFilename());
        Assertions.assertArrayEquals(files.get(0).getFileData(), result.get(0).getFileData());
        Assertions.assertEquals(files.get(1).getFilename(), result.get(1).getFilename());
        Assertions.assertArrayEquals(files.get(1).getFileData(), result.get(1).getFileData());
    }
}
