package com.gestankbratwurst.chunkeditor;

import java.text.DecimalFormat;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of ChunkEditor and was created at the 15.07.2020
 *
 * ChunkEditor can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class Msg {

  private static final String PREFIX = "ChunkEditor> ";
  private static final DecimalFormat DF = new DecimalFormat("#.##");

  public static String info(Object message) {
    return "§e" + PREFIX + "§7" + message.toString();
  }

  public static String error(Object message) {
    return "§c" + PREFIX + "§7" + message.toString();
  }

  public static String format(double value) {
    return DF.format(value);
  }

}
