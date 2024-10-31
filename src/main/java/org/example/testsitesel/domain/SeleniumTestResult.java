package org.example.testsitesel.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeleniumTestResult {
    String test;
    String result;
}
