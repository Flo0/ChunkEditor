package com.gestankbratwurst.chunkeditor;

import java.util.function.Consumer;
import org.bukkit.Chunk;
import org.bukkit.World;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of ChunkEditor and was created at the 16.07.2020
 *
 * ChunkEditor can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ChunkEditorAPI {

  private static ChunkEditorAPI instance;

  protected static void init(ChunkEditManager chunkEditManager) {
    instance = new ChunkEditorAPI(chunkEditManager);
  }

  public static ChunkEditorAPI get() {
    return instance;
  }

  private ChunkEditorAPI(ChunkEditManager chunkEditManager) {
    this.chunkEditManager = chunkEditManager;
  }

  private final ChunkEditManager chunkEditManager;

  public boolean isRunning() {
    return chunkEditManager.isRunning();
  }

  public void start(World world, Consumer<Chunk> chunkConsumer) {
    chunkEditManager.start(world, chunkConsumer, 20D);
  }

  public void start(World world, Consumer<Chunk> chunkConsumer, double maxMillisPerTick) {
    chunkEditManager.start(world, chunkConsumer, maxMillisPerTick);
  }

  public void stop() {
    chunkEditManager.stop();
  }

  public void pause() {
    chunkEditManager.pause();
  }

  public NotificationType getNotificationType() {
    return chunkEditManager.getNotificationType();
  }

  public void setNotificationType(NotificationType type) {
    chunkEditManager.setNotificationType(type);
  }

  public void setSecondsBetweenNotifications(int seconds) {
    chunkEditManager.setSecondsBetweenNotification(seconds);
  }

  public void setMaxParallelChunkCalls(int value) {
    chunkEditManager.setMaxParallelCalls(value);
  }

  public int getMaxParallelChunkCalls(int value) {
    return chunkEditManager.getMaxParallelCalls();
  }

}
