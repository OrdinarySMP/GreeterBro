package com.padbro.greeterbro.client.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.padbro.greeterbro.client.AfkManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class AfkCommand implements Command<FabricClientCommandSource> {
  @Override
  public int run(CommandContext<FabricClientCommandSource> context) {
    FabricClientCommandSource source = context.getSource();
    if (AfkManager.isAfk) {
      source.sendError(Text.translatable("text.command.GreeterBro.afk.error.isAfk"));
      return 0;
    }
    AfkManager.goAfk();
    return 0;
  }
}
