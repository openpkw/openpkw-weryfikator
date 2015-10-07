package org.openpkw.utils.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.file.LineMapper;

/**
 * Created by wojciech.szostak on 15.08.2015.
 */
public class CsvParser<T> {

    private final static Logger log = Logger.getLogger(CsvParser.class);

    private LineMapper<T> lineMapper;
    private String charset;

    public CsvParser(LineMapper<T> lineMapper) {
        this.lineMapper = lineMapper;
        this.charset = "utf-8";
    }

    public CsvParser(LineMapper<T> lineMapper, String charset) {
        this.lineMapper = lineMapper;
        this.charset = charset;
    }

    public List<T> parse(File file) {
        List<T> result = new ArrayList<>();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

            int lineNumber = 1;
            boolean header = true;
            for (String line; (line = br.readLine()) != null;) {
                if (header) {
                    header = false;
                    continue;
                }

                T fieldSet = lineMapper.mapLine(line, lineNumber);
                if (fieldSet != null) {
                    result.add(fieldSet);
                }
            }

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse file " + file + ": " + ex.getMessage(), ex);
        } finally {
            try {
                br.close();
            } catch (Exception ex) {
                log.warn(ex);
            }
        }

    }
}
