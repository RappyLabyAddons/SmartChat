package de.jardateien.smartchat.ui.widget;

import de.jardateien.smartchat.SmartChatAddon;
import de.jardateien.smartchat.api.PlaceholderRegistry;
import de.jardateien.smartchat.api.SmartChatPlaceholder;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import java.util.Objects;

@Link("placeholder.lss")
@AutoWidget
public class PlaceholderWidget extends HorizontalListWidget {

  private final SmartChatPlaceholder placeholder;
  private final PlaceholderRegistry registry;

  public PlaceholderWidget(SmartChatPlaceholder placeholder) {
    this.placeholder = placeholder;
    this.registry = SmartChatAddon.placeholderRegistry();
  }

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);
    Component identifier;
    boolean duplicate = this.registry.countPlaceholders(this.placeholder.getIdentifier()) > 1;
    if(duplicate) {
      identifier = Component
          .text(this.placeholder.toString(), NamedTextColor.GREEN)
          .hoverEvent(HoverEvent.showText(Component.translatable(
              "smartchat.chatInput.placeholderMenu.duplicateIdentifier"
          )));
    } else {
      identifier = Component.empty()
          .append(Component.text(this.placeholder.getNamespace() + ":", NamedTextColor.GRAY))
          .append(Component.text(this.placeholder.getIdentifier(), NamedTextColor.GREEN))
          .hoverEvent(HoverEvent.showText(Component.translatable(
              "smartchat.chatInput.placeholderMenu.uniqueIdentifier"
          )));
    }
    Component hoverComponent = Component.empty().append(Component.translatable(
        "smartchat.chatInput.placeholderMenu.hover.version",
        NamedTextColor.YELLOW,
        Component.text(this.placeholder.getVersion(), NamedTextColor.GRAY)
    ));

    if(this.placeholder.getDescription() != null) {
      hoverComponent
          .append(Component.newline())
          .append(Component.translatable(
              "smartchat.chatInput.placeholderMenu.hover.description",
              NamedTextColor.YELLOW,
              Objects.requireNonNull(
                  this.placeholder.getDescriptionComponent()
              ).color(NamedTextColor.GRAY)
          ));
    }

    identifier
        .append(Component.space())
        .append(
            Component
                .text("ℹ", NamedTextColor.YELLOW)
                .hoverEvent(HoverEvent.showText(hoverComponent))
        );

    ButtonWidget buttonWidget = ButtonWidget.i18n("smartchat.chatInput.placeholderMenu.use", () ->
      Laby.references().chatExecutor().insertText(String.format(
          "{%s}",
          duplicate ? this.placeholder.toString() : this.placeholder.getIdentifier()
      ))
    );
    buttonWidget.addId("use-button");

    this.addEntry(ComponentWidget.component(identifier).addId("identifier"));
    this.addEntry(buttonWidget);
  }

  public SmartChatPlaceholder getPlaceholder() {
    return this.placeholder;
  }

  @Override
  public int getSortingValue() {
    return 1;
  }
}
