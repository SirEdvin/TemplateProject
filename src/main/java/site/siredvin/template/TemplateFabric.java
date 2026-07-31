package site.siredvin.template;

//? if fabric {
import net.fabricmc.api.ModInitializer;

public final class TemplateFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TemplateMod.initialize("Fabric");
    }
}
//?}
