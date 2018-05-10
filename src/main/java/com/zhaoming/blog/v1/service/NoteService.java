package com.zhaoming.blog.v1.service;

import com.zhaoming.blog.v1.model.Note;

/**
 * created by Wuwenbin on 2018/1/17 at 20:56
 */
public interface NoteService {

    boolean postNote(Note note, String tagNames) throws Exception;

    boolean updateNote(Note note, String tagNames) throws Exception;
}
