package netology.cloudserverdiplom.repository;

import netology.cloudserverdiplom.entity.File;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepo extends JpaRepository<File, Integer> {
    boolean existsByAuthTokenAndFilename(String authToken, String filename);
    void deleteByAuthTokenAndFilename(String authToken, String filename);
    byte[] findByAuthTokenAndFilename(String authToken, String filename);
    File findFileByAuthTokenAndFilename(String authToken, String filename);
    List<File> findByAuthToken(String authToken, Pageable pageable);
}