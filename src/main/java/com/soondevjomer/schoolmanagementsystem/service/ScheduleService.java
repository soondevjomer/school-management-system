package com.soondevjomer.schoolmanagementsystem.service;

import com.soondevjomer.schoolmanagementsystem.dto.ScheduleDto;
import org.springframework.data.domain.Page;

public interface ScheduleService {

    ScheduleDto addSchedule(ScheduleDto scheduleDto);

    ScheduleDto getSchedule(Integer scheduleId);

    Page<ScheduleDto> getSchedules(Integer page, Integer size, String sortField, String sortOrder);

    ScheduleDto updateSchedule(Integer scheduleId, ScheduleDto scheduleDto);

    String deleteSchedule(Integer scheduleId);
}
