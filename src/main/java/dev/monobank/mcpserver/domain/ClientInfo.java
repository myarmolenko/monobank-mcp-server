package dev.monobank.mcpserver.domain;

import java.util.List;

public record ClientInfo(
        String name,
        List<Account> accounts,
        List<Jar> jars
) {

}
