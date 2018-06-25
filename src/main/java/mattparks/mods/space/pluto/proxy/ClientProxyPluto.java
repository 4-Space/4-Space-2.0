package mattparks.mods.space.pluto.proxy;

import java.util.ArrayList;

import mattparks.mods.space.pluto.dimension.SkyProviderPluto;
import mattparks.mods.space.pluto.dimension.WorldProviderPluto;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxyPluto extends CommonProxyPluto {
	private static Minecraft mc = FMLClientHandler.instance().getClient();

	private static int renderIndexSapphireArmor;

	public static ArrayList<SoundPoolEntry> newMusic = new ArrayList<SoundPoolEntry>();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		ClientProxyPluto.renderIndexSapphireArmor = RenderingRegistry.addNewArmourRendererPrefix("sapphire");

		super.preInit(event);
	}

	@Override
	public int getSapphireArmorRenderIndex() {
		return ClientProxyPluto.renderIndexSapphireArmor;
	}

	public static void registerEntityRenderers() {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new TickHandlerClient());

		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		ClientProxyPluto.registerEntityRenderers();
		super.postInit(event);
	}

	public void registerRenderInfo() {
	}

	@Override
	public int getBlockRender(Block block) {
		return -1;
	}

	public static class TickHandlerClient {
		@SideOnly(Side.CLIENT)
		@SubscribeEvent
		public void onClientTick(ClientTickEvent event) {
			final Minecraft minecraft = FMLClientHandler.instance().getClient();

			final WorldClient world = minecraft.theWorld;

			if (world != null) {
				if (world.provider instanceof WorldProviderPluto) {
					if (world.provider.getSkyRenderer() == null) {
						world.provider.setSkyRenderer(new SkyProviderPluto((IGalacticraftWorldProvider) world.provider));
					}

					if (world.provider.getCloudRenderer() == null) {
						world.provider.setCloudRenderer(new CloudRenderer());
					}
				}
			}
		}
	}
}
