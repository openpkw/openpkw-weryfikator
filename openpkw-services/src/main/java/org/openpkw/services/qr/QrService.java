package org.openpkw.services.qr;

import org.openpkw.services.qr.dto.QrDTO;
import org.openpkw.services.qr.dto.QrResultDTO;

/**
 * Service for parsing and saving QR data from mobile
 * @author Sebastian Pogorzelski
 */
public interface QrService {

    QrResultDTO saveResult(QrDTO qrDTO);

}
