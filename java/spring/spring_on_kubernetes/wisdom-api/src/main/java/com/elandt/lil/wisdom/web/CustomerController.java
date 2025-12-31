package com.elandt.lil.wisdom.web;

import java.util.ArrayList;
import java.util.Map;
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

import com.elandt.lil.wisdom.data.entity.Customer;
import com.elandt.lil.wisdom.data.repository.CustomerRepository;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("customers")
@Slf4j
public class CustomerController {

  private final CustomerRepository customerRepository;

  private final Map<String, Timer> timerMap;

  private static final String GET_ALL_CUSTOMERS = "getAllCustomers";
  private static final String CREATE_CUSTOMER = "createCustomer";
  private static final String GET_BY_ID = "getById";
  private static final String UPDATE_CUSTOMER = "updateCustomer";
  private static final String DELETE_CUSTOMER = "deleteCustomer";

  public CustomerController(CustomerRepository customerRepository, MeterRegistry registry) {
    super();
    this.customerRepository = customerRepository;
    this.timerMap = Map.of(GET_ALL_CUSTOMERS, registry.timer(GET_ALL_CUSTOMERS),
        CREATE_CUSTOMER, registry.timer(CREATE_CUSTOMER),
        GET_BY_ID, registry.timer(GET_BY_ID),
        UPDATE_CUSTOMER, registry.timer(UPDATE_CUSTOMER),
        DELETE_CUSTOMER, registry.timer(DELETE_CUSTOMER));
  }

  @GetMapping
  public Iterable<Customer> getAllCustomers(@RequestParam(required = false) String email) {
    var timer = Timer.start();
    if (StringUtils.hasLength(email)) {
      var customers = new ArrayList<Customer>();
      var customer = this.customerRepository.findByEmail(email);
      if (customer.isPresent()) {
        customers.add(customer.get());
      }
      timerMap.get(GET_ALL_CUSTOMERS).record(() -> timer.stop(timerMap.get(GET_ALL_CUSTOMERS)));
      return customers;
    }

    var customers = this.customerRepository.findAll();
    timerMap.get(GET_ALL_CUSTOMERS).record(() -> timer.stop(timerMap.get(GET_ALL_CUSTOMERS)));
    return customers;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getById(@PathVariable UUID id) {
    return ResponseEntity.of(this.customerRepository.findById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer createCustomer(@RequestBody Customer entity) {
    return this.customerRepository.save(entity);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer entity) {
    if (!id.equals(entity.getCustomerId())) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ofNullable(this.customerRepository.save(entity));
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.RESET_CONTENT)
  public void deleteCustomer(@PathVariable UUID id) {
    this.customerRepository.deleteById(id);
  }
}
