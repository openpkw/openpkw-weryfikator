package org.openpkw.qr.parser;

import org.springframework.stereotype.Service;

/**
 * Service for parsing qr
 * @author Sebastian Pogorzelski
 */
@Service
public class QrParserService {

    public QrWrapper parse(String qr) {
        return new DefaultQrWrapper(qr);
    }

}
