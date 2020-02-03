package api.montly_report;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportResponse {
    private Map<String, Double> prices = new LinkedHashMap<String, Double>();

    public ReportResponse(Report report) {
        report.getVirtualMachinePrices().forEach((vm, price) -> prices.put(vm.getName(), price));
        report.getDrivePrices().forEach((drive, price) -> prices.put(drive.getName(), price));
    }
}
