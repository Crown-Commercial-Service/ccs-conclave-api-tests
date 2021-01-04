package com.ccs.conclave.api.report;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
    static ExtentReports extent;
    final static String filePath = "target/report/conclave_api_test_report.html";

    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
        }

        return extent;
    }
}