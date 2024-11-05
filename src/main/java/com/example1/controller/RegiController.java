package com.example1.controller;

import com.example1.entity.Regi;
import com.example1.payload.RegiDto;
import com.example1.service.RegiService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v3/regi")
@RestController
public class RegiController {
    private RegiService regiService;
    public RegiController(RegiService regiService) {
        this.regiService = regiService;
    }



    @PostMapping
  public ResponseEntity<?> addRegi(  @Valid   @RequestBody RegiDto regiDto,
BindingResult result

    ) {
        if(result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.OK) ;
        }

        RegiDto regDto = regiService.createRegi(regiDto);
        return new ResponseEntity<>(regiDto, HttpStatus.CREATED);
}







//delete
//http://localhost:8080/api/v3/regi?id=21
@DeleteMapping
public ResponseEntity<String>deleteRegiByid(@RequestParam  Long  id ){
        regiService.deleteRegiByid(id);
        return new ResponseEntity<>("Regi deleted", HttpStatus.OK);
}
//update
    @PutMapping
public ResponseEntity<RegiDto>updateRegi(@RequestParam  long id, @RequestBody   RegiDto regiDto){
   RegiDto dto =  regiService.updateRegi(id  , regiDto);
   return new ResponseEntity<>(dto ,HttpStatus.OK);
    }
//retrieve & Pagination implementation
//http://localhost:8080/api/v3/regi?pageNo&pageSize=5
//localhost:8080/api/v3/regi?pageNo=0&pageSize=5&sortBy=id
//http://localhost:8080/api/v3/regi?pageNo=0&pageSize=5&sortBy=email&sortDir=desc
//http://localhost:8080/api/v3/regi?pageNo=0&pageSize=5&sortBy=email&sortDir=asc
//http://localhost:8080/api/v3/regi?pageNo=0&pageSize=5&sortBy=email&sortDir=desc
@GetMapping
public ResponseEntity<List<RegiDto>> getAllRegistrations(
        @RequestParam(name="pageNo", defaultValue="0", required = false) int pageNo,
        @RequestParam(name="pageSize", defaultValue="5", required = false) int pageSize,
        @RequestParam(name="sortBy", defaultValue="name", required = false) String sortBy,
        @RequestParam(name="sortDir", defaultValue="asc", required = false) String sortDir,
        @RequestParam(name="filter", required = false) String filterValue // Add filter parameter

){
    List<RegiDto> dtos =  regiService.getAllRegistrations(pageNo,pageSize,sortBy,sortDir,filterValue );
    return  new ResponseEntity<>(dtos,HttpStatus.OK);//(pageNo, pageSize, sortBy, sortDir, filterValue)


    }
@GetMapping("/byid")
public ResponseEntity<RegiDto>getRegiById(@RequestParam long id){

RegiDto   dto = regiService.getRegiById(id);
return new ResponseEntity<>(dto,HttpStatus.OK);
}


}





