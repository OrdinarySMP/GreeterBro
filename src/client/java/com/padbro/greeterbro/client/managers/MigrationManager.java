package com.padbro.greeterbro.client.managers;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.GreeterBroConfig;

public class MigrationManager {
    public static void migrate() {
        boolean migrated = false;
        GreeterBroConfig config = GreeterBroClient.getConfig();
        int configVersion = config.generalConfig.configVersion;

        if (configVersion < 1) {
            float min = config.generalConfig.delayRange.getMin();
            float max = config.generalConfig.delayRange.getMax();
            if (min != 3.0
                    && max != 5.0) {

                config.generalConfig.delayRange.setMax(max / 20);
                config.generalConfig.delayRange.setMin(min / 20);
            }

            config.generalConfig.configVersion = 1;
            migrated = true;
        }

        if (migrated) {
            GreeterBroClient.saveConfig();
        }
    }
}
