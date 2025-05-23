package helpers;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import config.AppProperties;
import ui.VaadinApplication;

import java.util.ArrayList;
import java.util.List;

public class HeaderBuilder {

    private static AppProperties getAppProperties() {
        return VaadinApplication.getContext().getBean(AppProperties.class);
    }

    public static Headers getHeaders(int contentLength) {
        String bearerToken = getAppProperties().getBearerToken();

        List<Header> headersList = new ArrayList<>();

        headersList.add(new Header("Host", "web-api-prod-ytp.tcddtasimacilik.gov.tr"));
        headersList.add(new Header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:133.0) Gecko/20100101 Firefox/133.0"));
        headersList.add(new Header("Accept", "application/json, text/plain, */*"));
        headersList.add(new Header("Accept-Language", "tr"));
        headersList.add(new Header("Accept-Encoding", "gzip, deflate, br, zstd"));
        headersList.add(new Header("Authorization", "Bearer " + bearerToken));
        headersList.add(new Header("unit-id", "3895"));
        headersList.add(new Header("Content-Type", "application/json"));
        headersList.add(new Header("Origin", "https://ebilet.tcddtasimacilik.gov.tr"));
        headersList.add(returnContentLengthHeader(contentLength));
        headersList.add(new Header("DNT", "1"));
        headersList.add(new Header("Connection", "keep-alive"));
        headersList.add(new Header("Sec-Fetch-Dest", "empty"));
        headersList.add(new Header("Sec-Fetch-Mode", "cors"));
        headersList.add(new Header("Sec-Fetch-Site", "same-site"));

        return new Headers(headersList);

    }

    private static Header returnContentLengthHeader(int contentLength) {
        return  new Header("~Content-Length", String.valueOf(contentLength));
    }
}
