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

    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
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

    public Result error() {
        return new Result("error", false);
    }

    public Result success() {
        return new Result("success", true);
    }


}
