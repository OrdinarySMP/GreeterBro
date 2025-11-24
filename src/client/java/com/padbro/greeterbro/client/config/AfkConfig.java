package com.padbro.greeterbro.client.config;

import com.padbro.greeterbro.client.GreeterBroClient;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "afk")
public class AfkConfig implements ConfigData {
  @ConfigEntry.Gui.Tooltip private boolean enable = true;

  @ConfigEntry.Gui.Tooltip private int afkTime = 5;

  @ConfigEntry.Gui.Tooltip
  @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
  public AfkNotifyType notifyType = AfkNotifyType.Chat;

    public boolean getEnabled() {
        boolean enforceAfkMode = GreeterBroClient.serverConfig != null
                ? GreeterBroClient.serverConfig.afkConfig.enforceAfkMode
                : false;
        return enforceAfkMode || this.enable;
    }

    public int getAfkTime() {
        if (GreeterBroClient.serverConfig != null && GreeterBroClient.serverConfig.afkConfig.afkTime != -1) {
            return GreeterBroClient.serverConfig.afkConfig.afkTime;
        }
        return this.afkTime;
    }

  @Override
  public void validatePostLoad() {
    if (this.afkTime < 0) {
      this.afkTime = 1;
    }
  }
}
