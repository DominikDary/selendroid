/*
 * Copyright 2012-2014 eBay Software Foundation and selendroid committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.selendroid.server.handler;

import io.selendroid.server.BaseSelendroidServerHandler;
import io.selendroid.server.Response;
import io.selendroid.server.SelendroidResponse;
import io.selendroid.server.model.ActiveSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.openqa.selenium.logging.LogEntry;
import io.selendroid.server.http.HttpRequest;

public class GetLogs extends BaseSelendroidServerHandler {

  public GetLogs(String mappedUri) {
    super(mappedUri);
  }

  @Override
  public Response handle(HttpRequest request) throws JSONException {
    // TODO probably should look at the payload for what type of logs ('driver')
    // but really we only support getting the adb logcat
    ActiveSession session = getSelendroidDriver(request).getActiveSession(getSessionId(request));
    JSONArray logs = new JSONArray();
    for (LogEntry l : session.getDevice().getLogs()) {
      logs.put(l.toString());
    }
    return new SelendroidResponse(getSessionId(request), logs);
  }
}
