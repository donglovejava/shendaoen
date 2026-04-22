package edu.neusoft.springbookdemo.controller;

import edu.neusoft.springbookdemo.entity.Checkin;
import edu.neusoft.springbookdemo.entity.User;
import edu.neusoft.springbookdemo.service.CheckinService;
import edu.neusoft.springbookdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CheckinController {

    private final CheckinService checkinService;
    private final UserService userService;

    // ============ 教师：发起签到 ============

    @PostMapping("/teacher/courses/{courseId}/checkin")
    public String start(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long courseId,
            @RequestParam(required = false) Integer minutes,
            Model model,
            RedirectAttributes ra
    ) {
        User teacher = userService.getByUsername(principal.getUsername());
        try {
            Checkin c = checkinService.start(courseId, teacher.getId(), minutes);
            model.addAttribute("checkin", c);
            model.addAttribute("courseId", courseId);
            return "teacher/checkin-started";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/teacher/courses/" + courseId;
        }
    }

    // ============ 学生：输入签到码签到 ============

    @GetMapping("/checkin")
    public String page() {
        return "student/checkin";
    }

    @PostMapping("/checkin")
    public String submit(
            @AuthenticationPrincipal UserDetails principal,
            @RequestParam String code,
            Model model
    ) {
        User user = userService.getByUsername(principal.getUsername());
        try {
            boolean first = checkinService.doCheckin(code.trim(), user.getId());
            model.addAttribute("success", first);
            model.addAttribute("message", first
                    ? "签到成功！获得 " + CheckinService.CHECKIN_SCORE + " 分"
                    : "你已经签过到了，不能重复签到");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "student/checkin";
    }
}
