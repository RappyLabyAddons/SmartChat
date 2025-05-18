package de.jardateien.smartchat.registry.placeholder;

import de.jardateien.smartchat.SmartChatAddon;
import de.jardateien.smartchat.api.SmartChatPlaceholder;
import net.labymod.api.Laby;
import net.labymod.api.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldDimensionPlaceholder extends SmartChatPlaceholder {

  public WorldDimensionPlaceholder(SmartChatAddon addon) {
    super(addon, "dimension");
  }

  @Override
  public @NotNull String getVersion() {
    return "1.0.0";
  }

  @Override
  public @Nullable String parse() {
    ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
    if(world == null) return null;
    return world.dimension().getPath();
  }
}
