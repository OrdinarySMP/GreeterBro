package com.padbro.greeterbro.client.config;

import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

public enum CacheClearType implements SelectionListEntry.Translatable  {
  OnJoin,
  OnNewSession,
  OnNewDay,
  Never;

  @Override
  public @NotNull String getKey() {
    return "text.autoconfig.GreeterBro.option.returningPlayerConfig.cacheClearType." + toString().toLowerCase();
  }
}
