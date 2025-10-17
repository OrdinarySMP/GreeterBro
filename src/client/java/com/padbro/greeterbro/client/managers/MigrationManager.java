package com.padbro.greeterbro.client.managers;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.GreeterBroConfig;

public class MigrationManager {
  public static void migrate() {
    boolean migrated = false;
    GreeterBroConfig config = GreeterBroClient.getConfig();
    int configVersion = config.generalConfig.configVersion;

    if (configVersion < 1) {
      if (config.generalConfig.delayRange.min != 3.0
          && config.generalConfig.delayRange.max != 5.0) {
        config.generalConfig.delayRange.max /= 20;
        config.generalConfig.delayRange.min /= 20;
      }

      config.generalConfig.configVersion = 1;
      migrated = true;
    }

    if (migrated) {
      GreeterBroClient.saveConfig();
    }
  }
}
