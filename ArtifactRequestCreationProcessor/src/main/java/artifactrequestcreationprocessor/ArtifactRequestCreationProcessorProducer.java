/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package artifactrequestcreationprocessor;

import java.net.URI;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fraunhofer.iais.eis.ArtifactRequestMessageBuilder;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;

/**
 * The www.Sample.com producer.
 */
public class ArtifactRequestCreationProcessorProducer extends DefaultProducer {
    private static final transient Logger LOG = LoggerFactory.getLogger(ArtifactRequestCreationProcessorProducer.class);
    private ArtifactRequestCreationProcessorEndpoint endpoint;

	public ArtifactRequestCreationProcessorProducer(ArtifactRequestCreationProcessorEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    public void process(final Exchange exchange) throws Exception {
    	
		Object uriVal= exchange.getProperty("artifactUri");
		ArtifactRequestMessageBuilder builder = new ArtifactRequestMessageBuilder();
		URI requestedArtifact =null;
		 if (uriVal != null) {
			 
			 requestedArtifact = uriVal instanceof URI ? (URI)uriVal : URI.create(uriVal.toString());
		 }
		
		 if(requestedArtifact!=null) {
			 builder._requestedArtifact_(requestedArtifact);
			 Serializer ser = new Serializer();
			 exchange.getMessage().setHeader("idscp2-header",ser.serialize(builder.build()));
			 exchange.getMessage().setBody(ser.serialize(builder.build()));
		 }
    }

}
