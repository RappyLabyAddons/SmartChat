package de.jardateien.smartchat.ui.activity;

import de.jardateien.smartchat.SmartChatAddon;
import de.jardateien.smartchat.api.PlaceholderRegistry;
import de.jardateien.smartchat.api.SmartChatPlaceholder;
import de.jardateien.smartchat.ui.widget.PlaceholderWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabSettingActivity;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@Link("chat-tab.lss")
@AutoActivity
public class PlaceholderMenuChatActivity extends ChatInputTabSettingActivity<FlexibleContentWidget> {

  private final PlaceholderRegistry registry;

  public PlaceholderMenuChatActivity() {
    this.registry = SmartChatAddon.placeholderRegistry();
  }

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);
    this.contentWidget = new FlexibleContentWidget();
    this.contentWidget.addId("window");

    DivWidget titleBar = new DivWidget();
    titleBar.addId("title-bar");
    titleBar.addChild(ComponentWidget.i18n("smartchat.chatInput.placeholderMenu.title"));

    VerticalListWidget<PlaceholderWidget> placeholders = new VerticalListWidget<>();
    placeholders.addId("placeholders");
    for(SmartChatPlaceholder placeholder : this.registry.getPlaceholders()) {
      placeholders.addChild(new PlaceholderWidget(placeholder));
    }
    placeholders.setComparator((o1, o2) -> {
      if(!(o1 instanceof PlaceholderWidget p1 && o2 instanceof PlaceholderWidget p2)) {
        return 0;
      }
      SmartChatPlaceholder placeholder1 = p1.getPlaceholder();
      SmartChatPlaceholder placeholder2 = p2.getPlaceholder();
      int compareNamespace = placeholder1.getNamespace().compareTo(placeholder2.getNamespace());
      if(compareNamespace != 0) {
        return compareNamespace;
      }

      return placeholder1.getIdentifier().compareTo(placeholder2.getIdentifier());
    });

    this.contentWidget.addContent(titleBar);
    this.contentWidget.addFlexibleContent(new ScrollWidget(placeholders));
    this.document.addChild(this.contentWidget);
  }
}
