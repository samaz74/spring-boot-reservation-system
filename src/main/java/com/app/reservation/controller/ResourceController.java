package com.app.reservation.controller;

import com.app.reservation.Service.ResourceService;
import com.app.reservation.dto.ResourceRequest;
import com.app.reservation.dto.ResourceResponse;
import com.app.reservation.models.enums.ResourceType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResourceResponse creatResource(@Valid @RequestBody ResourceRequest resourceRequest, Principal principal){
        return resourceService.createResource(resourceRequest,principal);
    }
    @GetMapping("/{resourceType}")
    public List<ResourceResponse> getResourceActiveReservations(@PathVariable ResourceType resourceType){
        return resourceService.getResourceByType(resourceType);
    }

    @GetMapping("")
    public List<ResourceResponse> getAll(){
        return resourceService.getAllResources();
    }

    @GetMapping("/search/{id}")
    public ResourceResponse getById(@PathVariable Long id){
        return resourceService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResourceResponse update(@PathVariable Long id, @Valid @RequestBody ResourceRequest resourceRequest, Principal principal){
        return resourceService.updateResource(resourceRequest,id,principal);
    }


}
