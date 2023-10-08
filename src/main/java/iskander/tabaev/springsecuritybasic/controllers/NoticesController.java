package iskander.tabaev.springsecuritybasic.controllers;

import iskander.tabaev.springsecuritybasic.models.Notice;
import iskander.tabaev.springsecuritybasic.repositories.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticesController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("/notices")
    public List<Notice> getNotices() {
        List<Notice> notices = noticeRepository.findAllActiveNotices();
        if (notices != null ) {
            return notices;
        }else {
            return null;
        }
    }

}
