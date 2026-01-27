package com.github.ysbbbbbb.kaleidoscopecookery.client.animation;

import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.client.animation.EnumProxy;
import com.github.ysbbbbbb.kaleidoscopecookery.util.neo.client.animation.IArmPoseTransformer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义的第三人称姿势
 */
public class CustomArmPose {
    /**
     * 玩家手持大盘菜时，举起的动画
     */
    @Deprecated
    public static final EnumProxy<HumanoidModel.ArmPose> LIFT_POSE = new EnumProxy<>(HumanoidModel.ArmPose.class, false,
            (IArmPoseTransformer) (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = -Mth.PI;
                    model.rightArm.zRot = -Mth.PI * 0.025f;
                } else {
                    model.leftArm.xRot = -Mth.PI * 0.5f;
                    model.leftArm.zRot = Mth.PI * 0.025f;
                }
            });
}
