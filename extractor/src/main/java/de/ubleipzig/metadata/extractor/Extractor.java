/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ubleipzig.metadata.extractor;

import static org.apache.camel.Exchange.CONTENT_TYPE;
import static org.apache.camel.Exchange.HTTP_METHOD;
import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.apache.camel.LoggingLevel.INFO;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;
import org.apache.camel.util.IOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Extractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Extractor.class);
    private static final String HTTP_ACCEPT = "Accept";
    private static final String SPARQL_QUERY = "type";
    private static final String MANIFEST_URI = "manifest";
    private static final String contentTypeJsonLd = "application/ld+json";


    /**
     * main.
     *
     * @param args String[]
     * @throws Exception Exception
     */
    public static void main(final String[] args) throws Exception {
        final Extractor selector = new Extractor();
        selector.init();
    }

    /**
     * init.
     *
     * @throws Exception Exception
     */
    private void init() throws Exception {
        final Main main = new Main();
        main.addRouteBuilder(new Extractor.QueryRoute());
        main.addMainListener(new Extractor.Events());
        final JndiRegistry registry = new JndiRegistry(createInitialContext());
        main.setPropertyPlaceholderLocations("file:${env:DYNAMO_HOME}/de.ubleipzig.metadata.extractor.cfg");
        main.run();
    }

    /**
     * Events.
     */
    public static class Events extends MainListenerSupport {

        @Override
        public void afterStart(final MainSupport main) {
            System.out.println("Extractor is now started!");
        }

        @Override
        public void beforeStop(final MainSupport main) {
            System.out.println("Extractor is now being stopped!");
        }
    }

    /**
     * QueryRoute.
     */
    public static class QueryRoute extends RouteBuilder {


        /**
         * configure.
         */
        public void configure() {
            from("jetty:http://{{api.host}}:{{api.port}}{{api.prefix}}?"
                    + "optionsEnabled=true&matchOnUriPrefix=true&sendServerVersion=false"
                    + "&httpMethodRestrict=GET,OPTIONS").routeId("Extractor").removeHeaders(HTTP_ACCEPT).setHeader(
                    "Access-Control-Allow" + "-Origin").constant("*").choice().when(
                    header(HTTP_METHOD).isEqualTo("GET")).to("direct:getManifest");
            from("direct:getManifest").process(
                    e -> e.getIn().setHeader(Exchange.HTTP_URI, e.getIn().getHeader(MANIFEST_URI))).to("http4").filter(
                    header(HTTP_RESPONSE_CODE).isEqualTo(200)).setHeader(CONTENT_TYPE).constant(
                    contentTypeJsonLd).convertBodyTo(String.class).log(INFO, LOGGER, "Fetching Json-LD document").to(
                    "direct:toRDF");
            from("direct:toRDF").choice().when(header(SPARQL_QUERY).isEqualTo("extract")).log(
                    INFO, LOGGER, "Extracting Metadata from Json-LD document").process(
                    ExchangeProcess::processJsonLdExchange);
        }
    }

    /**
     * createInitialContext.
     *
     * @return InitialContext Context
     * @throws Exception Exception
     */
    private static Context createInitialContext() throws Exception {
        final InputStream in = Extractor.class.getClassLoader().getResourceAsStream("jndi.properties");
        try {
            final Properties properties = new Properties();
            properties.load(in);
            return new InitialContext(new Hashtable<>(properties));
        } finally {
            IOHelper.close(in);
        }
    }
}