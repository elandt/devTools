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

import com.elandt.lil.wisdom.data.entity.Vendor;
import com.elandt.lil.wisdom.data.repository.VendorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("vendors")
@Slf4j
@RequiredArgsConstructor
public class VendorController {

  private final VendorRepository vendorRepository;

  @GetMapping
  public Iterable<Vendor> getAllVendors(@RequestParam(required = false) String email) {
    if (StringUtils.hasLength(email)) {
      var vendors = new ArrayList<Vendor>();
      var vendor = this.vendorRepository.findByEmail(email);
      if (vendor.isPresent()) {
        vendors.add(vendor.get());
      }
      return vendors;
    }

    return this.vendorRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Vendor> getById(@PathVariable UUID id) {
    return ResponseEntity.of(this.vendorRepository.findById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Vendor createVendor(@RequestBody Vendor entity) {
    return this.vendorRepository.save(entity);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Vendor> updateVendor(@PathVariable UUID id, @RequestBody Vendor entity) {
    if (!id.equals(entity.getVendorId())) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ofNullable(this.vendorRepository.save(entity));
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.RESET_CONTENT)
  public void deleteVendor(@PathVariable UUID id) {
    this.vendorRepository.deleteById(id);
  }
}
