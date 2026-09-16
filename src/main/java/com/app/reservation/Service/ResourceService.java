package com.app.reservation.Service;

import com.app.reservation.repository.ResourceRepository;
import com.app.reservation.dto.ResourceRequest;
import com.app.reservation.dto.ResourceResponse;
import com.app.reservation.dto.mappers.ResourceMapper;
import com.app.reservation.exception.AccessDeniedException;
import com.app.reservation.exception.ResourceNotFoundException;
import com.app.reservation.models.Resource;
import com.app.reservation.models.enums.ResourceType;
import com.app.reservation.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private UserService userService;

    public Resource findResourceByIdEntity(Long resourceId) {
        return resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
    public ResourceResponse findById(Long id){
        return resourceRepository.findById(id).map(resourceMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
    public  ResourceResponse createResource(ResourceRequest resourceRequest,Principal principal){
        if(userService.findByEmailEntity(principal.getName()).getRole().equals(Role.ADMIN)){
            Resource resource = resourceRepository.save(resourceMapper.toEntity(resourceRequest));
            return resourceMapper.toResponse(resource);
        }else throw new AccessDeniedException("Access Denied");
    }
    @Transactional
    public ResourceResponse updateResource(ResourceRequest resourceRequest, Long id, Principal principal){
        Resource resource = resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        if(userService.findByEmailEntity(principal.getName()).getRole().equals(Role.ADMIN)){
            resource.setName(resourceRequest.getName());
            resource.setCapacity(resourceRequest.getCapacity());
            resource.setDescription(resourceRequest.getDescription());
            resource.setActive(resourceRequest.isActive());
            return resourceMapper.toResponse(resource);
        }else throw new AccessDeniedException("Access Denied");
    }
    public List<ResourceResponse> getAllResources(){
        return resourceRepository.findAll().stream().filter(resource -> resource.isActive()).map(resourceMapper::toResponse).collect(Collectors.toList());
    }
    public List<ResourceResponse> getResourceByType(ResourceType resourceType){
        return resourceRepository.findByResourceType(resourceType).stream().filter(resource -> resource.isActive()).map(resourceMapper::toResponse).collect(Collectors.toList());
    }

}
