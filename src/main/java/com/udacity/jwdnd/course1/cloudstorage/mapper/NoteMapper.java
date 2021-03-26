package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES where userId=#{userId}")
    List<Note> getNotesByUserId (Integer userId);

    @Select("SELECT * FROM NOTES where noteId=#{noteId}")
    Note getNoteById (Integer noteId);

    @Delete("DELETE FROM NOTES where noteId=#{noteId}")
    public void deleteNote (Integer noteId);

    @Insert("INSERT INTO NOTES (noteTitle,noteDescription,userId) values (#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "noteId")
    int createNote(Note note);

    @Update("UPDATE NOTES set noteTitle=#{noteTitle}, noteDescription=#{noteDescription} where noteId=#{noteId}")
    public void updateNote(String noteTitle,String noteDescription,Integer noteId);

    @Select("SELECT count(*) FROM NOTES WHERE userId=#{userId} and noteTitle=#{noteTitle} and noteDescription=#{noteDescription}")
    int isExistsNoteByUserId (String noteTitle,String noteDescription,Integer userId);
}
