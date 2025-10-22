package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.dto.StatementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Mapper(componentModel = "spring")
public interface StatementMapper {

    @Mapping(target = "date", source = "time", qualifiedByName = "timestampToDate")
    @Mapping(target = "category", source = "mcc")
    @Mapping(target = "amount", source = "amount", qualifiedByName = "minorToMajor")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "minorToMajor")
    @Mapping(target = "merchant", source = "counterName")
    @Mapping(target = "notes", source = "comment")
    @Mapping(target = "cashback", source = "cashbackAmount", qualifiedByName = "cashbackToMajor")
    Statement toStatement(StatementResponse statementResponse);

    List<Statement> toStatements(List<StatementResponse> statementResponse);

    @Named("timestampToDate")
    default LocalDateTime timestampToDate(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Named("minorToMajor")
    default BigDecimal minorToMajor(long amount) {
        return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));
    }

    @Named("cashbackToMajor")
    default BigDecimal cashbackToMajor(BigDecimal cashbackAmount) {
        return cashbackAmount.divide(BigDecimal.valueOf(100));
    }
}