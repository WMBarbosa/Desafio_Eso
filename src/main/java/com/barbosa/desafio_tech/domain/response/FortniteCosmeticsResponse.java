package com.barbosa.desafio_tech.domain.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

import lombok.Data;
import java.util.List;

@Data
public class FortniteCosmeticsResponse {
    private int status;
    private DataContainer data;

    @Data
    public static class DataContainer {
        private List<CosmeticItem> br;
    }

    @Data
    public static class CosmeticItem {
        private String id;
        private String name;
        private String description;
        private TypeInfo type;
        private RarityInfo rarity;
        private Images images;
        private String added;

        @Data
        public static class TypeInfo {
            private String value;
            private String displayValue;
            private String backendValue;
        }

        @Data
        public static class RarityInfo {
            private String value;
            private String displayValue;
            private String backendValue;
        }

        @Data
        public static class Images {
            private String smallIcon;
        }
    }
}

