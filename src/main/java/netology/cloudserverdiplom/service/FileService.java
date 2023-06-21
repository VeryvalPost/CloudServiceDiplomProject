package netology.cloudserverdiplom.service;

import netology.cloudserverdiplom.entity.File;
import netology.cloudserverdiplom.entity.User;
import netology.cloudserverdiplom.model.FileData;
import netology.cloudserverdiplom.repository.AuthorizeRepo;
import netology.cloudserverdiplom.repository.FileRepo;
import netology.cloudserverdiplom.repository.UserRepo;
import netology.cloudserverdiplom.security.JWTUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileRepo fileRepo;
    private final UserRepo userRepo;
    private AuthorizeRepo authorizeRepo;
    private JWTUtil jwtUtil;

    public FileService(FileRepo fileRepo, UserRepo userRepo) {
        this.fileRepo = fileRepo;
        this.userRepo = userRepo;
    }

    public void uploadFile(String authToken, String filename, MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        File fileEntity = new File();
        fileEntity.setUser(userRepo.findUserByAuthToken(authToken));
        fileEntity.setAuthToken(authToken);
        fileEntity.setFilename(filename);
        fileEntity.setFileData(fileBytes);
        fileRepo.saveAndFlush(fileEntity);

    }

    public void deleteFile(String authToken, String filename) {
        File fileEntity = fileRepo.findByAuthTokenAndFilename(authToken, filename);
        fileRepo.delete(fileEntity);
    }

    public Resource getFile(String authToken, String filename) throws FileNotFoundException {
        File fileEntity = fileRepo.findByAuthTokenAndFilename(authToken, filename);
        if (fileEntity == null) {
            throw new FileNotFoundException("File not found");
        }
        String filePath = "/path/to/files/" + filename;
        return new FileSystemResource(filePath);
    }

    public void updateFile(String authToken, String filename, String newName) throws FileNotFoundException {
        File fileEntity = fileRepo.findByAuthTokenAndFilename(authToken, filename);
        if (fileEntity == null) {
            throw new FileNotFoundException("File not found");
        }
        fileEntity.setFilename(newName);
        fileRepo.saveAndFlush(fileEntity);
    }

    public List<FileData> getAllFiles(String authToken, Integer limit) {

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
}