package de.jardateien.smartchat.registry;

import de.jardateien.smartchat.api.PlaceholderRegistry;
import de.jardateien.smartchat.api.SmartChatPlaceholder;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Implements(PlaceholderRegistry.class)
public class DefaultPlaceholderRegistry implements PlaceholderRegistry {

  private final Map<String, SmartChatPlaceholder> placeholders = new HashMap<>();

  @Override
  public void register(@NotNull SmartChatPlaceholder placeholder) {
    if(this.placeholders.containsKey(placeholder.toString())) {
      throw new IllegalArgumentException("Placeholder '" + placeholder + "' is already registered");
    }
    this.placeholders.put(placeholder.toString(), placeholder);
  }

  @Override
  public Collection<SmartChatPlaceholder> getPlaceholders() {
    return Collections.unmodifiableCollection(this.placeholders.values());
  }

  @Override
  public String replacePlaceholders(String text) {
    for(SmartChatPlaceholder placeholder : this.placeholders.values()) {
      if(!placeholder.isEnabled() || !placeholder.hasPermission()) continue;
      String value = placeholder.parse();
      if(value == null) continue;

      boolean needsNamespace = this.countPlaceholders(placeholder.getIdentifier()) > 1;
      String identifier = String.format("{%s}", placeholder.getIdentifier());
      String fullIdentifier = String.format("{%s}", placeholder);

      if(!needsNamespace && text.contains(identifier)) {
        text = text.replace(identifier, value);
      }
      if(text.contains(fullIdentifier)) {
        text = text.replace(fullIdentifier, value);
      }
    }

    return text;
  }

  public int countPlaceholders(String identifier) {
    int result = 0;
    for (SmartChatPlaceholder placeholder : this.placeholders.values()) {
      if(placeholder.getIdentifier().equals(identifier.toLowerCase())) {
        result++;
      }
    }

    return result;
  }
}
