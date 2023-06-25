package netology.cloudserverdiplom.service;

import netology.cloudserverdiplom.entity.File;
import netology.cloudserverdiplom.error.FileError;
import netology.cloudserverdiplom.error.TokenError;
import netology.cloudserverdiplom.model.FileData;
import netology.cloudserverdiplom.repository.FileRepo;
import netology.cloudserverdiplom.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileRepo fileRepo;
    private final UserRepo userRepo;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService(FileRepo fileRepo, UserRepo userRepo) {
        this.fileRepo = fileRepo;
        this.userRepo = userRepo;
    }

    public void uploadFile(String authToken, String filename, byte[] fileBytes) throws IOException {
        validateToken(authToken);

        File fileEntity = new File();
        fileEntity.setUser(userRepo.findUserByAuthToken(authToken));
        fileEntity.setAuthToken(authToken);
        fileEntity.setFilename(filename);
        fileEntity.setFileData(fileBytes);
        fileRepo.saveAndFlush(fileEntity);
        logger.info("Success upload " + filename);
    }

    public void deleteFile(String authToken, String filename) {
        validateToken(authToken);

        if (fileRepo.existsByAuthTokenAndFilename(authToken, filename)) {
            fileRepo.deleteByAuthTokenAndFilename(authToken, filename);
        } else throw new FileError("Can't delete. File not found");
    }

    public byte[] getFile(String authToken, String filename) {
        validateToken(authToken);

        if (fileRepo.existsByAuthTokenAndFilename(authToken, filename)) {
            byte[] fileEntity = fileRepo.findByAuthTokenAndFilename(authToken, filename);
            return fileEntity;
        } else {
            throw new FileError("Can't get. File not found");
        }
    }

    public void updateFile(String authToken, String filename, String newName) throws FileNotFoundException {
        validateToken(authToken);

        File fileEntity = fileRepo.findFileByAuthTokenAndFilename(authToken, filename);
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