package de.jardateien.smartchat;

import de.jardateien.smartchat.api.PlaceholderRegistry;
import de.jardateien.smartchat.api.generated.ReferenceStorage;
import de.jardateien.smartchat.listeners.ChatMessageSendListener;
import de.jardateien.smartchat.listeners.ChatReceiveListener;
import de.jardateien.smartchat.registry.placeholder.*;
import de.jardateien.smartchat.ui.activity.PlaceholderMenuChatActivity;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatButtonWidget;
import net.labymod.api.client.resources.ResourceLocation;
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

    ChatButtonWidget placeholderMenu = ChatButtonWidget.icon(
        "placeholders",
        Icon.texture(ResourceLocation.create("smartchat", "textures/chat.png")),
        PlaceholderMenuChatActivity::new
    );
    placeholderMenu.setHoverComponent(Component.translatable("smartchat.chatInput.placeholderMenu.name"));
    placeholderMenu.property(this.configuration().enabled());
    this.labyAPI().chatProvider().chatInputService().register(placeholderMenu);

    placeholderRegistry.register(new GameModePlaceholder(this));
    placeholderRegistry.register(new HealthPlaceholder(this));
    placeholderRegistry.register(new LocationPitchPlaceholder(this));
    placeholderRegistry.register(new LocationPlaceholder(this));
    placeholderRegistry.register(new LocationXPlaceholder(this));
    placeholderRegistry.register(new LocationYawPlaceholder(this));
    placeholderRegistry.register(new LocationYPlaceholder(this));
    placeholderRegistry.register(new LocationZPlaceholder(this));
    placeholderRegistry.register(new PositionPlaceholder(this));
    placeholderRegistry.register(new WorldBiomePlaceholder(this));
    placeholderRegistry.register(new WorldDimensionPlaceholder(this));
  }

  @Override
  protected Class<SmartChatConfiguration> configurationClass() {
    return SmartChatConfiguration.class;
  }

  public static PlaceholderRegistry placeholderRegistry() {
    return placeholderRegistry;
  }
}
