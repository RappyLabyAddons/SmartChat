package de.jardateien.smartchat.api;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;

/**
 * A registry for managing {@link SmartChatPlaceholder} instances.
 */
@Referenceable
public interface PlaceholderRegistry {

  /**
   * Registers a new {@link SmartChatPlaceholder} in the registry.
   *
   * @param placeholder the placeholder to register; must not be {@code null}
   */
  void register(@NotNull SmartChatPlaceholder placeholder);

  /**
   * Retrieves all registered {@link SmartChatPlaceholder} instances.
   *
   * @return a collection of all registered placeholders
   */
  Collection<SmartChatPlaceholder> getPlaceholders();

  /**
   * Replaces all registered placeholders in the given text with their resolved values.
   *
   * @param text the input text possibly containing placeholders
   * @return the text with placeholders replaced
   */
  String replacePlaceholders(String text);

  /**
   * Counts how many times the given placeholder identifier appears in all registered placeholders.
   *
   * @param identifier the placeholder identifier to look for
   * @return the number of times the identifier appears
   */
  int countPlaceholders(String identifier);
}

