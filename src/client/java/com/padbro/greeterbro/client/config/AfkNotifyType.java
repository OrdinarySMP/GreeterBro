package com.padbro.greeterbro.client.config;

import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

public enum AfkNotifyType implements SelectionListEntry.Translatable {
  Disabled,
  Chat,
  Overlay;

  @Override
  public @NotNull String getKey() {
    return "text.autoconfig.GreeterBro.option.afkConfig.afkNotifyType." + toString().toLowerCase();
  }
}
