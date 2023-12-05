package HIM.project.service;

import HIM.project.common.ErrorCode;
import HIM.project.common.ResponseDto;
import HIM.project.dto.response.NaverOcrDto;
import HIM.project.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NaverOcrApiService {
    @Value("${naver.service.url}")
    private String apiUrl;
    @Value("${naver.service.secretKey}")
    private String naverSecretKey;



    public ResponseDto<?> requestImage(MultipartFile file) {

            Result result = getResult(file);

        try {
            result.con.connect();
        } catch (IOException e) {
            log.error("Error",e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }

        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(result.con.getOutputStream());
        writeMultiPart(wr, result.postParams, file, result.boundary);

            int responseCode = result.con.getResponseCode();

            ObjectMapper objectMapper = new ObjectMapper();
            NaverOcrDto response = objectMapper.readValue(readResponseBody(result.con, responseCode), NaverOcrDto.class);


            return ResponseDto.success(response.getImages().get(0).getReceipt().getResult().getStoreInfo().getBizNum().getText());
        } catch (IOException e) {
            log.error("Error",e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }
        }


    private Result getResult(MultipartFile file) {
        URL url = null;
        try {
            url = new URL(apiUrl);
        } catch (MalformedURLException e) {
            log.info("Error",e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            log.info("Error",e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Connection", "keep-alive");
        con.setReadTimeout(5000);
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            log.error("Error",e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }

        String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-OCR-SECRET", naverSecretKey);

        JSONObject json = new JSONObject();
        try {
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());

        JSONObject image = new JSONObject();
        image.put("format", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        image.put("name", "demo");

        JSONArray images = new JSONArray();
        images.put(image);
        json.put("images", images);
        }   catch (JSONException e) {
            log.error("error" ,e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }
        String postParams = json.toString();
        return new Result(con, boundary, postParams);
    }

    @AllArgsConstructor
    private static class Result {
        public final HttpURLConnection con;
        public final String boundary;
        public final String postParams;
    }

    private void writeMultiPart(OutputStream out, String jsonMessage, MultipartFile file, String boundary)  {
        String sb = "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"message\"\r\n\r\n" +
                jsonMessage + "\r\n";

        try {
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
        } catch (IOException e) {
            log.error("Error",e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }
    }

    private String readResponseBody(HttpURLConnection connection, int responseCode) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader((responseCode == 200) ? connection.getInputStream() : connection.getErrorStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } catch (IOException e) {
            log.error("Error",e);
            throw new CustomException(ErrorCode.NAVER_SERVER_ERROR);
        }
    }
}
