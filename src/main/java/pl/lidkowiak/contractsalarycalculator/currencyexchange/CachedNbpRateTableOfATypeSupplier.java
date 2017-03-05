package pl.lidkowiak.contractsalarycalculator.currencyexchange;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

/**
 * Supplies current NBP exchange rate A table and cached response in order to limit external API calls
 * with caching policy corresponded to table A publication rules (http://www.nbp.pl/home.aspx?f=/statystyka/kursy.html)
 */
public class CachedNbpRateTableOfATypeSupplier implements Supplier<ExchangeRateTable> {

    private final Supplier<ExchangeRateTable> decoratedSupplier;
    private final Supplier<LocalDateTime> currentTimeSupplier;

    private ExchangeRateTable cache;
    private LocalDateTime lastInvocation;

    public CachedNbpRateTableOfATypeSupplier(Supplier<ExchangeRateTable> decoratedSupplier) {
        this(decoratedSupplier, LocalDateTime::now);
    }

    CachedNbpRateTableOfATypeSupplier(Supplier<ExchangeRateTable> decoratedSupplier, Supplier<LocalDateTime> currentTimeSupplier) {
        this.decoratedSupplier = decoratedSupplier;
        this.currentTimeSupplier = currentTimeSupplier;
    }

    @Override
    public ExchangeRateTable get() {
        if (shouldInvokeUnderlyingSupplier()) {
            // no synchronization is intended, nothing bad happens
            // when underlying supplier will be calls more than once when table will be changing
            cache = decoratedSupplier.get();
            lastInvocation = currentTimeSupplier.get();
        }
        return cache;
    }

    private boolean shouldInvokeUnderlyingSupplier() {
        return emptyCache() || cachedValueIsFromYesterdayAndItIsAfter11_45() || cachedValueIsFromDayBeforeYestarday();
    }

    private boolean emptyCache() {
        return isNull(cache);
    }

    private boolean cachedValueIsFromYesterdayAndItIsAfter11_45() {
        LocalDateTime now = currentTimeSupplier.get();
        return cache.getPublicationDate().isEqual(now.toLocalDate().minusDays(1))
                && now.toLocalTime().isAfter(LocalTime.of(11, 45));
    }

    private boolean cachedValueIsFromDayBeforeYestarday() {
        LocalDateTime now = currentTimeSupplier.get();
        return cache.getPublicationDate().isBefore(now.toLocalDate().minusDays(1)) && lastInvocation.toLocalDate().isBefore(now.toLocalDate());
    }
}
