package org.openpkw.services.rest.services;

import org.openpkw.model.entity.PeripheralCommittee;
import org.openpkw.repositories.PeripheralCommitteeRepository;
import org.openpkw.services.rest.dto.PeripheralCommitteeDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeripheralCommitteeService {

    @Inject
    private PeripheralCommitteeRepository peripheralCommitteeRepository;

    public List<PeripheralCommitteeDTO> generate(int districtCommitteeNumber, int peripheralCommitteeNumber, String teritorialCode, String town, String street) {
        List<PeripheralCommitteeDTO> peripheralCommittees = new ArrayList<>();
        PeripheralCommitteeDTO peripheralCommitteeDTO = new PeripheralCommitteeDTO();

        teritorialCode = teritorialCode == null ? "%" : teritorialCode;
        town = town == null ? "%" : town;
        street = street == null ? "%" : street;

        List<PeripheralCommittee> peripheralCommitteeList;

        if (peripheralCommitteeNumber > 0) {
            peripheralCommitteeList = peripheralCommitteeRepository.findByDistrictCommitteeByNumber (
                    districtCommitteeNumber, teritorialCode , peripheralCommitteeNumber, town, street);
        } else {
            peripheralCommitteeList = peripheralCommitteeRepository.findByDistrictCommittee(
                    districtCommitteeNumber, town, street
            );
        }

        for (PeripheralCommittee committee : peripheralCommitteeList) {
            peripheralCommitteeDTO.setNumber(committee.getPeripheralCommitteeNumber());
            peripheralCommitteeDTO.setName(committee.getName());
            peripheralCommitteeDTO.setTeritorialCode(committee.getTerritorialCode());
            peripheralCommittees.add(peripheralCommitteeDTO);
        }

        return peripheralCommittees;
    }
}
