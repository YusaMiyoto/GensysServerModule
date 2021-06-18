package ru.miyoto.gensys.bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import ru.miyoto.gensys.dao.StudentDAO;
import ru.miyoto.gensys.models.Student;

public class BotServerFunctions {

    private static String metadata;
    private static String lastMetadata;
    private static Long chatId;
    private static int waitingEntering = 0;
    private static Student newStudent = new Student();
/*
    public BotServerFunctions() {
    }

   public static void main(String[] args) {
        BotServerFunctions func = new BotServerFunctions();
        System.out.println(func.searchPerson());
    }*/

    public static boolean checkUpdates() {
        if (Objects.equals(takeMetadata(), lastMetadata)) {
            return false;
        }
        else {
            lastMetadata = takeMetadata();
            return true;
        }
    }

    public static String searchPerson() {
        String s = takeMetadata();
        StringBuilder result = new StringBuilder();
        if (getStudentByMetadata(s) != null) {
            Student student = getStudentByMetadata(s);
            result.append("University student approaching the checkpoint.\nThis is: ").append(student.getLastName() + " ").
                    append(student.getName() + " ").append(student.getPatronymic() + "\n").append("Course: " + student.getCourse() + "\n").
                    append("Group: " + student.getGroup());

            return result.toString();
        }
        return null;
    }

    public static Student getStudentByMetadata(String s) {
        try (Connection c = StudentDAO.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM student WHERE metadata=?");
            ps.setString(1, s);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getString("patronymic"),
                        resultSet.getInt("course"),
                        resultSet.getString("group"),
                        resultSet.getString("metadata")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addStudent(Student student){
        try (Connection c = StudentDAO.getConnection()){
            PreparedStatement ps = c.prepareStatement("INSERT INTO student(`name`, lastName, patronymic, " +
                    "course,`group`,metadata) VALUES(?,?,?,?,?,?)");
            ps.setString(1,student.getName());
            ps.setString(2,student.getLastName());
            ps.setString(3,student.getPatronymic());
            ps.setInt(4, student.getCourse());
            ps.setString(5,student.getGroup());
            ps.setString(6,student.getMetadata());
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static String takeMetadata() {
        File file = new File("C://Users//810523//Documents//Metadata//metadata.dat");
        if (file.exists() && file.canRead()) {
            if (file.length() != 0) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    metadata = br.readLine();
                    return metadata;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("File does not contain metadata.");
                return null;
            }
        }
        System.out.println("File with metadata does not exist or can not be read.");
        return null;
    }

    public static String getMetadata() {
        return metadata;
    }

    public static void setMetadata(String metadata) {
        BotServerFunctions.metadata = metadata;
    }

    public static String getLastMetadata() {
        return lastMetadata;
    }

    public static void setLastMetadata(String lastMetadata) {
        BotServerFunctions.lastMetadata = lastMetadata;
    }

    public static Long getChatId() {
        return chatId;
    }

    public static void setChatId(Long chatId) {
        BotServerFunctions.chatId = chatId;
    }

    public static int getWaitingEntering() {
        return waitingEntering;
    }

    public static void setWaitingEntering(int waitingEntering) {
        BotServerFunctions.waitingEntering = waitingEntering;
    }

   public static Student getNewStudent() {
        return newStudent;
    }

    public static void setNewStudent(Student newStudent) {
        BotServerFunctions.newStudent = newStudent;
    }
}
