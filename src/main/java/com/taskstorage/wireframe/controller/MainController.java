package com.taskstorage.wireframe.controller;

import com.taskstorage.wireframe.domain.Task;
import com.taskstorage.wireframe.domain.User;
import com.taskstorage.wireframe.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private TaskRepository taskRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Model model) {
        return "welcome";
    }

    @GetMapping("/tasks")
    public String list(@RequestParam(required = false, defaultValue = "") String searchTag, Model model) {

        Iterable<Task> tasks;

        if (searchTag != null && !searchTag.isEmpty()) {
            tasks = taskRepository.findByDescriptionContainingOrContentContaining(searchTag, searchTag);
        } else {
            tasks = taskRepository.findAll();
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("searchTag", searchTag);
        return "main";
    }

    @PostMapping("/tasks")
    public String add(@AuthenticationPrincipal User user,
                      @Valid Task task,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file) throws IOException {

        task.setAuthor(user);

        if (bindingResult.hasErrors()) {
            //Смотри ControllerUtils
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            //Добавляем ошибки в модель
            model.mergeAttributes(errorsMap);
            //Заполняем поля в форме добавления чтоб не вводить заново
            model.addAttribute("task", task);
            // Вытягиваем все объекты из репозитория и кладём в модель
            Iterable<Task> tasks = taskRepository.findAll();
            model.addAttribute("tasks", tasks);
            //Возвращаем модель
            return "main";

        } else {
            //Если ошибок валидации нет - редиректим чтоб сообщение не дублировалось при перезагрузке
            saveFile(task, file);
            taskRepository.save(task);
            return "redirect:/tasks";
        }

    }

    @PostMapping("/delTask/{id}")
    public String delete(@PathVariable Long id) {

        attachedFileDelete(id);

        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }

    private void attachedFileDelete(Long id) {
        Optional<Task> parentTask = taskRepository.findById(id);
        if (parentTask !=null){
            String fileToDelete = parentTask.get().getFilename();
            if (fileToDelete != null){
            File file = new File(uploadPath + "/" + fileToDelete);
            file.delete();}
        }
    }

    private void saveFile(@Valid Task task, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            task.setFilename(resultFilename);
        }
    }

    @GetMapping("/personal-tasks/{user}")
    public String personalTasks(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Task task

    ) {
        Set<Task> tasks = user.getTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", task);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userTasks";
    }

    @PostMapping("/personal-tasks/{user}")
    public String updateTask(@AuthenticationPrincipal User currentUser,
                             @PathVariable Long user,
                             @RequestParam("id") Task task,
                             @RequestParam("description") String description,
                             @RequestParam("content") String content,
                             @RequestParam("file") MultipartFile file

    ) throws IOException {
        if (task != null){
            if (task.getAuthor().equals(currentUser)) {
                if(!StringUtils.isEmpty(description)) {
                    task.setDescription(description);
                }if(!StringUtils.isEmpty(content)) {
                    task.setContent(content);
                }
                attachedFileDelete(task.getId());
                task.setFilename(null);
                if (!file.getOriginalFilename().isEmpty())
                {
                    saveFile(task, file);
                }

                taskRepository.save(task);
            }
        }
        return "redirect:/personal-tasks/" + user;
    }
}
