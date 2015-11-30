package org.openpkw.weryfikator.rest;

import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test for QR REST service
 * @author Sebastian Pogorzelski
 */
public class When_sending_POST_request_to_qrResult {

    private static final String QR_EMPTY_JSON = "{}";
    private static final String QR_JSON = "{\n" +
            "\t\"token\": \"\",\n" +
            "\t\"qr\": \"146519,332,2086,1760,103,1657,1,18,0,0,0,0,0,0,0,1656,0,0,1656,12,1644,,0101;272,0102;38,0103;16,0104;6,0105;8,0106;6,0107;7,0108;4,0109;2,0110;4,0111;4,0112;7,0113;3,0114;17,0115;4,0116;1,0119;3,0120;4,0121;2,0124;1,0128;2,0129;1,0130;1,0131;5,0132;1,0133;7,0137;3,0138;2,0139;1,0140;2,0201;366,0202;31,0203;21,0204;12,0205;7,0206;1,0207;33,0208;2,0209;6,0210;3,0211;3,0212;5,0213;2,0214;2,0218;2,0219;3,0220;1,0222;1,0223;14,0224;1,0227;1,0229;1,0235;1,0236;1,0237;1,0238;1,0240;5,0301;78,0302;2,0303;1,0304;4,0305;1,0306;1,0310;2,0320;1,0326;1,0401;58,0402;3,0403;8,0404;1,0412;1,0420;1,0421;1,0424;1,0426;1,0501;50,0504;5,0505;2,0510;1,0511;2,0512;1,0601;249,0602;13,0603;1,0605;2,0609;1,0610;7,0611;2,0612;1,0614;4,0615;1,0616;3,0617;3,0621;1,0623;1,0626;1,0628;1,0629;1,0632;1,0635;1,0636;1,0637;2,0639;2,0701;1,0702;1\"\n" +
            "}";

    private static final String QR_WRONG_DATA_JSON = "{\n" +
            "\t\"token\": \"\",\n" +
            "\t\"qr\": \"111,222,2086,1760,103,1657,1,18,0,0,0,0,0,0,0,1656,0,0,1656,12,1644,,0101;272,0102;38,0103;16,0104;6,0105;8,0106;6,0107;7,0108;4,0109;2,0110;4,0111;4,0112;7,0113;3,0114;17,0115;4,0116;1,0119;3,0120;4,0121;2,0124;1,0128;2,0129;1,0130;1,0131;5,0132;1,0133;7,0137;3,0138;2,0139;1,0140;2,0201;366,0202;31,0203;21,0204;12,0205;7,0206;1,0207;33,0208;2,0209;6,0210;3,0211;3,0212;5,0213;2,0214;2,0218;2,0219;3,0220;1,0222;1,0223;14,0224;1,0227;1,0229;1,0235;1,0236;1,0237;1,0238;1,0240;5,0301;78,0302;2,0303;1,0304;4,0305;1,0306;1,0310;2,0320;1,0326;1,0401;58,0402;3,0403;8,0404;1,0412;1,0420;1,0421;1,0424;1,0426;1,0501;50,0504;5,0505;2,0510;1,0511;2,0512;1,0601;249,0602;13,0603;1,0605;2,0609;1,0610;7,0611;2,0612;1,0614;4,0615;1,0616;3,0617;3,0621;1,0623;1,0626;1,0628;1,0629;1,0632;1,0635;1,0636;1,0637;2,0639;2,0701;1,0702;1\"\n" +
            "}";
    public static final int NOT_FOUND_STATUS = 404;
    public static final int OK_STATUS = 200;
    public static final int BAD_REQUEST_STATUS = 400;

    @Test
    public void Should_return_BAD_REQUEST_status_for_empty_request() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://dobromir.openpkw.pl:9080/openpkw/api/qr");
        Response response = target.request().post(Entity.json(QR_EMPTY_JSON));

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST_STATUS);
    }

    @Test
    @Ignore // Doesn't work without initial data in database
    public void Should_return_OK_status() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://dobromir.openpkw.pl:9080/openpkw/api/qr");
        Response response = target.request().post(Entity.json(QR_JSON));

        assertThat(response.getStatus()).isEqualTo(OK_STATUS);
    }

    @Test
    public void Should_return_NOT_FOUND_status() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://dobromir.openpkw.pl:9080/openpkw/api/qr");
        Response response = target.request().post(Entity.json(QR_WRONG_DATA_JSON));

        assertThat(response.getStatus()).isEqualTo(NOT_FOUND_STATUS);
    }

}
