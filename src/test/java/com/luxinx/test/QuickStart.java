/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.luxinx.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class QuickStart {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet("http://data.gtimg.cn/flashdata/hushen/daily/17/sh601866.js");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
            	System.out.println(response1.getStatusLine().getStatusCode());
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                String st = EntityUtils.toString(entity1);
                System.out.println(st);
                String[] starr = st.split("\\\\n\\\\");
                for(int i=0;i<starr.length;i++){
                	System.out.println(starr[i]);
                }
            } finally {
                response1.close();
            }
        	
        } finally {
            httpclient.close();
        }
    }

}
