package com.gestankbratwurst.chunkeditor;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of ChunkEditor and was created at the 15.07.2020
 *
 * ChunkEditor can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public interface TickableProcess {

  boolean isDone();

  boolean proceed();

  String getProgress(long millisDelta);

}
