package org.sjtu.repository;

import org.sjtu.model.AddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ace on 6/14/17.
 */
public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    List<AddressEntity> findByAddrCityContainingIgnoreCase(String city);
    List<AddressEntity> findByAddrCountryContainingIgnoreCase(String country);
    List<AddressEntity> findByAddrCityContainingIgnoreCaseAndAddrCountryContainingIgnoreCase(String city, String country);
    List<AddressEntity> findByAddrCityIgnoreCase(String city);
}
