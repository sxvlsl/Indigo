package com.mdns.indigo.networking;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.thread.ThreadExecutor;

public class SafeExecutionContext {
    private final ThreadExecutor<?> executor;

    public SafeExecutionContext(MinecraftServer server) {
        this.executor = server;
    }

    public SafeExecutionContext(MinecraftClient client) {
        this.executor = client;
    }

    public boolean isOnMainThread() {
        return executor.isOnThread();
    }

    public void executeSafe(Runnable task) {
        if (isOnMainThread()) {
            task.run();
        } else {
            executor.execute(task);
        }
    }

    public void executeNextTick(Runnable task) {
        executor.execute(task);
    }
}
