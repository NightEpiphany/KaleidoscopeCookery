package com.github.ysbbbbbb.kaleidoscopecookery.config;


public class GeneralConfig {
    public static final TemporaryHolder<Boolean> SATIATED_SHIELD_ABSORB_ENABLED = TemporaryHolder.of(true);
    public static final TemporaryHolder<Boolean> SATIATED_SHIELD_ABSORB_EXCESS_DAMAGE = TemporaryHolder.of(true);
    public static final TemporaryHolder<Boolean> STOVE_FIRING_ENABLED = TemporaryHolder.of(false);
//    public static ModConfigSpec init() {
//        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
//        general(builder);
//        return builder.build();
//    }
//
//    /**
//     * 饱腹代偿属性可能在某些整合里过于 OP，故提供一个开关来关闭它。
//     */
//    public static ModConfigSpec.BooleanValue SATIATED_SHIELD_ABSORB_ENABLED;
//    public static ModConfigSpec.BooleanValue SATIATED_SHIELD_ABSORB_EXCESS_DAMAGE;
//    public static ModConfigSpec.BooleanValue STOVE_FIRING_ENABLED;
//
//    private static void general(ModConfigSpec.Builder builder) {
//        builder.push("cookery");
//
//        builder.comment("Whether enabling the Satiated Shield effect.");
//        SATIATED_SHIELD_ABSORB_ENABLED = builder.define("SatiatedShieldAbsorbEnabled", true);
//
//        builder.comment("Whether the Satiated Shield effect should absorb excess damage beyond its capacity.");
//        SATIATED_SHIELD_ABSORB_EXCESS_DAMAGE = builder.define("SatiatedShieldAbsorbExcessDamage", true);
//
//        builder.comment("Whether enabling the Stove Firing effect.");
//        STOVE_FIRING_ENABLED = builder.define("StoveFiringEnabled", false);
//
//        builder.pop();
//    }

    public static class TemporaryHolder<T> {
        private final T value;

        private TemporaryHolder(T value) {
            this.value = value;
        }

        public static <T> TemporaryHolder<T> of(T value) {
            return new TemporaryHolder<>(value);
        }

        public T get() {
            return value;
        }
    }
}
