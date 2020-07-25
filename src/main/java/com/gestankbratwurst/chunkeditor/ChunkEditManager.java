package com.gestankbratwurst.chunkeditor;

import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of ChunkEditor and was created at the 15.07.2020
 *
 * ChunkEditor can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ChunkEditManager implements Runnable {

  public ChunkEditManager(ChunkEditor plugin) {
    Bukkit.getScheduler().runTaskTimer(plugin, this, 20L, 1L);
  }

  @Getter
  @Setter
  private long tickStartNano = 0L;
  @Getter
  private boolean running = false;
  @Getter
  private FindState currentState = FindState.REGION_SEARCH;
  private TickableProcess tickableProcess = null;
  private long lastNotification = 0L;
  private Consumer<Chunk> chunkConsumer = null;
  private double maxMillisPerTick = 20.0;
  @Getter
  @Setter
  private NotificationType notificationType = NotificationType.CONSOLE;
  @Getter
  @Setter
  private int secondsBetweenNotification = 5;
  @Getter
  @Setter
  private int maxParallelCalls = 2;

  public void start(World world, Consumer<Chunk> chunkConsumer, double maxMillisPerTick) {
    if (running) {
      return;
    }
    notificationType.call(Msg.info("Starting chunk editing with:"));
    notificationType.call(Msg.info("    - §e" + maxMillisPerTick + "§7 millis per tick."));
    notificationType.call(Msg.info("    - §e" + maxParallelCalls + "§7 parallel chunk calls."));
    notificationType.call(Msg.info("    - §e" + secondsBetweenNotification + "§7 seconds between notifications."));
    notificationType.call(Msg.info("    - §e" + notificationType.toString() + "§7 notification type."));
    running = true;
    this.maxMillisPerTick = maxMillisPerTick;
    this.chunkConsumer = chunkConsumer;
    if (this.currentState == FindState.REGION_SEARCH) {
      tickableProcess = new RegionParser(world);
    }
  }

  public void pause() {
    this.running = false;
  }

  public void stop() {
    this.tickableProcess = null;
    this.running = false;
  }

  @Override
  public void run() {
    if (!running) {
      return;
    }
    while ((System.nanoTime() - tickStartNano) / 1E6 < maxMillisPerTick) {
      boolean pause = tickableProcess.proceed();
      if (tickableProcess.isDone()) {
        notificationType.call(tickableProcess.getProgress(System.currentTimeMillis() - lastNotification));
        lastNotification = 0L;
        if (tickableProcess instanceof RegionParser) {
          RegionParser parser = (RegionParser) tickableProcess;
          this.tickableProcess = new ChunkIterator(parser.getChunkKeys(), parser.getWorld(), chunkConsumer, maxParallelCalls);
          this.currentState = FindState.CHUNK_ITERATION;
        } else {
          this.stop();
        }
        return;
      }
      if (pause) {
        break;
      }
    }
    long now = System.currentTimeMillis();
    if (now - lastNotification > secondsBetweenNotification * 1000) {
      notificationType.call(tickableProcess.getProgress(now - lastNotification));
      lastNotification = now;
    }
  }

}
