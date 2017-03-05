package pl.lidkowiak.contractsalarycalculator.countrytaxsystem.domain;

public class NegativeDailyNetSalaryException extends RuntimeException {

    public NegativeDailyNetSalaryException() {
        super("Daily net salary should be greater 0.00.");
    }

}
