package pl.lidkowiak.contractsalarycalculator.salarycalculations;

/**
 * Created by lukasz on 05.03.2017.
 */
public class NegativeDailyNetSalaryException extends RuntimeException {

    public NegativeDailyNetSalaryException() {
        super("Daily net salary should be greater 0.00.");
    }

}
