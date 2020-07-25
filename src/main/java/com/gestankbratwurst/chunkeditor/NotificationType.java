package com.gestankbratwurst.chunkeditor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of ChunkEditor and was created at the 25.07.2020
 *
 * ChunkEditor can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public enum NotificationType {

  NONE() {
    @Override
    void call(Object value) {}
  },
  CONSOLE() {
    @Override
    void call(Object value) {
      Bukkit.getLogger().info(Msg.info(value.toString()));
    }
  },
  BROADCAST() {
    @Override
    void call(Object value) {
      Bukkit.getOnlinePlayers().forEach(pl -> pl.sendMessage(Msg.info(value.toString())));
    }
  },
  OPERATOR() {
    @Override
    void call(Object value) {
      Bukkit.getOnlinePlayers().stream().filter(Player::isOp).forEach(pl -> pl.sendMessage(Msg.info(value.toString())));
    }
  };

  abstract void call(Object value);

}
