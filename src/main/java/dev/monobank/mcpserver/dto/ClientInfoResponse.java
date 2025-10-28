package dev.monobank.mcpserver.dto;

import java.util.List;

public record ClientInfoResponse(
    String clientId,
    String name,
    String webHookUrl,
    String permissions,
    List<AccountResponse> accounts,
    List<JarResponse> jars
) {

}
