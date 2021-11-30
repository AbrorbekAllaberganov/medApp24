package com.example.medic.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    private String message;
    private boolean success;
    private Object data;
    private String exception;

    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Result(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public Result save(Object data) {
        return new Result("saved success", true, data);
    }

    public Result delete() {
        return new Result("delete success", true);
    }

    public Result edit(Object data) {
        return new Result("edit success", true, data);
    }

    public Result error(Exception e) {
        return new Result("error", false,e.getMessage());
    }

    public Result success(Object data) {
        return new Result("success", true,data);
    }


}
