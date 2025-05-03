package de.jardateien.smartchat.listeners;

import de.jardateien.smartchat.SmartChatAddon;
import de.jardateien.smartchat.config.SmartChatConfiguration;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import java.util.Objects;

public class ChatReceiveListener {

  private final SmartChatConfiguration configuration;

  public ChatReceiveListener(SmartChatAddon addon) {
    this.configuration = addon.configuration();
  }

  @Subscribe
  public void onChatReceive(ChatReceiveEvent receiveEvent) {
    if(!this.configuration.enabled().get() || !this.configuration.enabledPing().get()) return;
    ChatMessage chatMessage = receiveEvent.chatMessage();

    LabyAPI labyAPI = Laby.labyAPI();
    if(Objects.equals(chatMessage.getSenderUniqueId(), labyAPI.getUniqueId())) return;

    String message = chatMessage.getFormattedText();
    if(!message.contains(labyAPI.getName())) return;

    labyAPI.minecraft().sounds()
        .playSound(ResourceLocation.create("labymod", "marker.marker_notify"),
            this.configuration.getVolume().get(), this.configuration.getPitch().get());
  }

}
