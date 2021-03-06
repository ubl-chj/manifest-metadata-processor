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

package de.ubleipzig.metadata.indexer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ubleipzig.metadata.processor.JsonSerializer;
import de.ubleipzig.metadata.templates.MapListCollection;
import de.ubleipzig.metadata.templates.MetadataMap;
import de.ubleipzig.metadata.templates.collections.CollectionList;
import de.ubleipzig.metadata.templates.collections.ManifestItem;
import de.ubleipzig.metadata.templates.collections.ManifestList;
import de.ubleipzig.metadata.templates.collections.RootCollection;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.jena.JenaRDF;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trellisldp.client.LdpClient;
import org.trellisldp.client.LdpClientException;
import org.trellisldp.client.LdpClientImpl;

@Disabled
public class Collection3LevelCollectorTest {

    private final LdpClient client = new LdpClientImpl();
    private static final JenaRDF rdf = new JenaRDF();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String baseUrl = "http://localhost:9098/extractor?type=extract&m=";
    private final Indexer indexer = new Indexer();
    private static final Logger LOGGER = LoggerFactory.getLogger(Collection3LevelCollectorTest.class);

    @Test
    void buildCollectionsFromJson() {
        final IRI rootCollectionIRI = rdf.createIRI("https://iiif.durham.ac.uk/manifests/trifle/collection/index");
        try {
            final HttpResponse res = client.getResponse(rootCollectionIRI);
            if (res.statusCode() == 200 | res.statusCode() == 301) {
                final String json = res.body().toString();
                final CollectionList collections = MAPPER.readValue(json, new TypeReference<CollectionList>() {
                });
                final List<ManifestItem> cList = collections.getCollections();
                final RootCollection rootCollection = new RootCollection();
                final List<MapListCollection> mapListCollections = new ArrayList<>();
                cList.forEach(c -> {
                    final String cid = c.getId();
                    final String label = c.getLabel();
                    final IRI cIRI = rdf.createIRI(cid);
                    try {
                        final HttpResponse res1 = client.getResponse(cIRI);
                        if (res1.statusCode() == 200 | res1.statusCode() == 301) {
                            final String json1 = res1.body().toString();
                            final CollectionList subCollections = MAPPER.readValue(
                                    json1, new TypeReference<CollectionList>() {
                                    });
                            final List<ManifestItem> cList1 = subCollections.getCollections();
                            cList1.forEach(c1 -> {
                                final String cid2 = c1.getId();
                                final String label2 = c1.getLabel();
                                final IRI cIRI2 = rdf.createIRI(cid2);
                                final HttpResponse res2;
                                try {
                                    res2 = client.getResponse(cIRI2);
                                    if (res2.statusCode() == 200 | res2.statusCode() == 301) {
                                        final String json2 = res2.body().toString();
                                        final ManifestList subcollections = MAPPER.readValue(json2,
                                                new TypeReference<ManifestList>() {
                                                });
                                        final List<ManifestItem> manifestList = subcollections.getManifests();
                                        List<MetadataMap> finalMapList = new ArrayList<>();
                                        for (ManifestItem m : manifestList) {
                                            final IRI identifier = rdf.createIRI(m.getId());
                                            final IRI apiReq = rdf.createIRI(baseUrl + identifier.getIRIString());
                                            final HttpResponse res3 = client.getResponse(apiReq);
                                            if (res3.statusCode() == 200 | res3.statusCode() == 301) {
                                                final String json3 = res3.body().toString();
                                                final MetadataMap metadataMap = indexer.buildMetadataMap(json3);
                                                Map<String, Object> metadata = metadataMap.getMetadataMap();
                                                metadata.put("collection", label);
                                                metadata.put("subCollection", label2);
                                                metadata.put("manifest", m.getId());
                                                metadataMap.setMetadataMap(metadata);
                                                finalMapList.add(metadataMap);
                                                LOGGER.info(
                                                        "adding {} to indexable metadata", identifier.getIRIString());
                                            }
                                        }
                                        final MapListCollection l = new MapListCollection();
                                        l.setMapListCollection(finalMapList);
                                        l.setId(c.getId());
                                        l.setLabel(c.getLabel());
                                        mapListCollections.add(l);
                                    }
                                } catch (LdpClientException e) {
                                    e.printStackTrace();
                                } catch (JsonParseException e) {
                                    e.printStackTrace();
                                } catch (JsonMappingException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    } catch (LdpClientException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    };
                    rootCollection.setRootCollection(mapListCollections);
                    final String out = JsonSerializer.serialize(rootCollection).orElse("");
                    JsonSerializer.writeToFile(out, new File("/tmp/cambridge-metadata.json"));
                });
            }
        } catch (LdpClientException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
