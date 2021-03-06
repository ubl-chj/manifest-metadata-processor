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

package de.ubleipzig.metadata.templates.collections;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.ubleipzig.metadata.templates.Metadata;

import java.util.List;

public class ManifestItem {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    @JsonProperty
    private String label;

    @JsonProperty
    private String thumbnail;

    @JsonProperty
    private List<Related> related;

    @JsonProperty
    private String description;

    @JsonProperty
    private List<Metadata> metadata;

    @JsonProperty
    private SeeAlso seeAlso;

    private static class SeeAlso {
        @JsonProperty("@id")
        private String id;

        @JsonProperty
        private String format;

        public String getId() {
            return id;
        }
    }

    private static class Related {
        @JsonProperty("@id")
        private String id;

        @JsonProperty
        private String format;

        @JsonProperty
        private String label;

        public String getLabel() {
            return label;
        }

        public String getId() {
            return id;
        }
    }

    @JsonProperty
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<Related> getRelated() {
        return related;
    }

    public List<Metadata> getMetadata() {
        return metadata;
    }
}
