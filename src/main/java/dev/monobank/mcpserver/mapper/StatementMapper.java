package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.configs.CentralMappingConfiguration;
import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.dto.StatementResponse;
import dev.monobank.mcpserver.mapper.qualifier.MinorToMajor;
import dev.monobank.mcpserver.mapper.qualifier.TimestampToDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", config = CentralMappingConfiguration.class)
public interface StatementMapper {

    @Mapping(target = "date", source = "time", qualifiedBy = TimestampToDate.class)
    @Mapping(target = "category", source = "mcc")
    @Mapping(target = "amount", source = "amount", qualifiedBy = MinorToMajor.class)
    @Mapping(target = "balance", source = "balance", qualifiedBy = MinorToMajor.class)
    @Mapping(target = "merchant", source = "counterName")
    @Mapping(target = "notes", source = "comment")
    @Mapping(target = "cashback", source = "cashbackAmount", qualifiedBy = MinorToMajor.class)
    Statement toStatement(StatementResponse statementResponse);

    List<Statement> toStatements(List<StatementResponse> statementResponse);
}