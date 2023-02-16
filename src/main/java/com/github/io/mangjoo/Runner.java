package com.github.io.mangjoo;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        // 기본 jda를 만들고
        JDA jda = JDABuilder
                .createDefault("MTA3NTM2MTAwOTIwNTc4NDYyOA.Gts9JO.95JyruT39hNMrwyt9EtXtsMFox5AS8c91NxKHM")
                .setStatus(OnlineStatus.ONLINE)
                .setAutoReconnect(true)
                .build();

        // jda에 이벤트를 감지하는 리스너를 넣는다.
        jda.addEventListener(new ForumListener(1075380898754723961L, 1075378959874805802L));
    }
}
