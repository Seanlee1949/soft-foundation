package com.example.boot.controller;

import com.example.boot.controller.mock.MockDataController;
import org.junit.jupiter.api.Test;

class MockDataControllerTest {


    @Test
    void updateHistoryDataByFile() {
        new MockDataController().updateHistoryDataByFile();
        System.out.println();

    }
}