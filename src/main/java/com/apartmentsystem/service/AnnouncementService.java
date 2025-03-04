package com.apartmentsystem.service;

import com.apartmentsystem.entity.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllAnnouncement();
    void insetAnnouncement(Announcement announcement);
    void updateAnnouncementById(Announcement announcement);
    void deleteAnnouncementById(Integer id);
}
