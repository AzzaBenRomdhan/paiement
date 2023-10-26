package com.example.paymentms.Controller;

import com.example.paymentms.Model.Formation;
import com.example.paymentms.Model.ImageForm;
import com.example.paymentms.Repo.FormationRepository;
import com.example.paymentms.Repo.ImageRepository;
import com.example.paymentms.services.ImageUploadResponse;
import com.example.paymentms.utile.ImageUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ImgController {
    private  final FormationRepository formationRepository;
    private final ImageRepository imageRepository;
    @Transactional
    @PostMapping({"/upload/{idFormation}"})
    public ResponseEntity<ImageUploadResponse> uplaodImage(@RequestParam("image") MultipartFile file, @PathVariable Integer idFormation) throws IOException {
            Formation formation =formationRepository.findById(idFormation).orElse(null);
            ImageForm imageForm= imageRepository.save(ImageForm.builder().name(file.getOriginalFilename()).type(file.getContentType()).image(ImageUtility.compressImage(file.getBytes())).build());
            if(formation != null){
                imageForm.setFormation(formation);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ImageUploadResponse("Image uploaded successfully: " + file.getOriginalFilename()));
        }

    @GetMapping( path = {"/get/image/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable String name) throws IOException {
        Optional<ImageForm> dbImage = this.imageRepository.findByName(name);
        return ResponseEntity.ok().contentType(MediaType.valueOf(((ImageForm)dbImage.get()).getType())).body(ImageUtility.decompressImage(((ImageForm)dbImage.get()).getImage()));
    }

    @GetMapping("/img/{idFormation}")
    public ResponseEntity<byte[]> getImageByPostBlogId(@PathVariable Integer idFormation) throws IOException {
        Optional<ImageForm> dbImage = imageRepository.findByFormation_IdFormation(idFormation);
        if (dbImage.isPresent()) {
            ImageForm imageForm = dbImage.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(imageForm.getType()))
                    .body(ImageUtility.decompressImage(imageForm.getImage()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
