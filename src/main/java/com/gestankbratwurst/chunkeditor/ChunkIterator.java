package com.gestankbratwurst.chunkeditor;

import it.unimi.dsi.fastutil.longs.LongList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
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
public class ChunkIterator implements TickableProcess {

  public ChunkIterator(LongList chunkKeys, World world, Consumer<Chunk> chunkConsumer, int maxParallelCalls) {
    this.chunkKeys = chunkKeys;
    this.size = chunkKeys.size();
    this.world = world;
    this.chunkConsumer = chunkConsumer;
    this.maxParallelCalls = maxParallelCalls;
    this.activeCalls = new AtomicInteger();
  }

  private final AtomicInteger activeCalls;
  private final int maxParallelCalls;
  private final World world;
  private final LongList chunkKeys;
  private final int size;
  private final Consumer<Chunk> chunkConsumer;
  private int index = 0;
  private boolean done = false;
  private int lastIndex = 0;
  private int freeHits = 0;


  private String percent() {
    return Msg.format(100D / size * index) + "%";
  }

  private String chps(long dt) {
    int chunkDelta = index - (lastIndex - freeHits);
    lastIndex = index;
    if (chunkDelta < 0) {
      chunkDelta = 0;
    }
    freeHits = 0;
    return Msg.format(chunkDelta * 1E3 / dt) + " Chunks/s";
  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public boolean proceed() {
    if (index == size) {
      this.done = true;
      return false;
    }
    long key = chunkKeys.getLong(index++);
    if (world.isChunkGenerated(key)) {
      activeCalls.incrementAndGet();
      world.getChunkAtAsync((int) key, (int) (key >> 32)).thenAccept(chunkConsumer).thenRun(activeCalls::decrementAndGet);
      return activeCalls.get() != maxParallelCalls;
    } else {
      freeHits++;
    }
    return true;
  }

  @Override
  public String getProgress(long millisDelta) {
    return "§7Iterating chunks: §e" + index + "§7/§e" + size + " §e[§7" + chps(millisDelta) + "§e] [§7" + percent() + "§e]";
  }

}
