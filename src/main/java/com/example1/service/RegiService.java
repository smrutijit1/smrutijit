package com.example1.service;

import com.example1.payload.RegiDto;

import java.util.List;

public interface RegiService {

public RegiDto createRegi(RegiDto regiDto);


    void deleteRegiByid(Long id);


    RegiDto updateRegi(long id, RegiDto regiDto);

    List<RegiDto> getAllRegistrations(int pageNo, int pageSize, String sortBy, String sortDir, String filterValue);


    RegiDto getRegiById(long id);
}
