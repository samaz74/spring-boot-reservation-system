package com.app.reservation.repository;

import com.app.reservation.models.Resource;
import com.app.reservation.models.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findByName(String name);
    List<Resource> findByNameContaining(String name);
    List<Resource> findByResourceType(ResourceType resourceType);

}
