package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.domain.Jar;
import dev.monobank.mcpserver.dto.JarResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface JarMapper {

    @Mapping(target = "balance", source = "balance", qualifiedByName = "minorToMajor")
    @Mapping(target = "goal", source = "goal", qualifiedByName = "minorToMajor")
    @Mapping(target = "progressPercentage", source = ".", qualifiedByName = "calculateProgress")
    Jar toJar(JarResponse jarResponse);

    @Named("minorToMajor")
    default BigDecimal minorToMajor(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(100));
    }

    @Named("calculateProgress")
    default int calculateProgress(JarResponse jarResponse) {
        BigDecimal balance = jarResponse.balance();
        BigDecimal goal = jarResponse.goal();

        if (goal.compareTo(BigDecimal.ZERO) != 0) {
            return balance.multiply(BigDecimal.valueOf(100))
                    .divide(goal, 0, RoundingMode.DOWN)
                    .intValue();
        }

        return 0;
    }
}
