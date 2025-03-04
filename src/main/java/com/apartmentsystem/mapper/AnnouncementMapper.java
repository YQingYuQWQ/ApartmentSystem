package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {
    List<Announcement> getAllAnnouncement();
    void insertAnnouncement(Announcement announcement);
    void updateAnnouncementById(Announcement announcement);
    void deleteAnnouncementById(Integer id);
}
