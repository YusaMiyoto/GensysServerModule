package ru.miyoto.gensys.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Student {
    //private static int idCounter = 0;

    private int id;

    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Lastname shouldn't be empty!")
    @Size(min = 2, max = 30, message = "Lastname should be between 2 and 30 characters")
    private String lastName;
    @NotEmpty(message = "Patronymic shouldn't be empty!")
    @Size(min = 2, max = 30, message = "Patronymic should be between 2 and 30 characters")
    private String patronymic;
    @Min(value = 1, message = "Course should be greater than 1")
    @Max(value = 4, message = "Course should be less than 4")
    private int course;
    @NotEmpty(message = "Group shouldn't be empty!")
    @Size(min = 2, max = 7, message = "Group should be between 2 and 7 characters")
    private String group;
    @NotEmpty(message = "Metadata shouldn't be empty!")
    private String metadata;

    public Student(String name, String lastName, String patronymic, int course, String group, String metadata) {
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.course = course;
        this.group = group;
        this.metadata = metadata;
    }

    public Student(int id, String name, String lastName, String patronymic, int course, String group, String metadata) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.course = course;
        this.group = group;
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", course=" + course +
                ", group='" + group + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
