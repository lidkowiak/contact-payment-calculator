package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import pl.lidkowiak.contractsalarycalculator.money.Money;

/**
 * Exchanges different currencies to PLN.
 */
public interface ToPlnExchanger {

    /**
     * @throws ExchangeNotSupportedException
     */
    Money exchange(Money toExchange);

}
