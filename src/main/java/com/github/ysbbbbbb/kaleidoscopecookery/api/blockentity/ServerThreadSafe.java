package com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 用于修复C2ME的异步区块序列化存储
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface ServerThreadSafe {
}
