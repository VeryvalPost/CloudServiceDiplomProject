package netology.cloudserverdiplom.controller;

import lombok.AllArgsConstructor;
import netology.cloudserverdiplom.model.FileData;
import netology.cloudserverdiplom.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class FileController {

    private final FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String filename,
                                             @RequestBody MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        fileService.uploadFile(token, filename, fileBytes);
        logger.info("Request to upload: " + filename);
        return ResponseEntity.ok("Success upload");
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String filename) {
        fileService.deleteFile(token, filename);
        logger.info("Request to delete: " + filename);
        return ResponseEntity.ok("Success deleted");
    }

    @GetMapping(value = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> getFile(@RequestHeader("auth-token") String token,
                                          @RequestParam("filename") String filename) {
        byte[] fileData = fileService.getFile(token, filename);
        logger.info("Request to get: " + filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);


    }

    @PutMapping("/file")
    public ResponseEntity<String> updateFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String filename,
                                             @RequestBody String putName) throws FileNotFoundException {
        fileService.updateFile(token, filename, putName);
        logger.info("Request to edit: " + filename);
        return ResponseEntity.ok("Success update");
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileData>> getAll(@RequestHeader("auth-token") String token,
                                                 @RequestParam("limit") Integer limit) {
        List<FileData> fileList = fileService.getAllFiles(token, limit);
        logger.info("Request to receive all files List");
        return ResponseEntity.ok(fileList);
    }
}