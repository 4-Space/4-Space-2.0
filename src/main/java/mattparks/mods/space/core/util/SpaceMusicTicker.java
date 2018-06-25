package mattparks.mods.space.core.util;

import mattparks.mods.space.core.proxy.ClientProxy;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SpaceMusicTicker extends MusicTicker {
	public SpaceMusicTicker(Minecraft mc) {
		super(mc);
	}

	@Override
	public void update() {
		MusicTicker.MusicType musictype = this.field_147677_b.func_147109_W();

		if (FMLClientHandler.instance().getWorldClient() != null && FMLClientHandler.instance().getWorldClient().provider instanceof IGalacticraftWorldProvider) {
			musictype = ClientProxy.MUSIC_TYPE_SPACE;
		}

		if (this.field_147678_c != null) {
			if (!musictype.getMusicTickerLocation().equals(this.field_147678_c.getPositionedSoundLocation())) {
				this.field_147677_b.getSoundHandler().stopSound(this.field_147678_c);
				this.field_147676_d = MathHelper.getRandomIntegerInRange(this.field_147679_a, 0, musictype.func_148634_b() / 2);
			}

			if (!this.field_147677_b.getSoundHandler().isSoundPlaying(this.field_147678_c)) {
				this.field_147678_c = null;
				this.field_147676_d = Math.min(MathHelper.getRandomIntegerInRange(this.field_147679_a, musictype.func_148634_b(), musictype.func_148633_c()), this.field_147676_d);
			}
		}

		if (this.field_147678_c == null && this.field_147676_d-- <= 0) {
			this.field_147678_c = PositionedSoundRecord.func_147673_a(musictype.getMusicTickerLocation());
			this.field_147677_b.getSoundHandler().playSound(this.field_147678_c);
			this.field_147676_d = Integer.MAX_VALUE;
		}
	}
}