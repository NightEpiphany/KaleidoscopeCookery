package com.github.ysbbbbbb.kaleidoscopecookery.event.effect;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VitalityEvent {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(VitalityEvent::onLivingDeath);
    }

    private static void onLivingDeath(LivingEntity entity, DamageSource damageSource) {
        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        // FIXME：可能存在其他 mod 取消 LivingDeathEvent 事件，导致幼年实体刷出
        // TODO：是否添加粒子效果和音效？

        if (damageSource.getEntity() instanceof LivingEntity living && living.hasEffect(ModEffects.VITALITY)) {
            EntityType<?> type = entity.getType();
            Vec3 pos = entity.position();

            // 如果继承 AgeableMob 且成年
            if (entity instanceof AgeableMob mob && !mob.isBaby()) {
                if (type.create(level, EntitySpawnReason.EVENT) instanceof AgeableMob ageableMob) {
                    ageableMob.setBaby(true);
                    ageableMob.setPos(pos);
                    level.addFreshEntity(ageableMob);
                }
                return;
            }

            // 或者是僵尸
            if (entity instanceof Zombie mob && !mob.isBaby()) {
                // 5% 概率生成小村民
                if (level.getRandom().nextInt(20) == 0) {
                    Villager villager = new Villager(EntityType.VILLAGER, level);
                    villager.setBaby(true);
                    villager.setPos(pos);
                    level.addFreshEntity(villager);
                    return;
                }

                // 否则生成幼体
                if (type.create(level, EntitySpawnReason.EVENT) instanceof Zombie zombie) {
                    zombie.setBaby(true);
                    zombie.setPos(pos);
                    level.addFreshEntity(zombie);
                }
            }
        }
    }
}
