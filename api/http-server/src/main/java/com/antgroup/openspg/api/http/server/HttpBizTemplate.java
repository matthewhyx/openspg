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

package com.antgroup.openspg.api.http.server;

import com.antgroup.openspg.api.facade.ApiConstants;
import com.antgroup.openspg.biz.common.util.BizThreadLocal;
import com.antgroup.openspg.common.model.exception.IllegalParamsException;
import com.antgroup.openspg.common.model.exception.OpenSPGException;
import com.antgroup.openspg.common.util.NetworkAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Slf4j
public class HttpBizTemplate {

  /**
   * HTTP execution interface template.
   *
   * @param callback The callback for executing the logic.
   * @param <T> The type of the return result.
   * @return The execution result.
   */
  public static <T> ResponseEntity<Object> execute(HttpBizCallback<T> callback) {
    ResponseEntity<Object> response = null;
    try {
      callback.check();
      T result = callback.action();
      response = callback.response(result);
    } catch (IllegalParamsException e) {
      log.error("error http illegal params", e);
      response = ResponseEntity.badRequest().body(e.getMessage());
    } catch (OpenSPGException e) {
      log.error("execute http biz callback error", e);
      response = ResponseEntity.internalServerError().body(e.getMessage());
    } catch (Throwable e) {
      log.error("execute http biz callback unknown error", e);
      String errorMsg = e.getMessage();
      if (e instanceof NullPointerException) {
        errorMsg = "system unknown error with NullPointerException";
      } else if (StringUtils.isBlank(errorMsg)) {
        errorMsg = "system unknown error";
      }
      response = ResponseEntity.internalServerError().body(errorMsg);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add(ApiConstants.TRACE_ID, BizThreadLocal.getTraceId());
    headers.add(ApiConstants.REMOTE, NetworkAddressUtils.LOCAL_IP);
    return ResponseEntity.status(response.getStatusCode())
        .headers(headers)
        .body(response.getBody());
  }
}
