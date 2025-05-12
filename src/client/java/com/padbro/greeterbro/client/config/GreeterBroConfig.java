package com.padbro.greeterbro.client.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "GreeterBro")
public class GreeterBroConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public GeneralConfig generalConfig = new GeneralConfig();

    @ConfigEntry.Category("firstJoin")
    @ConfigEntry.Gui.TransitiveObject
    public FirstJoinConfig firstJoinConfig = new FirstJoinConfig();

    @ConfigEntry.Category("nameChange")
    @ConfigEntry.Gui.TransitiveObject
    public NameChangeConfig nameChangeConfig = new NameChangeConfig();
}
