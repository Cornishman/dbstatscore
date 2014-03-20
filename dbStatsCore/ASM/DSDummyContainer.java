package dbStatsCore.ASM;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;

public class DSDummyContainer extends DummyModContainer {

    public DSDummyContainer() {
            super(new ModMetadata());
            ModMetadata meta = getMetadata();
            meta.modId = "DbStatsCore";
            meta.name = "DbStatsCore";
            meta.version = "0.1.0";
            meta.credits = "";
            meta.authorList = Arrays.asList("wattzy");
            meta.description = "";
            meta.url = "";
            meta.updateUrl = "";
            meta.screenshots = new String[0];
            meta.logoFile = "";
            meta.parent = "DbStats";
    }
    
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
    	bus.register(this);
        return true;
    }
    
    @Subscribe
    public void modConstruction(FMLConstructionEvent evt){

    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent evt) {

    }

    @Subscribe
    public void init(FMLInitializationEvent evt) {

    }


    @Subscribe
    public void postInit(FMLPostInitializationEvent evt) {

    }
}
