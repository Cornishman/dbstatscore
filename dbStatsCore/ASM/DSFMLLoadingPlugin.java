package dbStatsCore.ASM;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class DSFMLLoadingPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{DSClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return DSDummyContainer.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

}
