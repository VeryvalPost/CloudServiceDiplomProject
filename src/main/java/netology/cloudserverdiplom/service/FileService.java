package netology.cloudserverdiplom.service;

import netology.cloudserverdiplom.entity.File;
import netology.cloudserverdiplom.error.FileError;
import netology.cloudserverdiplom.error.TokenError;
import netology.cloudserverdiplom.logger.LoggerClass;
import netology.cloudserverdiplom.model.FileData;
import netology.cloudserverdiplom.repository.FileRepo;
import netology.cloudserverdiplom.repository.UserRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileRepo fileRepo;
    private final UserRepo userRepo;
    private static LoggerClass logger = new LoggerClass();

    public FileService(FileRepo fileRepo, UserRepo userRepo) {
        this.fileRepo = fileRepo;
        this.userRepo = userRepo;
    }

    public void uploadFile(String authToken, String filename, MultipartFile file) throws IOException {
        validateToken(authToken);

        byte[] fileBytes = file.getBytes();
        File fileEntity = new File();
        fileEntity.setUser(userRepo.findUserByAuthToken(authToken));
        fileEntity.setAuthToken(authToken);
        fileEntity.setFilename(filename);
        fileEntity.setFileData(fileBytes);
        fileRepo.saveAndFlush(fileEntity);
        logger.writeLog("Success upload " + filename);
    }

    public void deleteFile(String authToken, String filename) {
        validateToken(authToken);

        if (fileRepo.existsByFilename(filename)) {
            fileRepo.deleteByAuthTokenAndFilename(authToken, filename);
        } else throw new FileError("Can't delete. File not found");
    }

    public byte[] getFile(String authToken, String filename) {
        validateToken(authToken);

        byte[] fileEntity = fileRepo.findByAuthTokenAndFilename(authToken, filename).getFileData();
        if (fileEntity == null) {
            throw new FileError("Can't get. File not found");
        }
        return fileEntity;
    }

    public void updateFile(String authToken, String filename, String newName) throws FileNotFoundException {
        validateToken(authToken);

        File fileEntity = fileRepo.findByAuthTokenAndFilename(authToken, filename);
        if (fileEntity == null) {
            throw new FileError("Can't update. File not found");
        }
        fileEntity.setFilename(newName);
        fileRepo.saveAndFlush(fileEntity);
    }

    public List<FileData> getAllFiles(String authToken, Integer limit) {
        validateToken(authToken);

        List<File> files = fileRepo.findByAuthToken(authToken, PageRequest.of(0, limit));

        List<FileData> fileDataList = new ArrayList<>();
        for (File file : files) {
            FileData fileData = new FileData();
            fileData.setAuthToken(file.getAuthToken());
            fileData.setFilename(file.getFilename());
            fileData.setFileData(file.getFileData());
            fileDataList.add(fileData);
        }
        return fileDataList;
    }

    void validateToken(String authToken) {
        if (authToken == null) {
            throw new TokenError("Nullable token");
        }
    }
}