package com.gestankbratwurst.chunkeditor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of ChunkEditor and was created at the 25.07.2020
 *
 * ChunkEditor can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public final class ChunkEditor extends JavaPlugin {

  @Override
  public void onEnable() {

    ChunkEditManager chunkEditManager = new ChunkEditManager(this);

    ChunkEditorAPI.init(chunkEditManager);

    Bukkit.getPluginManager().registerEvents(new ChunkEditListener(chunkEditManager), this);

  }

  @Override
  public void onDisable() {

  }

}
