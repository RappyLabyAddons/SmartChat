package de.jardateien.smartchat.listeners;

import de.jardateien.smartchat.SmartChatAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.util.math.position.Position;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class ChatMessageSendListener {

  private final SmartChatAddon addon;
  private final DecimalFormat format;

  public ChatMessageSendListener(SmartChatAddon addon) {
    this.addon = addon;
    this.format = new DecimalFormat("#.##");
  }

  @Subscribe(125)
  public void onChatReceive(ChatMessageSendEvent receiveEvent) {
    if (receiveEvent.isCancelled() || !this.addon.configuration().enabled().get()) {
      return;
    }

    ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
    Optional<Player> player = world.getPlayer(Laby.labyAPI().getUniqueId());
    if(player.isEmpty()) return;

    Player member = player.get();
    String value = receiveEvent.getOriginalMessage();
    Map<String, String> list = new HashMap<>();
    list.put("{loc}", this.addon.configuration().locationFormat().get());
    list.put("{pos}", this.addon.configuration().positionFormat().get());

    Position position = member.position();

    list.put("{x}", this.format.format(position.getX()));
    list.put("{y}", this.format.format(position.getY()));
    list.put("{z}", this.format.format(position.getZ()));
    list.put("{pitch}", this.format.format(member.getRotationPitch()));
    list.put("{yaw}", this.format.format(member.getRotationYaw()));

    list.put("{biome}", world.getReadableBiomeName());
    list.put("{gamemode}", member.gameMode().getName());
    list.put("{dimension}", world.dimension().getPath());
    list.put("{heart}", this.format.format(member.getHealth()));

    for (Entry<String, String> entry : list.entrySet()) {
      if(value.toLowerCase().contains(entry.getKey())) {
        value = value.replace(entry.getKey(), entry.getValue());
      }
    }

    receiveEvent.changeMessage(value);
  }


}
