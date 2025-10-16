package com.padbro.greeterbro.client.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "GreeterBro")
public class GreeterBroConfig extends PartitioningSerializer.GlobalData {
  @ConfigEntry.Category("general")
  @ConfigEntry.Gui.TransitiveObject
  public GeneralConfig generalConfig = new GeneralConfig();

  @ConfigEntry.Category("afk")
  @ConfigEntry.Gui.TransitiveObject
  public AfkConfig afkConfig = new AfkConfig();

  @ConfigEntry.Category("returningPlayer")
  @ConfigEntry.Gui.TransitiveObject
  public ReturningPlayerConfig returningPlayerConfig = new ReturningPlayerConfig();

  @ConfigEntry.Category("firstJoin")
  @ConfigEntry.Gui.TransitiveObject
  public FirstJoinConfig firstJoinConfig = new FirstJoinConfig();

  @ConfigEntry.Category("nameChange")
  @ConfigEntry.Gui.TransitiveObject
  public NameChangeConfig nameChangeConfig = new NameChangeConfig();

  @ConfigEntry.Category("specialGreetings")
  @ConfigEntry.Gui.TransitiveObject
  public SpecialGreetingsConfig specialGreetings = new SpecialGreetingsConfig();

  @ConfigEntry.Category("blacklist")
  @ConfigEntry.Gui.TransitiveObject
  public BlacklistConfig blacklistConfig = new BlacklistConfig();
}
