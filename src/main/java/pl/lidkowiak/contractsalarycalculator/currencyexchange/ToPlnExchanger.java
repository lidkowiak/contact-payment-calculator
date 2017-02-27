package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import pl.lidkowiak.contractsalarycalculator.money.Money;

public interface ToPlnExchanger {

    Money exchange(Money to);

}
