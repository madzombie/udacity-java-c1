package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService=fileService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile file, Authentication authentication, Model model) {
        String uploadError = null;
        System.out.println(authentication.getName()+":"+file.getOriginalFilename());
        if (file.isEmpty()) {
            uploadError="file not found";
        } else if (fileService.isFileAvailable(authentication,file.getOriginalFilename())) {
            uploadError="File already exists";
        } else {
            try {
                int sonuc = fileService.createFile(file,authentication);
                if (sonuc < 1) {
                    uploadError="File can't upload";
                }
            } catch (IOException e) {
                uploadError="file service error";
                e.printStackTrace();
            }
        }

        if(uploadError==null) {
            model.addAttribute("success",true);
        } else {
            model.addAttribute("uploadError",uploadError);
        }

        return "result";
    }

    @GetMapping("file-delete/{fileId}")
    public String deleteFile (@PathVariable("fileId") Integer fileId,Model model) {
        if (fileId>0) {
            fileService.deleteFile(fileId);
            model.addAttribute("success",true);
        } else {
            model.addAttribute("uploadError","Can not delete file");
        }
        return "result";
    }

    @GetMapping("/file-download/{fileId}")
    public ResponseEntity fileDownload(@PathVariable("fileId") Integer fileId) {
        File fileDownload = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDownload.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ fileDownload.getFileName()+"\"")
                .body(new ByteArrayResource(fileDownload.getFileData()));
    }

}
