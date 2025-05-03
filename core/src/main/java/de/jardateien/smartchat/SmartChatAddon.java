package de.jardateien.smartchat;

import de.jardateien.smartchat.listeners.ChatMessageSendListener;
import de.jardateien.smartchat.listeners.ChatReceiveListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import de.jardateien.smartchat.config.SmartChatConfiguration;

@AddonMain
public class SmartChatAddon extends LabyAddon<SmartChatConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(new ChatMessageSendListener(this));
    this.registerListener(new ChatReceiveListener(this));
  }

  @Override
  protected Class<SmartChatConfiguration> configurationClass() {
    return SmartChatConfiguration.class;
  }
}
