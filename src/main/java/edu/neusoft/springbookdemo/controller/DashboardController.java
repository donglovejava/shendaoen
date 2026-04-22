package edu.neusoft.springbookdemo.controller;

import edu.neusoft.springbookdemo.common.Result;
import edu.neusoft.springbookdemo.entity.ScoreRecord;
import edu.neusoft.springbookdemo.entity.User;
import edu.neusoft.springbookdemo.service.CheckinService;
import edu.neusoft.springbookdemo.service.ScoreService;
import edu.neusoft.springbookdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final ScoreService scoreService;
    private final CheckinService checkinService;

    @GetMapping("/scores")
    public String myScores(@AuthenticationPrincipal UserDetails principal, Model model) {
        User user = userService.getByUsername(principal.getUsername());
        List<ScoreRecord> records = scoreService.listByUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("records", records);
        return "student/scores";
    }

    @GetMapping("/teacher/dashboard")
    public String teacherDashboard(Model model) {
        model.addAttribute("todayCheckinUsers", checkinService.todayDistinctUsers());
        model.addAttribute("topStudents", userService.topStudentsByScore(10));
        return "teacher/dashboard";
    }

    @GetMapping("/api/teacher/dashboard/summary")
    @ResponseBody
    public Result<Map<String, Object>> teacherDashboardSummary() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("todayCheckinUsers", checkinService.todayDistinctUsers());
        payload.put("topStudents", userService.topStudentsByScore(10));
        return Result.ok(payload);
    }
}
