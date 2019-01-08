package com.taskstorage.wireframe.controller;

import com.taskstorage.wireframe.domain.Task;
import com.taskstorage.wireframe.domain.User;
import com.taskstorage.wireframe.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String greeting(Model model) {
        return "welcome";
    }

    @GetMapping("/tasks")
    public String list(Model model) {
        Iterable<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "main";
    }

    @PostMapping("/addTask")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String description,
                      @RequestParam String content,
                      Model model) {

        Task task = new Task(description, content, user);
        taskRepository.save(task);

        return "redirect:/tasks";
    }
    @PostMapping("/delTask/{id}")
    public String delete(@PathVariable Long id, Model model) {

        taskRepository.deleteById(id);

        return "redirect:/tasks";
    }

    @PostMapping("/search")
    public String search(@RequestParam String searchTag, Model model) {

        Iterable<Task> tasks;

        if (searchTag != null && !searchTag.isEmpty()) {
            tasks = taskRepository.findByDescriptionContainingOrContentContaining(searchTag, searchTag);
        } else {
            tasks = taskRepository.findAll();
        }

        model.addAttribute("tasks", tasks);
        return "main";
    }
}
