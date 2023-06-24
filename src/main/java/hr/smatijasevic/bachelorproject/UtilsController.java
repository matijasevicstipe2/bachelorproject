package hr.smatijasevic.bachelorproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UtilsController {

    @Value("${upload.path}") // Specify the directory where the profile pictures will be stored
    private String uploadPath;

    @PostMapping("/uploadpicture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file) {
        try {
            // Perform validation, file size check, etc. if required

            // Save the file to the upload directory
            String fileName = file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));

            // Return the profile picture URL or any other response as needed
            String profilePictureUrl = "http://example.com/profile-pictures/" + fileName;
            return ResponseEntity.ok().body(profilePictureUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile picture.");
        }
    }
}