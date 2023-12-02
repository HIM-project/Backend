package HIM.project.service;

import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.response.NaverOcrDto;
import HIM.project.exception.CustomException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NaverOcrApiService {
    @Value("${naver.service.url}")
    private String apiUrl;
    @Value("${naver.service.secretKey}")
    private String naverSecretKey;

    @SneakyThrows
    public ResponseDto<?> requestImage(MultipartFile file) {

            Result result = getResult(file);

            result.con.connect();

            DataOutputStream wr = new DataOutputStream(result.con.getOutputStream());
            writeMultiPart(wr, result.postParams, file, result.boundary);

            int responseCode = result.con.getResponseCode();

            ObjectMapper objectMapper = new ObjectMapper();
            NaverOcrDto response = objectMapper.readValue(readResponseBody(result.con, responseCode), NaverOcrDto.class);
            System.out.println("response = " + response);


            return ResponseDto.success(response.getImages().get(0).getReceipt().getResult().getStoreInfo().getBizNum().getText());
        }


    private Result getResult(MultipartFile file) throws IOException, JSONException {
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setReadTimeout(30000);
        con.setRequestMethod("POST");

        String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-OCR-SECRET", naverSecretKey);

        JSONObject json = new JSONObject();
        json.put("version", "V2");
        json.put("requestId", UUID.randomUUID().toString());
        json.put("timestamp", System.currentTimeMillis());

        JSONObject image = new JSONObject();
        image.put("format", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        image.put("name", "demo");

        JSONArray images = new JSONArray();
        images.put(image);
        json.put("images", images);
        String postParams = json.toString();
        Result result = new Result(con, boundary, postParams);
        return result;
    }

    @AllArgsConstructor
    private static class Result {
        public final HttpURLConnection con;
        public final String boundary;
        public final String postParams;
    }

    private void writeMultiPart(OutputStream out, String jsonMessage, MultipartFile file, String boundary) throws IOException {
        String sb = "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"message\"\r\n\r\n" +
                jsonMessage + "\r\n";

        out.write(sb.getBytes(StandardCharsets.UTF_8));
        out.flush();

        if (file != null && file.getSize() > 0) {
            out.write(("--" + boundary + "\r\n").getBytes(StandardCharsets.UTF_8));

            String fileName = URLEncoder.encode(Objects.requireNonNull(file.getOriginalFilename()), StandardCharsets.UTF_8);
            String fileString = "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n" +
                    "Content-Type: application/octet-stream\r\n\r\n";

            out.write(fileString.getBytes(StandardCharsets.UTF_8));
            out.flush();

            try (InputStream fis = file.getInputStream()) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
        }
        out.flush();
    }

    private String readResponseBody(HttpURLConnection connection, int responseCode) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader((responseCode == 200) ? connection.getInputStream() : connection.getErrorStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }
}
