package ru.miyoto.gensys.dao;

import org.springframework.stereotype.Component;
import ru.miyoto.gensys.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDAO {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Student> list() {
        List<Student> students = new ArrayList<>();
        try (Connection c = getConnection()) {
            String sql = "SELECT * FROM student";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getString("patronymic"),
                        resultSet.getInt("course"),
                        resultSet.getString("group"),
                        resultSet.getString("metadata")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student show(int id) {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM student WHERE id=?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
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

    public void addStudent(Student student) {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO student(`name`, lastName, patronymic, " +
                    "course,`group`,metadata) VALUES(?,?,?,?,?,?)");
            ps.setString(1,student.getName());
            ps.setString(2,student.getLastName());
            ps.setString(3,student.getPatronymic());
            ps.setInt(4, student.getCourse());
            ps.setString(5,student.getGroup());
            ps.setString(6,student.getMetadata());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Student updatedStudent) {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("UPDATE student SET `name`=?, lastName=?, patronymic=?," +
                    " course=?, `group`=?, metadata=? WHERE id=?");
            ps.setString(1,updatedStudent.getName());
            ps.setString(2,updatedStudent.getLastName());
            ps.setString(3,updatedStudent.getPatronymic());
            ps.setInt(4, updatedStudent.getCourse());
            ps.setString(5,updatedStudent.getGroup());
            ps.setString(6,updatedStudent.getMetadata());
            ps.setInt(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("DELETE FROM student WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/GensysDB",
                "root",
                "3009"
        );
    }
}
