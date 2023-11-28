package ru.djets.webclient.services;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.installer.NgrokVersion;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Proto;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NgrokClientServiceImpl implements NgrokClientService {
    @Override
    public String getBotLocalPath() {
        NgrokClient ngrokClient = new NgrokClient.Builder()
                .withJavaNgrokConfig(new JavaNgrokConfig.Builder()
                        .withNgrokVersion(NgrokVersion.V3)
                        .build())
                .build();
        Tunnel httpTunnel = ngrokClient.connect(new CreateTunnel.Builder()
                .withProto(Proto.HTTP)
                .withAddr(8080)
                .build());
        log.info("BOT LOCAL URL: {}", httpTunnel.getPublicUrl());
        return httpTunnel.getPublicUrl();
    }
}
