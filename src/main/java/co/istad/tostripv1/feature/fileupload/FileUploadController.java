package co.istad.tostripv1.feature.fileupload;

import co.istad.tostripv1.feature.fileupload.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
public class FileUploadController {
    private final FileUploadService fileUploadService;
    //upload single file
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    FileUploadResponse upload(@RequestPart MultipartFile file) {
        return fileUploadService.upload(file);
    }

    //upload multi-files
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/multiple")
    List<FileUploadResponse> uploadMultiFile(@RequestPart List<MultipartFile> files) {
        System.out.println("Test upload multi files.");
        return fileUploadService.uploadMultiple(files);
    }

    // Delete file
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fileName}")
    void deleteByFileName(@PathVariable String fileName) {
        fileUploadService.deleteByFileName(fileName);
    }
}
