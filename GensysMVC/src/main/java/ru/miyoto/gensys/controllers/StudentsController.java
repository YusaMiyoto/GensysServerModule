package ru.miyoto.gensys.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.miyoto.gensys.dao.StudentDAO;
import ru.miyoto.gensys.models.Student;

import javax.validation.Valid;

@Controller
@RequestMapping("/students") //старт адрес
public class StudentsController {

    private final StudentDAO studentDAO;
    @Autowired
    public StudentsController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping()
    public String list(Model model){ //передача объектов(всех) представлению
        model.addAttribute("students", studentDAO.list()); //ключ-значение
        return "students/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("student", studentDAO.show(id));
        return "students/showOne"; //возврат какой-либо стр/представления для юзера
    }

    @GetMapping("/new") //форма для создания
    public String newStudent(Model model){ //для таймлиф форм надо передавать объект
        model.addAttribute("student", new Student()); //создаём новый, чтобы потом назначить
        return "students/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("student") @Valid Student student,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "students/new";
        }
        studentDAO.addStudent(student);
        return "redirect:/students"; //перенаправление на сущ. стр
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("student", studentDAO.show(id)); //в поля поместить значения чела
        return "students/edit";

    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "students/edit";
        }
        studentDAO.update(id, student);
        return "redirect:/students";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        studentDAO.delete(id);
        return "redirect:/students";
    }

    public StudentDAO getStudentDAO() {
        return studentDAO;
    }
}
