package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.configs.CentralMappingConfiguration;
import dev.monobank.mcpserver.domain.Account;
import dev.monobank.mcpserver.dto.AccountResponse;
import dev.monobank.mcpserver.mapper.qualifier.ArrayToList;
import dev.monobank.mcpserver.mapper.qualifier.MinorToMajor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", config = CentralMappingConfiguration.class)
public interface AccountMapper {

    @Mapping(target = "currencyCode", source = "currencyCode")
    @Mapping(target = "balance", source = "balance", qualifiedBy = MinorToMajor.class)
    @Mapping(target = "creditLimit", source = "creditLimit", qualifiedBy = MinorToMajor.class)
    @Mapping(target = "maskedPan", source = "maskedPan", qualifiedBy = ArrayToList.class)
    Account toAccount(AccountResponse accountResponse);

    List<Account> toAccounts(List<AccountResponse> accountResponses);
}
