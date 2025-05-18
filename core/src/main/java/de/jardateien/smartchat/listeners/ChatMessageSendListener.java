package de.jardateien.smartchat.listeners;

import de.jardateien.smartchat.SmartChatAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

public class ChatMessageSendListener {

  @Subscribe(125)
  public void onChatReceive(ChatMessageSendEvent receiveEvent) {
    if (receiveEvent.isCancelled()) return;

    receiveEvent.changeMessage(
        SmartChatAddon.placeholderRegistry().replacePlaceholders(receiveEvent.getMessage()),
        receiveEvent.getHistoryText()
    );
  }


}
