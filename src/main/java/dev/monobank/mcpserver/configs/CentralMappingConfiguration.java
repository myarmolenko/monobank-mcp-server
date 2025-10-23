package dev.monobank.mcpserver.configs;

import dev.monobank.mcpserver.mapper.converter.CollectionConverters;
import dev.monobank.mcpserver.mapper.converter.MoneyConverters;
import dev.monobank.mcpserver.mapper.converter.TimeConverters;
import org.mapstruct.MapperConfig;

@MapperConfig(
        componentModel = "spring",
        uses = {
                MoneyConverters.class,
                CollectionConverters.class,
                TimeConverters.class
        }
)
public class CentralMappingConfiguration {

}
