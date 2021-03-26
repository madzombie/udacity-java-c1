package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController (FileService fileService,NoteService noteService,CredentialService credentialService,EncryptionService encryptionService) {
        this.fileService=fileService;
        this.noteService=noteService;
        this.credentialService=credentialService;
        this.encryptionService=encryptionService;
    }


    @GetMapping
    public String homeView (@ModelAttribute("file") File file, Authentication authentication, Model model) {
        model.addAttribute("fileList",fileService.getAllFilesByUserId(authentication));
        model.addAttribute("noteList",noteService.getNotesByUserId(authentication));
        model.addAttribute("credentialList",credentialService.getAllCredential(authentication));
        model.addAttribute("decryptePasswordFunc",encryptionService);
        return "home";
    }

}
