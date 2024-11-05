package com.example1.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example1.entity.Regi;
import com.example1.exception.ResourceNotFound;
import com.example1.payload.RegiDto;
import com.example1.repository.RegiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegiServiceImpl implements RegiService {

    private final RegiRepository regiRepository;

    public RegiServiceImpl(RegiRepository regiRepository) {
        this.regiRepository = regiRepository;
    }

    @Override
    public RegiDto createRegi(RegiDto regiDto) {
        Regi regi = mapToEntity(regiDto);
        Regi savedEntity = regiRepository.save(regi);
        RegiDto   dto = mapToDto(savedEntity);
        dto.setMessage("data saved");

        return dto;
    }

    @Override
    public void deleteRegiByid(Long id) {
        regiRepository.deleteById(id);
    }

    @Override
    public RegiDto updateRegi(long id, RegiDto regiDto) {
        Optional<Regi> opReg = regiRepository.findById(id);
        Regi regi = opReg.get();

        regi.setName(regiDto.getName());
        regi.setEmail(regiDto.getEmail());
        regi.setMobile(regiDto.getMobile());
        Regi saveEntity = regiRepository.save(regi);
         RegiDto dto =mapToDto(regi);
        return dto ;
    }

//Pagination implementation in retrieve  section//
    @Override
    public List<RegiDto> getAllRegistrations(int pageNo, int pageSize, String sortBy, String sortDir,  String  filterValue) {
    // List<Regi> regi = regiRepository.findAll();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);//ternary operator

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort );
        Page<Regi> all = regiRepository.findAll(pageable);
        List<Regi> regi = all.getContent();
        List<RegiDto> regiDto = regi.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
        System.out.println(all.getTotalPages());//total page
        System.out.println(all.isLast()) ;//last page in the table?
        System.out.println(all.isFirst()) ;//first page in the table?
        System.out.println(pageable.getPageSize()) ;//size
        return regiDto;




    }
// fetch by id //
    @Override
    public RegiDto getRegiById(long id) {
        Regi regi = regiRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Regi not found with id:" + id)
        );
        RegiDto regiDto = mapToDto(regi);
        return regiDto;
    }






    Regi mapToEntity(RegiDto dto) {
        Regi entity = new Regi();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMobile(dto.getMobile());
        dto.setId(dto.getId());
        return entity;
    }


    RegiDto mapToDto(Regi regi) {
        RegiDto dto = new RegiDto();
        dto.setName(regi.getName());
        dto.setEmail(regi.getEmail());
        dto.setMobile(regi.getMobile());
        dto.setId(regi.getId());

        return dto;
    }
}