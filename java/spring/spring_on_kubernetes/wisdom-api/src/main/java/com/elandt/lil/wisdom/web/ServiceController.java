package com.elandt.lil.wisdom.web;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elandt.lil.wisdom.data.entity.Service;
import com.elandt.lil.wisdom.data.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("services")
@Slf4j
@RequiredArgsConstructor
public class ServiceController {

  private final ServiceRepository serviceRepository;

  @GetMapping
  public Iterable<Service> getAllServices(@RequestParam(required = false) String name) {
    if (StringUtils.hasLength(name)) {
      var services = new ArrayList<Service>();
      var service = this.serviceRepository.findByName(name);
      if (service.isPresent()) {
        services.add(service.get());
      }
      return services;
    }

    return this.serviceRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Service> getById(@PathVariable UUID id) {
    return ResponseEntity.of(this.serviceRepository.findById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Service createService(@RequestBody Service entity) {
    return this.serviceRepository.save(entity);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Service> updateService(@PathVariable UUID id, @RequestBody Service entity) {
    if (!id.equals(entity.getServiceId())) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ofNullable(this.serviceRepository.save(entity));
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.RESET_CONTENT)
  public void deleteService(@PathVariable UUID id) {
    this.serviceRepository.deleteById(id);
  }
}
