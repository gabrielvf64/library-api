package com.box.library.customer;


import com.box.library.request.CustomerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CustomerRequest request);
    CustomerRequest toCreateCustomerRequest(Customer customer);
}
