package org.openpkw.services.rest.services;

import org.openpkw.model.entity.DistrictCommittee;
import org.openpkw.model.entity.PeripheralCommittee;
import org.openpkw.repositories.DistrictCommitteeRepository;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.repositories.ProtocolRepository;
import org.openpkw.services.rest.dto.DistrictCommitteeDTO;
import org.openpkw.services.rest.dto.DistrictsDTO;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kamil Szestowicki
 * @author Remigiusz Mrozek
 * @author Sebastian Celejewski
 */
@Component
@Transactional
public class DistrictsService {

    @Qualifier("districtCommitteeRepository")
    @Inject
    private DistrictCommitteeRepository districtCommitteeRepository;

    @Inject
    private ProtocolRepository protocolRepository;

    public DistrictsDTO getDistricts() {
        DistrictsDTO result = new DistrictsDTO();

        List<DistrictCommittee> districtCommitteeAll = districtCommitteeRepository.findAll();

        for (DistrictCommittee districtCommittee : districtCommitteeAll) {
            DistrictCommitteeDTO districtCommitteeDTO = new DistrictCommitteeDTO();
            districtCommitteeDTO.setNumber(districtCommittee.getDistrictCommitteeNumber());
            districtCommitteeDTO.setName(districtCommittee.getName());
            districtCommitteeDTO.setCities(getCities(districtCommittee));
            districtCommitteeDTO.setPeripheralsNumber( districtCommittee.getPeripheralCommitteeCollection().size());
            districtCommitteeDTO.setProtocolNumber(protocolRepository.getCountByDistrictCommittee(districtCommittee));
            result.getDistricts().add(districtCommitteeDTO);
        }

        return result;
    }

    private List<String> getCities(DistrictCommittee districtCommittee) {
        List<String> cities = new ArrayList<>();
        cities.add(districtCommittee.getDistrictCommitteeAddress().getCity());
        return cities;
    }
}