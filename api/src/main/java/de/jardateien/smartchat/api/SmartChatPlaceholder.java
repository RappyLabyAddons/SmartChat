package de.jardateien.smartchat.api;

import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Represents a dynamic placeholder used in chat messages.
 * <p>
 * A {@code SmartChatPlaceholder} is tied to a specific {@link LabyAddon} and is identified
 * by a namespace and an identifier. It can optionally define a permission, description, and
 * how it should be parsed into a value.
 */
public abstract class SmartChatPlaceholder {

  /**
   * A shared decimal formatter for placeholder values.
   * Formats numbers to a maximum of two decimal places.
   */
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

  /**
   * The associated addon that registered this placeholder.
   */
  protected final LabyAddon<?> addon;

  private final String namespace;
  private final String identifier;

  /**
   * Constructs a new {@code SmartChatPlaceholder} associated with a given addon namespace and identifier.
   *
   * @param addon      the addon registering this placeholder; must not be {@code null}
   * @param identifier the identifier of the placeholder; must not be {@code null}
   */
  public SmartChatPlaceholder(@NotNull LabyAddon<?> addon, @NotNull String identifier) {
    Objects.requireNonNull(addon, "addon must not be null");
    Objects.requireNonNull(identifier, "identifier must not be null");
    this.addon = addon;
    this.namespace = addon.addonInfo().getNamespace();
    this.identifier = identifier.toLowerCase();
  }

  /**
   * @return the namespace of the placeholder (based on the associated addon)
   */
  @NotNull
  public String getNamespace() {
    return this.namespace;
  }

  /**
   * @return the identifier of the placeholder
   */
  @NotNull
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * @return the I18n translation key for the placeholder description, or {@code null} to remove description
   */
  @Nullable
  public String getDescriptionKey() {
    return this.namespace + ".placeholders." + this.identifier;
  }

  /**
   * Gets the translated description of the placeholder.
   *
   * @return the translated description if available; {@code null} otherwise
   */
  @Nullable
  public String getDescription() {
    if (this.getDescriptionKey() == null || I18n.has(this.getDescriptionKey()))
      return null;
    return I18n.translate(this.getDescriptionKey());
  }

  /**
   * Gets the description as a {@link Component}.
   *
   * @return the description component if available; {@code null} otherwise
   */
  @Nullable
  public Component getDescriptionComponent() {
    if (this.getDescriptionKey() == null || I18n.has(this.getDescriptionKey()))
      return null;
    return Component.translatable(this.getDescriptionKey());
  }

  /**
   * Checks whether the placeholder is currently enabled.
   *
   * @return {@code true} if enabled; {@code false} otherwise
   */
  public boolean isEnabled() {
    return this.addon.configuration().enabled().get();
  }

  /**
   * Checks whether the player has permission to use this placeholder.
   *
   * @return {@code true} if no permission is required or the permission is granted; {@code false} otherwise
   */
  public boolean hasPermission() {
    if (this.getPermission() == null)
      return true;
    return Laby.labyAPI().permissionRegistry().isPermissionEnabled(this.getPermission());
  }

  /**
   * Gets the permission string required to use this placeholder.
   *
   * @return the permission string, or {@code null} if no permission is required
   */
  @Nullable
  public String getPermission() {
    return null;
  }

  /**
   * Gets the version of this placeholder.
   *
   * @return the version string; never {@code null}
   */
  @NotNull
  public abstract String getVersion();

  /**
   * Parses the placeholder and returns its current value.
   *
   * @return the resolved placeholder value; may be {@code null} if not available
   */
  @Nullable
  public abstract String parse();

  /**
   * Returns a string representation of the placeholder in the form {@code namespace:identifier}.
   *
   * @return a string representation of the placeholder
   */
  @Override
  public String toString() {
    return this.namespace + ":" + this.identifier;
  }
}
