/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.sort.standalone.channel;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.flume.event.SimpleEvent;
import org.apache.inlong.sort.standalone.config.pojo.InlongId;
import org.apache.inlong.sort.standalone.utils.Constants;

import java.util.Map;

/**
 * 
 * ProfileEvent
 */
public class ProfileEvent extends SimpleEvent {

    private final String inlongGroupId;
    private final String inlongStreamId;
    private final String uid;

    private final long rawLogTime;
    private final long fetchTime;
    private final CacheMessageRecord cacheRecord;

    /**
     * Constructor
     * 
     * @param body
     * @param headers
     * @param cacheRecord
     */
    public ProfileEvent(byte[] body, Map<String, String> headers, CacheMessageRecord cacheRecord) {
        super.setBody(body);
        super.setHeaders(headers);
        this.cacheRecord = cacheRecord;
        this.inlongGroupId = headers.get(Constants.INLONG_GROUP_ID);
        this.inlongStreamId = headers.get(Constants.INLONG_STREAM_ID);
        this.uid = InlongId.generateUid(inlongGroupId, inlongStreamId);
        this.fetchTime = System.currentTimeMillis();
        this.rawLogTime = NumberUtils.toLong(headers.get(Constants.HEADER_KEY_MSG_TIME), fetchTime);
    }

    /**
     * get inlongGroupId
     * 
     * @return the inlongGroupId
     */
    public String getInlongGroupId() {
        return inlongGroupId;
    }

    /**
     * get inlongStreamId
     * 
     * @return the inlongStreamId
     */
    public String getInlongStreamId() {
        return inlongStreamId;
    }

    /**
     * get rawLogTime
     * 
     * @return the rawLogTime
     */
    public long getRawLogTime() {
        return rawLogTime;
    }

    /**
     * get fetchTime
     * 
     * @return the fetchTime
     */
    public long getFetchTime() {
        return fetchTime;
    }

    /**
     * get uid
     * 
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * get cacheRecord
     * 
     * @return the cacheRecord
     */
    public CacheMessageRecord getCacheRecord() {
        return cacheRecord;
    }

    /**
     * ack
     */
    public void ack() {
        if (cacheRecord != null) {
            cacheRecord.ackMessage();
        }
    }
}
