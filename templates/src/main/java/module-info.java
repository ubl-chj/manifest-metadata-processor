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
module de.ubleipzig.metadata.templates {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires de.ubleipzig.iiif.vocabulary;
    requires com.fasterxml.jackson.dataformat.xml;
    exports de.ubleipzig.metadata.templates;
    exports de.ubleipzig.metadata.templates.collections;
    opens de.ubleipzig.metadata.templates to com.fasterxml.jackson.databind;
    opens de.ubleipzig.metadata.templates.metsmods to com.fasterxml.jackson.databind;
    opens de.ubleipzig.metadata.templates.v2 to com.fasterxml.jackson.databind;
    opens de.ubleipzig.metadata.templates.v3 to com.fasterxml.jackson.databind;
    opens de.ubleipzig.metadata.templates.atomic to com.fasterxml.jackson.databind;
    opens de.ubleipzig.metadata.templates.indexer to com.fasterxml.jackson.databind;
    opens de.ubleipzig.metadata.templates.collections to com.fasterxml.jackson.databind;
    exports de.ubleipzig.metadata.templates.v2;
    exports de.ubleipzig.metadata.templates.v3;
    exports de.ubleipzig.metadata.templates.atomic;
    exports de.ubleipzig.metadata.templates.indexer;
    exports de.ubleipzig.metadata.templates.metsmods;
}