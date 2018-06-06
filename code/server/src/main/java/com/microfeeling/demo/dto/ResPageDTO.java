package com.microfeeling.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResPageDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int pageNumber;
    private int pageSize;
    private int total;
    private List<T> data;
}
