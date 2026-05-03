package com.github.ysbbbbbb.kaleidoscopecookery.mixin.client;

import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.client.animation.IExtensibleEnum;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import org.spongepowered.asm.mixin.*;

@Implements({
        @Interface(iface = IExtensibleEnum.class, prefix = "i_extend$")
})
@Environment(EnvType.CLIENT)
@Mixin(HumanoidModel.ArmPose.class)
public class HumanoidModelImplementationMixin {

}
