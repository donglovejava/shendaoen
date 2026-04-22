package edu.neusoft.springbookdemo.controller;

import edu.neusoft.springbookdemo.entity.Course;
import edu.neusoft.springbookdemo.entity.User;
import edu.neusoft.springbookdemo.service.CourseService;
import edu.neusoft.springbookdemo.service.FileStorageService;
import edu.neusoft.springbookdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final CourseService courseService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @GetMapping("/courses")
    public String myCourses(@AuthenticationPrincipal UserDetails principal, Model model) {
        User teacher = userService.getByUsername(principal.getUsername());
        model.addAttribute("courses", courseService.listByTeacher(teacher.getId()));
        model.addAttribute("teacher", teacher);
        return "teacher/courses";
    }

    @PostMapping("/courses")
    public String createCourse(
            @AuthenticationPrincipal UserDetails principal,
            @RequestParam String title,
            @RequestParam(required = false) String description
    ) {
        User teacher = userService.getByUsername(principal.getUsername());
        courseService.create(title, description, teacher.getId());
        return "redirect:/teacher/courses";
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return "redirect:/teacher/courses";
    }

    @GetMapping("/courses/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        Course course = courseService.get(id);
        if (course == null) return "redirect:/teacher/courses";
        model.addAttribute("course", course);
        model.addAttribute("chapters", courseService.chaptersOf(id));
        return "teacher/course-edit";
    }

    @PostMapping("/courses/{id}/chapters")
    public String addChapter(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(required = false) Integer sortOrder,
            @RequestParam(required = false) MultipartFile video,
            @RequestParam(required = false) MultipartFile ppt
    ) {
        String videoPath = fileStorageService.save(video, "course-" + id);
        String pptPath   = fileStorageService.save(ppt,   "course-" + id);
        courseService.addChapter(id, title, sortOrder, videoPath, pptPath);
        return "redirect:/teacher/courses/" + id;
    }

    @PostMapping("/chapters/{id}/delete")
    public String deleteChapter(@PathVariable Long id,
                                @RequestParam Long courseId) {
        courseService.deleteChapter(id);
        return "redirect:/teacher/courses/" + courseId;
    }
}
