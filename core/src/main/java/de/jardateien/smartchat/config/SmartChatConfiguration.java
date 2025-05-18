package de.jardateien.smartchat.config;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@ConfigName("settings")
public class SmartChatConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SettingSection(value = "general", center = true)
  @TextFieldSetting
  private final ConfigProperty<String> locationFormat = new ConfigProperty<>("[x: {x} y: {y} z: {z} pitch: {pitch} yaw: {yaw}]");
  @TextFieldSetting
  private final ConfigProperty<String> positionFormat = new ConfigProperty<>("[x: {x} y: {y} z: {z}]");

  @SettingSection(value = "chatNotification", center = true)
  @SwitchSetting
  private final ConfigProperty<Boolean> pingSound = new ConfigProperty<>(true);
  @SettingRequires("pingSound")
  @SliderSetting(min = 0.1F, max = 2F, steps = 0.1F)
  private final ConfigProperty<Float> volume = new ConfigProperty<>(1F);
  @SettingRequires("pingSound")
  @SliderSetting(min = 0F, max = 2F, steps = 0.1F)
  private final ConfigProperty<Float> pitch = new ConfigProperty<>(1.2F);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<String> locationFormat() {
    return this.locationFormat;
  }
  public ConfigProperty<String> positionFormat() {
    return this.positionFormat;
  }

  public ConfigProperty<Boolean> enabledPing() {
    return this.pingSound;
  }
  public ConfigProperty<Float> getVolume() {
    return this.volume;
  }
  public ConfigProperty<Float> getPitch() {
    return this.pitch;
  }
}
