package org.openpkw.qr.service;

import org.openpkw.qr.dto.QrDTO;
import org.openpkw.qr.dto.QrResultDTO;

/**
 * Service for parsing and saving qr from mobile
 * @author Sebastian Pogorzelski
 */
public interface QrService {

    QrResultDTO saveResult(QrDTO qrDTO);

}
