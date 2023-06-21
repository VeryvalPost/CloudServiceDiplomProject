package netology.cloudserverdiplom.controller;

import lombok.AllArgsConstructor;
import netology.cloudserverdiplom.model.FileData;
import netology.cloudserverdiplom.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String filename,
                                             @RequestBody MultipartFile file) throws IOException {
        fileService.uploadFile(token, filename, file);
        return ResponseEntity.ok("Success upload");
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String token,
                                             @RequestParam("filename") String filename) {
        fileService.deleteFile(token, filename);
        return ResponseEntity.ok("Success deleted");
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> getFile(@RequestHeader("auth-token") String token,
                                            @RequestParam("filename") String filename) throws FileNotFoundException {
        Resource fileResource = fileService.getFile(token, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(fileResource);
    }

    @PutMapping("/file")
    public ResponseEntity<String> putFile(@RequestHeader("auth-token") String token,
                                          @RequestParam("filename") String filename,
                                          @RequestBody String putName) throws FileNotFoundException {
        fileService.updateFile(token, filename, putName);
        return ResponseEntity.ok("Success update");
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileData>> getAll(@RequestHeader("auth-token") String token,
                                                 @RequestParam("limit") Integer limit) {
        List<FileData> fileList = fileService.getAllFiles(token, limit);
        return ResponseEntity.ok(fileList);
    }
}