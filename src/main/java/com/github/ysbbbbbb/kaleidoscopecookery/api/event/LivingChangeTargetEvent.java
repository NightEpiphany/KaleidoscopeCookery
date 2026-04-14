package com.github.ysbbbbbb.kaleidoscopecookery.api.event;

import com.github.ysbbbbbb.kaleidoscopecookery.event.ChangeTargetEvent;
import com.github.ysbbbbbb.kaleidoscopecookery.util.event.CancellableEvent;
import net.minecraft.world.entity.LivingEntity;

public class LivingChangeTargetEvent extends CancellableEvent {
    private final LivingEntity entity;
    private final ILivingTargetType targetType;
    private final LivingEntity originalTarget;
    private LivingEntity newTarget;


    public LivingChangeTargetEvent(LivingEntity entity, LivingEntity originalTarget, ILivingTargetType targetType)
    {
        this.entity = entity;
        this.originalTarget = originalTarget;
        this.newTarget = originalTarget;
        this.targetType = targetType;
    }

    public LivingEntity getNewTarget()
    {
        return newTarget;
    }

    public void setNewTarget(LivingEntity newTarget)
    {
        this.newTarget = newTarget;
    }

    public ILivingTargetType getTargetType()
    {
        return targetType;
    }

    public LivingEntity getOriginalTarget()
    {
        return originalTarget;
    }

    public static void register() {
        CALLBACK.register(event -> {
            if (event instanceof LivingChangeTargetEvent changeTargetEvent)
                ChangeTargetEvent.onTarget(changeTargetEvent);
        });
    }

    @Override
    public void post() {
        CALLBACK.invoker().post(this);
    }

    public static interface ILivingTargetType { }

    public static enum LivingTargetType implements ILivingTargetType
    {

        MOB_TARGET,

        BEHAVIOR_TARGET;
    }
}
