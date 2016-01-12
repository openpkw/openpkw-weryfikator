package org.openpkw.services.qr.parser;

import org.springframework.stereotype.Service;

/**
 * Service for parsing qr
 * @author Sebastian Pogorzelski
 */
@Service
public class QrParserServiceImpl implements QrParseService {
    public QrWrapper parse(String qr) {
        return new QrWrapperImpl(qr);
    }
}
