/*
 * Copyright 2023 Ant Group CO., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 */

package com.antgroup.openspg.core.spgbuilder.model.pipeline;

import com.antgroup.openspg.common.model.base.BaseValObj;
import com.antgroup.openspg.core.spgbuilder.model.pipeline.config.BaseNodeConfig;

/**
 * The node used to assemble the builder pipeline, which include the node ID, name, and node
 * configuration.
 */
public class Node extends BaseValObj {

  /** The id of the node. */
  private final String id;

  /** The name of the node. */
  private final String name;

  /** The config of the node. */
  private final BaseNodeConfig nodeConfig;

  public Node(String id, String name, BaseNodeConfig nodeConfig) {
    this.id = id;
    this.name = name;
    this.nodeConfig = nodeConfig;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BaseNodeConfig getNodeConfig() {
    return nodeConfig;
  }

  public NodeTypeEnum getType() {
    return nodeConfig.getType();
  }
}
