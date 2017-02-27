package pl.lidkowiak.contractsalarycalculator.salarycalculations;

import org.junit.Test;
import pl.lidkowiak.contractsalarycalculator.money.Money;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultMonthlyNetSalaryCalculationPolicyTest {

    @Test
    public void should_calculate_monthly_net_salary() throws Exception {
        //given
        DefaultMonthlyNetSalaryCalculationPolicy cut = new DefaultMonthlyNetSalaryCalculationPolicy(
                new BigDecimal("0.1"), Money.pln(BigDecimal.valueOf(2000)));

        //when
        Money monthlyNetSalary = cut.calculate(Money.pln(BigDecimal.valueOf(1000)));

        //then
        //(22 * 1000 - 2000) * 90%
        assertThat(monthlyNetSalary).isEqualTo(Money.pln(BigDecimal.valueOf(18000)));
    }
}