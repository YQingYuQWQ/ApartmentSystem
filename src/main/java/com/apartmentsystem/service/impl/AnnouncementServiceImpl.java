package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.Announcement;
import com.apartmentsystem.mapper.AnnouncementMapper;
import com.apartmentsystem.service.AnnouncementService;
import com.apartmentsystem.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> getAllAnnouncement() {
        return announcementMapper.getAllAnnouncement();
    }

    @Override
    public void insetAnnouncement(Announcement announcement) {
        if(UserHolder.getUser().getRole() ==1)
            throw new RuntimeException("权限不足");
        announcement.setCreated_by(UserHolder.getUser().getId());
        announcementMapper.insertAnnouncement(announcement);
    }

    @Override
    public void updateAnnouncementById(Announcement announcement) {
        if(UserHolder.getUser().getRole() ==1)
            throw new RuntimeException("权限不足");
        announcementMapper.updateAnnouncementById(announcement);
    }

    @Override
    public void deleteAnnouncementById(Integer id) {
        if(UserHolder.getUser().getRole() ==1)
            throw new RuntimeException("权限不足");
        announcementMapper.deleteAnnouncementById(id);
    }
}
