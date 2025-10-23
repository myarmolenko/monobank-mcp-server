package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.dto.ClientInfoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, JarMapper.class})
public interface ClientInfoMapper {

    ClientInfo toClientInfo(ClientInfoResponse clientInfoResponse);
}
