package org.sjtu.controller;

import org.sjtu.model.AddressEntity;
import org.sjtu.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ace on 6/14/17.
 */
@Controller
@RequestMapping(path = "/addrs")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;


    @PostMapping(path = {"/",""})
    public @ResponseBody Iterable<AddressEntity> getAllAddress(@RequestParam(defaultValue = "") String city,
                                                               @RequestParam(defaultValue = "") String counrty) {
        if (city.length() == 0 && counrty.length() == 0) {
            return addressRepository.findAll();
        }
        else if (city.length() > 0 && counrty.length() == 0) {
            return addressRepository.findByAddrCityContainingIgnoreCase(city);
        }
        else if ( city.length() == 0 && counrty.length() > 0) {
            return addressRepository.findByAddrCountryContainingIgnoreCase(counrty);
        }
        else {
            return addressRepository.findByAddrCityContainingIgnoreCaseAndAddrCountryContainingIgnoreCase(city, counrty);
        }
    }

}
