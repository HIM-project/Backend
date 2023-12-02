package HIM.project.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverOcrDto {

    @JsonProperty("version")
    private String version;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("images")
    private List<Image> images;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Image {
        @JsonProperty("receipt")
        private Receipt receipt;

        @JsonProperty("uid")
        private String uid;

        @JsonProperty("name")
        private String name;

        @JsonProperty("inferResult")
        private String inferResult;

        @JsonProperty("message")
        private String message;

        @JsonProperty("validationResult")
        private ValidationResult validationResult;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)

    @Getter
    public static class Receipt {
        @JsonProperty("meta")
        private Meta meta;

        @JsonProperty("result")
        private Result result;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Meta {
        @JsonProperty("estimatedLanguage")
        private String estimatedLanguage;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Result {
        @JsonProperty("storeInfo")
        private StoreInfo storeInfo;

        @JsonProperty("paymentInfo")
        private PaymentInfo paymentInfo;

        @JsonProperty("subResults")
        private List<SubResult> subResults;

        @JsonProperty("totalPrice")
        private TotalPrice totalPrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class StoreInfo {
        @JsonProperty("name")
        private StringObject name;

        @JsonProperty("subName")
        private StringObject subName;

        @JsonProperty("bizNum")
        private StringObject bizNum;

        @JsonProperty("addresses")
        private List<StringObject> addresses;


        @JsonProperty("tel")
        private List<TelObject> tel;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class PaymentInfo {
        @JsonProperty("date")
        private DateObject date;

        @JsonProperty("time")
        private TimeObject time;

        @JsonProperty("cardInfo")
        private CardInfo cardInfo;

        @JsonProperty("confirmNum")
        private BaseObject confirmNum;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class SubResult {
        @JsonProperty("items")
        private List<Item> items;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Item {
        @JsonProperty("name")
        private StringObject name;

        @JsonProperty("count")
        private FloatObject count;

        @JsonProperty("price")
        private Price price;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Price {
        @JsonProperty("price")
        private FloatObject price;

        @JsonProperty("unitPrice")
        private FloatObject unitPrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class TotalPrice {
        @JsonProperty("price")
        private FloatObject price;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StringObject {
        @JsonProperty("text")
        private String text;

        @JsonProperty("formatted")
        private Formatted formatted;

        @JsonProperty("boundingPolys")
        private List<BoundingPoly> boundingPolys;

        @JsonProperty("keyText")
        private String keyText;


        @JsonProperty("confidenceScore")
        private double confidenceScore;


    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Formatted {
        @JsonProperty("value")
        private String value;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class BoundingPoly {
        @JsonProperty("vertices")
        private List<Vertex> vertices;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Vertex {
        @JsonProperty("x")
        private double x;

        @JsonProperty("y")
        private double y;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class DateObject {
        @JsonProperty("text")
        private String text;

        @JsonProperty("formatted")
        private YearMonthDay formatted;

        @JsonProperty("boundingPolys")
        private List<BoundingPoly> boundingPolys;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class YearMonthDay {
        @JsonProperty("year")
        private String year;

        @JsonProperty("month")
        private String month;

        @JsonProperty("day")
        private String day;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class TimeObject {
        @JsonProperty("text")
        private String text;

        @JsonProperty("formatted")
        private HourMinuteSecond formatted;

        @JsonProperty("boundingPolys")
        private List<BoundingPoly> boundingPolys;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class HourMinuteSecond {
        @JsonProperty("hour")
        private String hour;

        @JsonProperty("minute")
        private String minute;

        @JsonProperty("second")
        private String second;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class CardInfo {
        @JsonProperty("company")
        private StringObject company;

        @JsonProperty("number")
        private StringObject number;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class ValidationResult {
        @JsonProperty("result")
        private String result;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class TelObject {
        @JsonProperty("text")
        private String text;

        @JsonProperty("formatted")
        private Formatted formatted;

        @JsonProperty("boundingPolys")
        private List<BoundingPoly> boundingPolys;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class BaseObject {
        @JsonProperty("text")
        private String text;

        @JsonProperty("boundingPolys")
        private List<BoundingPoly> boundingPolys;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class FloatObject {
        @JsonProperty("text")
        private String text;

        @JsonProperty("formatted")
        private Formatted formatted;

        @JsonProperty("boundingPolys")
        private List<BoundingPoly> boundingPolys;
    }
}