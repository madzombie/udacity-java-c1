package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper=noteMapper;
        this.userMapper=userMapper;
    }

    // list not by user +
    // create a note    +
    // delete a note
    // edit a note

    public Integer getUserId(Authentication authentication) {
        return userMapper.getUser(authentication.getName()).getUserId();
    }

    public List<Note> getNotesByUserId (Authentication authentication) {
        Integer userid = getUserId(authentication);
        return noteMapper.getNotesByUserId(userid);
    }

    public Note getNoteById(Integer noteId) {
        return  noteMapper.getNoteById(noteId);
    }

    public int createNote (Authentication authentication,String title, String desc) {
        Integer userid = getUserId(authentication);
        return noteMapper.createNote(new Note(null,title,desc,userid));
    }
    public void deleteNote (Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
    public void editNote(String title,String desc,Integer noteId) {
        noteMapper.updateNote(title,desc,noteId);
    }


}
