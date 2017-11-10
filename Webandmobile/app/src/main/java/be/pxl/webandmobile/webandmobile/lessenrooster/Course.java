package be.pxl.webandmobile.webandmobile.lessenrooster;

import java.time.LocalDate;

/**
 * Created by René on 07/11/2017.
 */

public class Course {
    private LocalDate date;
    private int startHour;
    private int endHour;
    private String classRoom;
    private String olod;
    private String codeTeacher;

    public Course(LocalDate date, int startHour, int endHour, String classRoom, String olod, String codeTeacher) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.classRoom = classRoom;
        this.olod = olod;
        this.codeTeacher = codeTeacher;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getOlod() {
        return olod;
    }

    public void setOlod(String olod) {
        this.olod = olod;
    }

    public String getCodeTeacher() {
        return codeTeacher;
    }

    public void setCodeTeacher(String codeTeacher) {
        this.codeTeacher = codeTeacher;
    }

    @Override
    public String toString() {
        return olod + " - " + codeTeacher + " - " + classRoom;
    }
}
