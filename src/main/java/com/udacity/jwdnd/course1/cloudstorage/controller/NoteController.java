package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService=noteService;
    }
    @PostMapping("/add")
    public String createNote (@ModelAttribute Note note, Model model,Authentication authentication) {
        int noteId=-1;
        boolean update =false;
        String signupError=null;
        if (note.getNoteId()!=null) {
            noteId=note.getNoteId();
            if (noteService.getNoteById(noteId)!=null) {
                try {

                    if (note.getNoteTitle().length()>999 || note.getNoteDescription().length()>999) {
                        signupError="Note can't be saved as description exceed 1000 characters";
                    } else {
                        if (noteService.isNoteExistsByUserId(authentication,note)==true) {
                            signupError="Note already exists...";
                        } else {
                            noteService.editNote(note.getNoteTitle(), note.getNoteDescription(), noteId);
                        }
                    }
                } catch (Exception ex) {
                    signupError=ex.getMessage();
                }

                update=true;
            }
        }

        if (update==false) {
            int dbId=0;
            try {
                if (note.getNoteTitle().length()>999 || note.getNoteDescription().length()>999) {
                        signupError="Note can't be saved as description exceed 1000 characters";
                } else {
                    if (noteService.isNoteExistsByUserId(authentication,note)==true) {
                        signupError="Note conflicts.. because Note already exists...";
                    } else {
                        dbId = noteService.createNote(authentication, note.getNoteTitle(), note.getNoteDescription());
                    }

                }
            } catch (Exception ex) {
                signupError=ex.getMessage();
            }


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

    @GetMapping("/note-delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId,Model model) {
        String signupError=null;
        if (noteId<1) {
            signupError="Please select a note for delete";
        } else {
            noteService.deleteNote(noteId);
        }
        if (signupError==null) {
            model.addAttribute("success",true);

        }else {
            model.addAttribute("uploadError",signupError);
        }

        return "result";
    }


}
