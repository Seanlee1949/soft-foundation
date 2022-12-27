package com.example.boot.entity.vo;

import com.example.boot.entity.AnalysisResult;
import com.example.boot.entity.response.CommonResponse;

import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/25
 */
public class AnalysisVo extends CommonResponse {
    private List<AnalysisResult> result;

    public List<AnalysisResult> getResult() {
        return result;
    }

    public void setResult(List<AnalysisResult> result) {
        this.result = result;
    }
}
