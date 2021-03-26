package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService=credentialService;
    }

    @PostMapping("/add")
    public String createNote (@ModelAttribute Credential credential, Model model, Authentication authentication) {
        int credentialId=-1;
        boolean update =false;
        String signupError=null;
        if (credential.getCredentialId()!=null) {
            credentialId=credential.getCredentialId();
            System.out.println("createNote:id:"+credentialId);
            if (credentialService.getCredentialById(credentialId)!=null) {
                credentialService.editCredential(credential.getUrl(),credential.getUsername(),credential.getKey(),credential.getPassword(),credentialId);
                update=true;
                System.out.println("createNote:update:ok");
            } else {
                System.out.println("createNote:update:false");
            }
        } else {
            System.out.println("createNote:null");
        }

        if (update==false) {
            int dbId = credentialService.createCredential(authentication,credential.getUrl(),credential.getUsername(),credential.getKey(),credential.getPassword());


            if (dbId < 1) {
                signupError = "Note no created";
            }
        }
        if (signupError==null) {
            model.addAttribute("success",true);

        }else {
            model.addAttribute("uploadError",signupError);
        }
        return "result";
    }

    @GetMapping("/credential-delete/{credentialId}")
    public String deleteCredential (@PathVariable("credentialId") Integer credentialId, Model model) {
        String signupError=null;
        if (credentialId<1) {
            signupError="Please select a note for delete";
        } else {
            credentialService.deleteCredential(credentialId);
        }
        if (signupError==null) {
            model.addAttribute("success",true);

        }else {
            model.addAttribute("uploadError",signupError);
        }
        return "result";
    }
}
