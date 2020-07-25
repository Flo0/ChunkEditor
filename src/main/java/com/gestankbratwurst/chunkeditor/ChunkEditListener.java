package com.gestankbratwurst.chunkeditor;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of ChunkEditor and was created at the 15.07.2020
 *
 * ChunkEditor can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
@RequiredArgsConstructor
public class ChunkEditListener implements Listener {

  private final ChunkEditManager chunkEditManager;

  @EventHandler
  public void onTick(ServerTickStartEvent event) {
    chunkEditManager.setTickStartNano(System.nanoTime());
  }

}
