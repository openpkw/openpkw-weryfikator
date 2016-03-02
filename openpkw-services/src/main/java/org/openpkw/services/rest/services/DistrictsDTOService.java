package org.openpkw.services.rest.services;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.PeripheralCommittee;
import org.openpkw.repositories.DistrictCommitteeRepository;
import org.openpkw.services.rest.dto.DistrictCommitteeDTO;
import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by kamil on 08.02.16.
 */
@Component
public class DistrictsDTOService {

    @Qualifier("districtCommitteeRepository")
    @Inject
    private DistrictCommitteeRepository districtCommitteeRepository;


    public DistrictsDTO generate() {
        DistrictsDTO districtsDTO = new DistrictsDTO();
        DistrictCommitteeDTO districtCommitteeDTO;

        districtsDTO.setDistricts(new ArrayList<>());
        List<DistrictCommittee> districtCommitteeAll = districtCommitteeRepository.findAll();
        Random r = new Random();
        for (DistrictCommittee districtCommittee : districtCommitteeAll) {
            districtCommitteeDTO = new DistrictCommitteeDTO();
            districtCommitteeDTO.setNumber(districtCommittee.getDistrictCommitteeNumber());
            districtCommitteeDTO.setName(districtCommittee.getName());
            //districtCommitteeDTO.setCities(getCities(districtCommittee));
            Optional<Long> peripheralsNumber = districtCommitteeRepository.getPeripheralNumber(districtCommittee.getDistrictCommitteeNumber());
            if (peripheralsNumber.isPresent())
                districtCommitteeDTO.setPeripheralsNumber(peripheralsNumber.get());

            districtCommitteeDTO.setProtocolNumber(r.nextInt(peripheralsNumber.get().intValue()));
            districtsDTO.getDistricts().add(districtCommitteeDTO);
        }
        return districtsDTO;
    }
    /*
    private List<String> getCities(DistrictCommittee districtCommittee) {
        List<String> cities = new ArrayList<>();
        Collection<PeripheralCommittee> peripheralCommittees = districtCommittee.getPeripheralCommitteeCollection();

        cities.addAll(peripheralCommittees.stream().map(
                committee -> committee.getPeripheralCommitteeAddress().getCity()).
                filter(a->a!=null).
                collect(Collectors.toList()));

        return cities;
    }*/


}
