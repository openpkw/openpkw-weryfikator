package org.openpkw.repositories;

import org.openpkw.model.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

	UserDevice findByUserIdAndDevId(Long userId, String devId);

}
