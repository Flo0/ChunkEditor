package com.gestankbratwurst.chunkeditor;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.File;
import lombok.Getter;
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
public class RegionParser implements TickableProcess {

  public RegionParser(World world) {
    this.world = world;
    this.regionFolder = new File(world.getWorldFolder() + File.separator + "region");
  }

  @Getter
  private final World world;
  private final File regionFolder;
  private File[] regionFiles = null;
  private int index;
  @Getter
  private final LongList chunkKeys = new LongArrayList();
  private boolean done = false;

  private String percent() {
    return Msg.format(100D / regionFiles.length * index) + "%";
  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public boolean proceed() {
    if (regionFiles == null) {
      long start = System.nanoTime();
      System.out.println(Msg.info("Listing region files. This could freeze the server for several seconds."));
      regionFiles = regionFolder.listFiles();
      double time = (System.nanoTime() - start) / 1E6;
      System.out.println(Msg.info(regionFiles.length + " Region files are listed. Took §e" + Msg.format(time) + "ms"));
      return false;
    }
    if (index == regionFiles.length) {
      done = true;
      return false;
    }
    File regionFile = regionFiles[index++];
    String[] split = regionFile.getName().split("\\.");
    int rx = Integer.parseInt(split[1]);
    int rz = Integer.parseInt(split[2]);
    for (int x = 0; x < 32; x++) {
      for (int z = 0; z < 32; z++) {
        chunkKeys.add(Chunk.getChunkKey(rx * 32 + x, rz * 32 + z));
      }
    }
    return true;
  }

  @Override
  public String getProgress(long millisDelta) {
    return "§7Generating chunk keys: §e" + index + "§7/§e" + regionFiles.length + " §e[§7" + percent() + "§e]";
  }

}
