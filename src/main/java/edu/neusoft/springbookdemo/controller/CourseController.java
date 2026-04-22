package edu.neusoft.springbookdemo.controller;

import edu.neusoft.springbookdemo.entity.Chapter;
import edu.neusoft.springbookdemo.entity.Course;
import edu.neusoft.springbookdemo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 学生侧的课程浏览（教师也可用）。
 */
@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public String list(Model model) {
        model.addAttribute("courses", courseService.listAll());
        return "courses/list";
    }

    @GetMapping("/courses/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Course course = courseService.get(id);
        if (course == null) return "redirect:/courses";
        List<Chapter> chapters = courseService.chaptersOf(id);
        model.addAttribute("course", course);
        model.addAttribute("chapters", chapters);
        return "courses/detail";
    }

    @GetMapping("/chapters/{id}")
    public String chapter(@PathVariable Long id, Model model) {
        Chapter ch = courseService.chapter(id);
        if (ch == null) return "redirect:/courses";
        Course course = courseService.get(ch.getCourseId());
        model.addAttribute("chapter", ch);
        model.addAttribute("course", course);
        return "courses/chapter";
    }
}
