package ca.bungo.bungopatches;

import ca.bungo.bungopatches.mixin.CraftPlayerMixin;
import cn.apisium.papershelled.annotation.Mixin;
import cn.apisium.papershelled.plugin.PaperShelledPlugin;
import cn.apisium.papershelled.plugin.PaperShelledPluginDescription;
import cn.apisium.papershelled.plugin.PaperShelledPluginLoader;
import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.annotation.plugin.*;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@SuppressWarnings("unused")
@Plugin(name = "BungoPatches", version = "@@RELEASE_VERSION@@")
@Description("Bungos patches to fix bad code design")
@Author("Bungo")
@Website("https://bungo.ca")
@ApiVersion(ApiVersion.Target.v1_17)
@Mixin({
    CraftPlayerMixin.class
})
public class Main extends PaperShelledPlugin {
    public Main(PaperShelledPluginLoader loader, PaperShelledPluginDescription paperShelledDescription, PluginDescriptionFile description, File file) {
        super(loader, paperShelledDescription, description, file);
    }

    @Override
    public @NotNull PluginMeta getPluginMeta() {
        return this.getDescription();
    }
}
