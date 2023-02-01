package com.nhaccuaquang.musique.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RESTResponse {
    private HashMap<String, Object> response;

    private RESTResponse() {
        this.response = new HashMap<>();
    }
    public HashMap<String, Object> getResponse() {
        return response;
    }
    public void setResponse(HashMap<String, Object> response) {
        this.response = response;
    }
    public void addResponse(String key, Object value) {
        this.response.put(key, value);
    }

    public static class Error {
        private int status;
        private String message;
        private List<String> errors;


        public Error() {
        }

        public Error setStatus(int status) {
            this.status = status;
            return this;
        }

        public Error setMessage(String message) {
            this.message = message;
            return this;
        }

        public Error addError(String error) {
            this.errors.add(error);
            return this;
        }

        public Error addMultipleError(List<String> errors) {
            this.errors.addAll(errors);
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("errors", this.errors);
            return restResponse.getResponse();
        }
    }
    public static class Success {
        private int status;
        private String message;
        private HashMap<String, Object> data;

        public Success() {
        }

        public Success setStatus(int status) {
            this.status = status;
            return this;
        }

        public Success setMessage(String message) {
            this.message = message;
            return this;
        }

        public Success addData(String key, Object value) {
            if (this.data == null) {
                this.data = new HashMap<>();
            }
            this.data.put(key, value);
            return this;
        }

        public Success addMultipleData(HashMap<String, Object> listValue) {
            this.data.putAll(listValue);
            return this;
        }

        public HashMap<String, Object> build() {
            RESTResponse restResponse = new RESTResponse();
            restResponse.addResponse("status", this.status);
            restResponse.addResponse("message", this.message);
            restResponse.addResponse("data", this.data);
            return restResponse.getResponse();
        }
    }

}
