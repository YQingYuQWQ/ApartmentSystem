package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Announcement;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.AnnouncementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementServiceImpl announcementServiceImpl;

    @PostMapping("/getAllAnnouncement")
    public Result getAllAnnouncement() {
        return Result.success(announcementServiceImpl.getAllAnnouncement());
    }

    @PostMapping("/insertAnnouncement")
    public Result insertAnnouncement(@RequestBody Announcement announcement) {
        announcementServiceImpl.insetAnnouncement(announcement);
        return Result.success();
    }

    @PostMapping("/updateAnnouncementById")
    public Result updateAnnouncementById(@RequestBody Announcement announcement) {
        announcementServiceImpl.updateAnnouncementById(announcement);
        return Result.success();
    }

    @PostMapping("/deleteAnnouncementById")
    public Result deleteAnnouncementById(@RequestBody Announcement announcement) {
        announcementServiceImpl.deleteAnnouncementById(announcement.getId());
        return Result.success();
    }
}
