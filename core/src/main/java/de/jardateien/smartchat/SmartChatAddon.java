package de.jardateien.smartchat;

import de.jardateien.smartchat.api.PlaceholderRegistry;
import de.jardateien.smartchat.api.generated.ReferenceStorage;
import de.jardateien.smartchat.listeners.ChatMessageSendListener;
import de.jardateien.smartchat.listeners.ChatReceiveListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;
import de.jardateien.smartchat.config.SmartChatConfiguration;

@AddonMain
public class SmartChatAddon extends LabyAddon<SmartChatConfiguration> {

  public static PlaceholderRegistry placeholderRegistry;

  @Override
  protected void enable() {
    placeholderRegistry = ((ReferenceStorage) this.referenceStorageAccessor()).placeholderRegistry();

    this.registerSettingCategory();

    this.registerListener(new ChatMessageSendListener());
    this.registerListener(new ChatReceiveListener(this));
  }

  @Override
  protected Class<SmartChatConfiguration> configurationClass() {
    return SmartChatConfiguration.class;
  }

  public static PlaceholderRegistry placeholderRegistry() {
    return placeholderRegistry;
  }
}
