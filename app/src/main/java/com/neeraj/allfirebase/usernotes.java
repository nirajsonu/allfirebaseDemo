package com.neeraj.allfirebase;

import java.io.Serializable;

public class usernotes implements Serializable {
    String noteTitle="",noteDesc="",noteDates="",noteId="";

    public usernotes(String noteTitle, String noteDesc, String noteDates, String noteId) {
        this.noteTitle = noteTitle;
        this.noteDesc = noteDesc;
        this.noteDates = noteDates;
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDesc() {
        return noteDesc;
    }

    public void setNoteDesc(String noteDesc) {
        this.noteDesc = noteDesc;
    }

    public String getNoteDates() {
        return noteDates;
    }

    public void setNoteDates(String noteDates) {
        this.noteDates = noteDates;
    }

    public String getNoteId() {
        return noteId;
    }
public usernotes()
{

}
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
