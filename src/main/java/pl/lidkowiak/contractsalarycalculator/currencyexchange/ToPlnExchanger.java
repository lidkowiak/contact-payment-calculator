package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import pl.lidkowiak.contractsalarycalculator.core.Money;

public interface ToPlnExchanger {

    Money exchange(Money to);

}
