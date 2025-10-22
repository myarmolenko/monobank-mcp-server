package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.domain.Account;
import dev.monobank.mcpserver.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "currencyCode", source = "currencyCode")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "minorToMajor")
    @Mapping(target = "creditLimit", source = "creditLimit", qualifiedByName = "minorToMajor")
    @Mapping(target = "maskedPan", source = "maskedPan", qualifiedByName = "arrayToList")
    Account toAccount(AccountResponse accountResponse);

    List<Account> toAccounts(List<AccountResponse> accountResponses);

    @Named("minorToMajor")
    default BigDecimal minorToMajor(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(100));
    }

    @Named("arrayToList")
    default List<String> arrayToList(String[] array) {
        return array != null ? Arrays.asList(array) : List.of();
    }
}
