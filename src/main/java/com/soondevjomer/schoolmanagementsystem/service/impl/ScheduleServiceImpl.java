package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.*;
import com.soondevjomer.schoolmanagementsystem.entity.ClassSection;
import com.soondevjomer.schoolmanagementsystem.entity.Schedule;
import com.soondevjomer.schoolmanagementsystem.entity.Teacher;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.ClassSectionRepository;
import com.soondevjomer.schoolmanagementsystem.repository.ScheduleRepository;
import com.soondevjomer.schoolmanagementsystem.repository.TeacherRepository;
import com.soondevjomer.schoolmanagementsystem.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClassSectionRepository classSectionRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    @Override
    public ScheduleDto addSchedule(ScheduleDto scheduleDto) {

        ClassSection classSection = classSectionRepository.findById(scheduleDto.getClassSectionDto().getId())
                .orElseThrow(()->new NoRecordFoundException(
                        "Class Section", "id", scheduleDto.getClassSectionDto().getId().toString()));

        Teacher teacher = teacherRepository.findById(scheduleDto.getTeacherDto().getId())
                .orElseThrow(()->new NoRecordFoundException("Teacher", "id", scheduleDto.getId().toString()));

        Schedule schedule = new Schedule();
        schedule.setClassSection(classSection);
        schedule.setTeacher(teacher);
        schedule.setDayOfWeek(schedule.getDayOfWeek());
        schedule.setTimeIn(schedule.getTimeIn());
        schedule.setTimeOut(schedule.getTimeOut());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return createScheduleDtoResponse(savedSchedule);
    }

    @Override
    public ScheduleDto getSchedule(Integer scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new NoRecordFoundException("Schedule", "id", scheduleId.toString()));

        return createScheduleDtoResponse(schedule);
    }

    @Override
    public Page<ScheduleDto> getSchedules(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return scheduleRepository.findAll(pageable)
                .map(this::createScheduleDtoResponse);
    }

    @Override
    public ScheduleDto updateSchedule(Integer scheduleId, ScheduleDto scheduleDto) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new NoRecordFoundException("Schedule", "id", scheduleId.toString()));

        if (!Objects.equals(schedule.getTeacher().getId(), scheduleDto.getTeacherDto().getId())) {
            // There is changes in teacher;
            Teacher teacher = teacherRepository.findById(scheduleDto.getTeacherDto().getId())
                    .orElseThrow(()->new NoRecordFoundException("Teacher", "id", scheduleDto.getTeacherDto().getId().toString()));

            schedule.setTeacher(teacher);
        }

        schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        schedule.setTimeIn(scheduleDto.getTimeIn());
        schedule.setTimeOut(scheduleDto.getTimeOut());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return createScheduleDtoResponse(savedSchedule);
    }

    @Override
    public String deleteSchedule(Integer scheduleId) {

        // Now deleting a schedule needs to delete its connection to associated parent
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()->new NoRecordFoundException("Schedule", "id", scheduleId.toString()));

        Teacher teacher = teacherRepository.findById(schedule.getTeacher().getId())
                .orElseThrow(()->new NoRecordFoundException("Teacher", "id", schedule.getTeacher().getId().toString()));

        teacher.getSchedules().forEach(tempSchedule -> {
            if (tempSchedule.getId().equals(schedule.getId()))
                teacher.getSchedules().remove(tempSchedule);
        });
        teacherRepository.save(teacher);

        ClassSection classSection = classSectionRepository.findById(schedule.getClassSection().getId())
                .orElseThrow(()->new NoRecordFoundException("Class Section", "id", schedule.getClassSection().getId().toString()));

        classSection.getSchedules().forEach(tempSchedule -> {
            if (tempSchedule.getId().equals(schedule.getId()))
                classSection.getSchedules().remove(tempSchedule);
        });
        classSectionRepository.save(classSection);

        scheduleRepository.delete(schedule);

        return "Schedule delete successfully.";
    }

    private ScheduleDto createScheduleDtoResponse(Schedule schedule) {

        ClassSectionDto classSectionDto = new ClassSectionDto();
        classSectionDto.setId(schedule.getClassSection().getId());
        classSectionDto.setClassDto(modelMapper.map(schedule.getClassSection().getClass_(), ClassDto.class));
        classSectionDto.setSectionDto(modelMapper.map(schedule.getClassSection().getSection(), SectionDto.class));
        PersonDto personDto = new PersonDto();
        personDto.setId(schedule.getTeacher().getPerson().getId());
        personDto.setNameDto(modelMapper.map(schedule.getTeacher().getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(schedule.getTeacher().getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(schedule.getTeacher().getPerson().getContact(), ContactDto.class));
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(schedule.getTeacher().getId());
        teacherDto.setPersonDto(personDto);

        ScheduleDto scheduleDtoResponse = new ScheduleDto();
        scheduleDtoResponse.setId(schedule.getId());
        scheduleDtoResponse.setClassSectionDto(classSectionDto);
        scheduleDtoResponse.setTeacherDto(teacherDto);
        scheduleDtoResponse.setDayOfWeek(schedule.getDayOfWeek());
        scheduleDtoResponse.setTimeIn(schedule.getTimeIn());
        scheduleDtoResponse.setTimeOut(schedule.getTimeOut());

        return scheduleDtoResponse;
    }
}
