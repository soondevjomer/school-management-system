package com.soondevjomer.schoolmanagementsystem.service.impl;

import com.soondevjomer.schoolmanagementsystem.dto.*;
import com.soondevjomer.schoolmanagementsystem.entity.*;
import com.soondevjomer.schoolmanagementsystem.exception.NoRecordFoundException;
import com.soondevjomer.schoolmanagementsystem.repository.AdminRepository;
import com.soondevjomer.schoolmanagementsystem.repository.PersonRepository;
import com.soondevjomer.schoolmanagementsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public AdminDto addAdmin(AdminDto adminDto) {

        Person person = new Person();
        person.setName(modelMapper.map(adminDto.getPersonDto().getNameDto(), Name.class));
        person.setAddress(modelMapper.map(adminDto.getPersonDto().getAddressDto(), Address.class));
        person.setContact(modelMapper.map(adminDto.getPersonDto().getContactDto(), Contact.class));
        Person savedPerson = personRepository.save(person);

        Admin admin = new Admin();
        admin.setPerson(savedPerson);
        Admin savedAdmin = adminRepository.save(admin);

        return createAdminDtoResponse(savedAdmin);
    }

    @Override
    public AdminDto getAdmin(Integer adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()->new NoRecordFoundException("Admin", "id", adminId.toString()));

        return createAdminDtoResponse(admin);
    }

    @Override
    public Page<AdminDto> getAdmins(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, direction, sortField);

        return adminRepository.findAll(pageable)
                .map(this::createAdminDtoResponse);
    }

    @Override
    public AdminDto updateAdmin(Integer adminId, AdminDto adminDto) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()->new NoRecordFoundException("Admin", "id", adminId.toString()));

        admin.getPerson().setName(modelMapper.map(adminDto.getPersonDto().getNameDto(), Name.class));
        admin.getPerson().setAddress(modelMapper.map(adminDto.getPersonDto().getAddressDto(), Address.class));
        admin.getPerson().setContact(modelMapper.map(adminDto.getPersonDto().getContactDto(), Contact.class));
        Admin updatedAdmin = adminRepository.save(admin);

        return createAdminDtoResponse(updatedAdmin);
    }

    @Override
    public String deleteAdmin(Integer adminId) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()->new NoRecordFoundException("Admin", "id", adminId.toString()));

        adminRepository.delete(admin);

        return "Admin deleted successfully";
    }

    private AdminDto createAdminDtoResponse(Admin admin) {

        PersonDto personDto = new PersonDto();
        personDto.setId(admin.getPerson().getId());
        personDto.setNameDto(modelMapper.map(admin.getPerson().getName(), NameDto.class));
        personDto.setAddressDto(modelMapper.map(admin.getPerson().getAddress(), AddressDto.class));
        personDto.setContactDto(modelMapper.map(admin.getPerson().getContact(), ContactDto.class));
        AdminDto adminDtoResponse = new AdminDto();
        adminDtoResponse.setId(admin.getId());
        adminDtoResponse.setPersonDto(personDto);

        return adminDtoResponse;
    }
}