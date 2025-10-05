package com.padbro.greeterbro.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "afk")
public class AfkConfig implements ConfigData {
  @ConfigEntry.Gui.Tooltip public boolean enable = true;

  @ConfigEntry.Gui.Tooltip public int afkTime = 5;

  @ConfigEntry.Gui.Tooltip
  @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
  public AfkNotifyType notifyType = AfkNotifyType.Chat;

  @Override
  public void validatePostLoad() {
    if (this.afkTime < 0) {
      this.afkTime = 1;
    }
  }
}
