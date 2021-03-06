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

public class LandingDoc {
    @JsonProperty
    private String tag1;

    @JsonProperty
    private String tag2;

    @JsonProperty
    private String tag3;

    @JsonProperty
    private String tag4;

    @JsonProperty
    private String imageServiceIRI;

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public void setTag4(String tag4) {
        this.tag4 = tag4;
    }

    public void setImageServiceIRI(String imageServiceIRI) {
        this.imageServiceIRI = imageServiceIRI;
    }
}
